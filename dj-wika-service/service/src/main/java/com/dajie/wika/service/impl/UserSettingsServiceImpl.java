package com.dajie.wika.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.wika.constants.PrivacyType;
import com.dajie.wika.constants.returncode.ResultCodeConstant;
import com.dajie.wika.dao.UserDeviceDAO;
import com.dajie.wika.dao.UserSettingsDAO;
import com.dajie.wika.model.NotificationSettings;
import com.dajie.wika.model.UserDefaultSettings;
import com.dajie.wika.model.UserDevice;
import com.dajie.wika.model.UserPrivacySettings;
import com.dajie.wika.service.UserSettingsService;

@Service("userSettingsService")
public class UserSettingsServiceImpl implements UserSettingsService {

	private Logger logger = Logger.getLogger(UserSettingsServiceImpl.class);

	@Autowired
	private UserDeviceDAO userDeviceDAO;

	@Autowired
	private UserSettingsDAO userSettingsDAO;

	@Override
	public int addDefaultUserSettings(int userId) {
		UserDefaultSettings userDefaultSettings = UserDefaultSettings
				.getUserDefaultSettings(userId);
		try {
			int insertResult = userSettingsDAO
					.addDefaultUserSettings(userDefaultSettings);
			logger.info("insert defaultUserSetting return = " + insertResult);
			return insertResult > 0 ? ResultCodeConstant.OP_SUCC
					: ResultCodeConstant.OP_FAIL;
		} catch (Exception e) {
			logger.info("add defaultUserSettings error", e);
			return ResultCodeConstant.OP_FAIL;
		}
	}

	@Override
	public int setPrivacySettings(UserPrivacySettings userPrivacySettings) {
		try {
			boolean privacyValid = checkPrivacySettings(userPrivacySettings);
			if (!privacyValid) {
				logger.info("user privacy setting unvalid.");
				return ResultCodeConstant.OP_FAIL;
			}
			int updateResult = userSettingsDAO
					.setPrivacySettings(userPrivacySettings);
			logger.info("update privacy setting return = " + updateResult);
			return updateResult > 0 ? ResultCodeConstant.OP_SUCC
					: ResultCodeConstant.OP_FAIL;
		} catch (Exception e) {
			logger.info("update privacy setting error", e);
			return ResultCodeConstant.OP_FAIL;
		}
	}

	@Override
	public int setNotificationsSettings(
			NotificationSettings notificationSettings) {
		int userId = notificationSettings.getUserId();
		boolean newFollowerNotice = notificationSettings.isNewFollowerNotice();
		boolean connectionStatisticNotice = notificationSettings
				.isConnectionStatisticNotice();
		boolean contactWikaUpdateNotice = notificationSettings
				.isContactWikaUpdateNotice();

		long modify_code = (newFollowerNotice ? 1 : 0) * 1L
				+ (connectionStatisticNotice ? 1 : 0) * 2L
				+ (contactWikaUpdateNotice ? 1 : 0) * 4L;
		try {
			long notificationSettingsCode = userSettingsDAO
					.getNotificationSetting(userId);

			notificationSettingsCode = (notificationSettingsCode >> 3 << 3)
					| modify_code;
			int updateResult = userSettingsDAO.setNotificationSetting(userId,
					notificationSettingsCode);
			if (updateResult > 0) {
				logger.info("set notificationSettings success.");
				return ResultCodeConstant.OP_SUCC;
			} else {
				logger.info("set notificationSettings fail. update database return="
						+ updateResult);
				return ResultCodeConstant.OP_FAIL;
			}

		} catch (Exception e) {
			logger.error("set notificationSettings error:", e);
			return ResultCodeConstant.OP_FAIL;
		}

	}

	@Override
	public UserPrivacySettings getUserPrivacySettings(int userId) {
		UserPrivacySettings privacySettings = userSettingsDAO
				.getPrivacySettingsByUserId(userId);
		if (null == privacySettings) {
			addDefaultUserSettings(userId);
			privacySettings = userSettingsDAO
					.getPrivacySettingsByUserId(userId);
		}
		return privacySettings;
	}

	@Override
	public NotificationSettings getNotificationsSettings(int userId) {
		Long notification = userSettingsDAO.getNotificationSetting(userId);
		NotificationSettings settings = new NotificationSettings();
		settings.setUserId(userId);
		if (notification != null) {
			boolean newFollowerNotice = (notification & 1L) > 0;
			boolean connectionStatisticNotice = (notification & 2L) > 0;
			boolean contactWikaUpdateNotice = (notification & 4L) > 0;

			settings.setNewFollowerNotice(newFollowerNotice);
			settings.setConnectionStatisticNotice(connectionStatisticNotice);
			settings.setContactWikaUpdateNotice(contactWikaUpdateNotice);
		}
		// 默认设置
		else {
			settings.setConnectionStatisticNotice(true);
			settings.setNewFollowerNotice(true);
			settings.setContactWikaUpdateNotice(true);
		}

		return settings;
	}

	/*************************** device token 操作,与用户push相关 ***/
	@Override
	public int setDeviceToken(int userId, String deviceToken, int deviceType) {
		UserDevice userDevice = getDeviceTokenByUserIdAndType(userId,
				deviceType);
		if (userDevice != null
				&& deviceToken.equals(userDevice.getDeviceToken())) {
			logger.info("device token no change");
			return ResultCodeConstant.OP_SUCC;
		} else {
			// 删除
			if (null == deviceToken || "".equals(deviceToken)) {
				try {
					int deleteResult = delteDeviceToken(userId, deviceType);
					if (deleteResult > 0) {
						logger.info("delete device token success userId:"
								+ userId + ",deviceToken=" + deviceToken
								+ ",deviceType=" + deviceType);
						return ResultCodeConstant.OP_SUCC;
					} else {
						logger.info("delete device token success userId:"
								+ userId + ",this user has no device");
						return ResultCodeConstant.OP_SUCC;
					}
				} catch (Exception e) {
					logger.error("delete token error:", e);
					return ResultCodeConstant.OP_FAIL;
				}
			} else {
				try {
					// 删除这个用户绑定的其它手机
					int deleteResult = delteDeviceToken(userId);
					logger.info("delete " + deleteResult + " device , userId="
							+ userId);
					// replace:multi primary key(deviceToken,deviceType)
					// 把这台设备给userId这个用户
					userDevice = new UserDevice(userId, deviceToken, deviceType);
					int setResult = userDeviceDAO.setUserDevice(userDevice);
					logger.info("set device return : " + setResult);
					return ResultCodeConstant.OP_SUCC;
				} catch (Exception e) {
					logger.error("set token error:", e);
					return ResultCodeConstant.OP_FAIL;
				}
			}
		}

	}

	@Override
	public UserDevice getDeviceTokenByUserIdAndType(int userId, int deviceType) {
		return userDeviceDAO.getDeviceByUserIdAndType(userId, deviceType);
	}

	@Override
	public int delteDeviceToken(int userId, int deviceType) {
		return userDeviceDAO.deleteByUserIdAndType(userId, deviceType);
	}

	public int delteDeviceToken(int userId) {
		return userDeviceDAO.deleteByUserId(userId);
	}

	private boolean checkPrivacySettings(UserPrivacySettings userPrivacySettings) {
		int emailPrivacy = userPrivacySettings.getEmailPrivacy();
		int mobilePrivacy = userPrivacySettings.getMobilePrivacy();
		boolean emailPrivacyValid = false;
		boolean mobilePrivacyValid = false;
		if (emailPrivacy >= PrivacyType.EVERYONE
				&& emailPrivacy <= PrivacyType.SELF) {
			emailPrivacyValid = true;
		}
		if (mobilePrivacy >= PrivacyType.EVERYONE
				&& mobilePrivacy <= PrivacyType.SELF) {
			mobilePrivacyValid = true;
		}
		return mobilePrivacyValid && emailPrivacyValid;
	}
}
