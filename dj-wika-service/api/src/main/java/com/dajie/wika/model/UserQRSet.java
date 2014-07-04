package com.dajie.wika.model;

import java.io.Serializable;

public class UserQRSet implements Serializable {
	
	private int userId;
	
	/**
	 * 模板id
	 */
	private int qrId;
	
	/**
	 * 对应此模板的url
	 */
	private String qrUrl;
	
	public UserQRSet(){
		
	}
	public UserQRSet(int userId,int qrId,String qrUrl){
		this.userId=userId;
		this.qrId=qrId;
		this.qrUrl=qrUrl;
	}

	public int getQrId() {
		return qrId;
	}

	public void setQrId(int qrId) {
		this.qrId = qrId;
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	
}
