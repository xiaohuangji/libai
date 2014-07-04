package com.dajie.wika.model;

import java.io.Serializable;

public class NotificationSettings implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2505763620360688223L;
	// 用户id
	public int userId;
	// 想认识你的
	public boolean newFollowerNotice;

	// 人脉统计更新
	public boolean connectionStatisticNotice;

	// 联系人微卡更新
	public boolean contactWikaUpdateNotice;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public boolean isNewFollowerNotice() {
		return newFollowerNotice;
	}

	public void setNewFollowerNotice(boolean newFollowerNotice) {
		this.newFollowerNotice = newFollowerNotice;
	}

	public boolean isConnectionStatisticNotice() {
		return connectionStatisticNotice;
	}

	public void setConnectionStatisticNotice(boolean connectionStatisticNotice) {
		this.connectionStatisticNotice = connectionStatisticNotice;
	}

	public boolean isContactWikaUpdateNotice() {
		return contactWikaUpdateNotice;
	}

	public void setContactWikaUpdateNotice(boolean contactWikaUpdateNotice) {
		this.contactWikaUpdateNotice = contactWikaUpdateNotice;
	}

}
