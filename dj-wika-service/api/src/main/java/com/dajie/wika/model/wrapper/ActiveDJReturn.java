package com.dajie.wika.model.wrapper;

import java.io.Serializable;

public class ActiveDJReturn implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2575037887404916267L;

	private int returnCode;

	private LoginUserView loginUserView;

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
	
	
}
