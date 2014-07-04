package com.dajie.wika.model.wrapper;

import java.io.Serializable;

import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.UserCountBase;
import com.dajie.wika.model.UserCountYesterday;
import com.dajie.wika.model.UserOccupation;

public class UserProfile implements Serializable {

	/**
	 * userId
	 */
	private int userId;

	private int relationType;
	
	/**
	 * 短链url
	 */
	private String shortUrl;
	/**
	 * 用户基本信息
	 */
	private UserBase userBase;

	/**
	 * 用户职场信息
	 */
	private UserOccupation userOccupation;

	/**
	 * 用户计数信息精简版
	 */
	private UserCountLiteWrapper userCountLiteWrapper;

	public UserProfile(int userId) {
		this.userId = userId;
	}

	public int getUserId() {
		return userId;
	}

	public UserBase getUserBase() {
		return userBase;
	}

	public void setUserBase(UserBase userBase) {
		this.userBase = userBase;
	}

	public UserOccupation getUserOccupation() {
		return userOccupation;
	}

	public void setUserOccupation(UserOccupation userOccupation) {
		this.userOccupation = userOccupation;
	}

	public UserCountLiteWrapper getUserCountLiteWrapper() {
		return userCountLiteWrapper;
	}

	public void setUserCountLiteWrapper(
			UserCountLiteWrapper userCountLiteWrapper) {
		this.userCountLiteWrapper = userCountLiteWrapper;
	}

	public int getRelationType() {
		return relationType;
	}

	public void setRelationType(int relationType) {
		this.relationType = relationType;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}
