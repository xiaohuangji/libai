package com.dajie.wika.model;

import com.dajie.wika.constants.PrivacyType;

/**
 * 用户默认设置
 * 
 * @author li.hui
 * 
 */
public class UserDefaultSettings {
	private int userId;

	private int emailPrivacy = PrivacyType.FRIEND;

	private int mobilePrivacy = PrivacyType.FRIEND;

	// 按位存储消息接收开关,1是开启，0是关闭
	private long notification = Long.MAX_VALUE;

	public static UserDefaultSettings getUserDefaultSettings(int userId) {
		return new UserDefaultSettings(userId);
	}

	public UserDefaultSettings(int userId) {
		this.userId = userId;
	}

	public UserDefaultSettings() {
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getEmailPrivacy() {
		return emailPrivacy;
	}

	public void setEmailPrivacy(int emailPrivacy) {
		this.emailPrivacy = emailPrivacy;
	}

	public int getMobilePrivacy() {
		return mobilePrivacy;
	}

	public void setMobilePrivacy(int mobilePrivacy) {
		this.mobilePrivacy = mobilePrivacy;
	}

	public long getNotification() {
		return notification;
	}

	public void setNotification(long notification) {
		this.notification = notification;
	}

}
