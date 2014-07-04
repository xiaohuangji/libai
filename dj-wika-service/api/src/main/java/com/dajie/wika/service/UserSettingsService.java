package com.dajie.wika.service;

import com.dajie.wika.model.NotificationSettings;
import com.dajie.wika.model.UserDevice;
import com.dajie.wika.model.UserPrivacySettings;

public interface UserSettingsService {

	/**
	 * 增加默认用户设置
	 * 
	 * @param userId
	 * @return
	 */
	int addDefaultUserSettings(int userId);

	/**
	 * 设置信息隐私
	 * 
	 * @param userPrivacySettings
	 * @return
	 */
	int setPrivacySettings(UserPrivacySettings userPrivacySettings);

	/**
	 * 设置接收消息通知开关
	 * 
	 * @param userNotifationSettings
	 * @return
	 */
	int setNotificationsSettings(NotificationSettings notificationSettings);

	/**
	 * 更新用户token,解绑时传入空
	 * 
	 * @param userId
	 * @param deviceToken
	 * @type 设备类型 android ios @see com.dajie.wika.constants.DeviceType
	 * @return
	 */
	int setDeviceToken(int userId, String deviceToken, int deviceType);

	/**
	 * 获取用户device
	 * 
	 * @param userId
	 * @param deviceType @see com.dajie.wika.constants.DeviceType
	 *            设备类型
	 * @return
	 */
	UserDevice getDeviceTokenByUserIdAndType(int userId, int deviceType);

	/**
	 * 删除一个用户的一种设备token
	 * 
	 * @param userId
	 * @param deviceType @see com.dajie.wika.constants.DeviceType
	 * @return
	 */
	int delteDeviceToken(int userId, int deviceType);

	/**
	 * 获取用户隐私设置
	 * 
	 * @param userId
	 * @return
	 */
	UserPrivacySettings getUserPrivacySettings(int userId);

	/**
	 * 获取用户通知设置
	 * 
	 * @param userId
	 * @return
	 */
	NotificationSettings getNotificationsSettings(int userId);
}
