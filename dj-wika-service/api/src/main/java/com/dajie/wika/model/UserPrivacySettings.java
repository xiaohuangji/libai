package com.dajie.wika.model;

import java.io.Serializable;

public class UserPrivacySettings implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4026746526267074285L;

	private int userId;

	/**
	 * 邮箱隐私控制
	 * @see com.dajie.wika.constants.PrivacyType
	 */
	private int emailPrivacy;

	/**
	 * 手机隐私控制
	 * @see com.dajie.wika.constants.PrivacyType
	 */
	private int mobilePrivacy;

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
}
