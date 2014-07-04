package com.dajie.wika.model;

import java.io.Serializable;

public class UserValue implements Serializable {

	private int userId;
	
	private int userValue;
	
	private long dayStartTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserValue() {
		return userValue;
	}

	public void setUserValue(int userValue) {
		this.userValue = userValue;
	}

	public long getDayStartTime() {
		return dayStartTime;
	}

	public void setDayStartTime(long dayStartTime) {
		this.dayStartTime = dayStartTime;
	}
	
}
