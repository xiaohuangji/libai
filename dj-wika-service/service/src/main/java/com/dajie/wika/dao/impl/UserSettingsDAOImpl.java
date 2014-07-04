package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.UserSettingsDAO;
import com.dajie.wika.model.UserDefaultSettings;
import com.dajie.wika.model.UserPrivacySettings;

@Component("userSettingsDAO")
public class UserSettingsDAOImpl extends Dao implements UserSettingsDAO {

	private final String SQL_PREFIX = "com.dajie.wika.dao.UserSettingsDAO.";

	public UserSettingsDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@PostConstruct
	public void init() {
		this.initResource();
	}

	@Override
	public int addDefaultUserSettings(UserDefaultSettings defaultSettings) {
		return insert(SQL_PREFIX + "addDefaultUserSettings", defaultSettings);
	}

	@Override
	public UserPrivacySettings getPrivacySettingsByUserId(int userId) {
		return (UserPrivacySettings) selectOne(SQL_PREFIX
				+ "getPrivacySettingsByUserId", userId);
	}

	@Override
	public int setPrivacySettings(UserPrivacySettings userPrivacySettings) {
		return update(SQL_PREFIX + "setPrivacySettings", userPrivacySettings);
	}

	@Override
	public long getNotificationSetting(int userId) {
		return (Long) selectOne(SQL_PREFIX + "getNotificationSetting", userId);
	}

	@Override
	public int setNotificationSetting(int userId, long notificationSettingsCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("notification", notificationSettingsCode);
		return update(SQL_PREFIX + "setNotificationSetting",
				param);
	}

}
