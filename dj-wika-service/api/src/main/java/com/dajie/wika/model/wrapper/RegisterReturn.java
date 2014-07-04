package com.dajie.wika.model.wrapper;

import java.io.Serializable;

public class RegisterReturn implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7129912901315085546L;

	private int userId;

	private int returnCode;

	private String profileShortUrl;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public String getProfileShortUrl() {
		return profileShortUrl;
	}

	public void setProfileShortUrl(String profileShortUrl) {
		this.profileShortUrl = profileShortUrl;
	}

}
