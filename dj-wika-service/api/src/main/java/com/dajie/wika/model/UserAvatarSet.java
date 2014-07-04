package com.dajie.wika.model;

public class UserAvatarSet {

	
	private int userId;
	/**
	 * 头像
	 */
	private String avatar;
	
	/**
	 * 只是默认的二维码头像
	 */
	private String qrAvatar;
	
	/**
	 * 默认的二维码头像模板id
	 */
	private int qrId;
	
	public UserAvatarSet(int userId,String avatar){
		this.userId=userId;
		this.avatar=avatar;
	}
	
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getQrAvatar() {
		return qrAvatar;
	}

	public void setQrAvatar(String qrAvatar) {
		this.qrAvatar = qrAvatar;
	}

	public int getQrId() {
		return qrId;
	}

	public void setQrId(int qrId) {
		this.qrId = qrId;
	}
	
}
