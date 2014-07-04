package com.dajie.wika.model.wrapper;

import java.io.Serializable;

public class LoginReturn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8585369170202263895L;

	private int returnCode;

	private LoginUserView loginUserView;

	private int userId;

	private String shortUrl;

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	public LoginUserView getLoginUserView() {
		return loginUserView;
	}

	public void setLoginUserView(LoginUserView loginUserView) {
		this.loginUserView = loginUserView;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

}
