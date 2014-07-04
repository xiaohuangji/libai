package com.dajie.wika.dao;

import com.dajie.wika.model.UserDefaultSettings;
import com.dajie.wika.model.UserPrivacySettings;

public interface UserSettingsDAO {

	int addDefaultUserSettings(UserDefaultSettings defaultSettings);

	UserPrivacySettings getPrivacySettingsByUserId(int userId);

	int setPrivacySettings(UserPrivacySettings userPrivacySettings);

	long getNotificationSetting(int userId);

	int setNotificationSetting(int userId, long notificationSettingsCode);

}
