package com.dajie.wika.model;

import java.io.Serializable;

public class ShortUrlInfo implements Serializable {

	private int userId;
	
	/**
	 * 身份标识
	 */
	private int idt;
	
	/**
	 * 渠道来源
	 */
	private int cn;

	/**
	 * 是否他人态
	 */
	private int sts;
	
	/**
	 * 文案
	 */
	private int cp;
	
	/**
	 * 来源入口
	 */
	private int etc;
	
	/**
	 * 用户基本信息
	 */
	private UserBase userBase;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getIdt() {
		return idt;
	}

	public void setIdt(int idt) {
		this.idt = idt;
	}

	public int getCn() {
		return cn;
	}

	public void setCn(int cn) {
		this.cn = cn;
	}

	public int getSts() {
		return sts;
	}

	public void setSts(int sts) {
		this.sts = sts;
	}


	public int getCp() {
		return cp;
	}

	public void setCp(int cp) {
		this.cp = cp;
	}

	public int getEtc() {
		return etc;
	}

	public void setEtc(int etc) {
		this.etc = etc;
	}

	public UserBase getUserBase() {
		return userBase;
	}

	public void setUserBase(UserBase userBase) {
		this.userBase = userBase;
	}	
	
}
