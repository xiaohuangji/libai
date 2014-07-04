package com.dajie.wika.model;

import java.io.Serializable;
import java.util.Date;

public class UserDevice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1851614975149075283L;

	/**
	 * userId
	 */
	private int userId;

	/**
	 * token字符串
	 */
	private String deviceToken;

	/**
	 * device类型
	 */
	private int deviceType;

	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	public UserDevice(int userId, String deviceToken, int deviceType){
		this.userId=userId;
		this.deviceToken=deviceToken;
		this.deviceType=deviceType;
	}
	
	public UserDevice(){}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public int getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(int deviceType) {
		this.deviceType = deviceType;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
