package com.dajie.wika.model;

import java.io.Serializable;
import java.util.Date;

public class Feedback implements Serializable{

	/**
	 * 提交反馈的userId
	 */
	private int userId;
	
	/**
	 * 提交反馈的内容
	 */
	private String content;
	
	/**
	 * 联系方式
	 */
	private String contact;
	
	/**
	 * 创建时间
	 */
	private Date createTime;
	
	/**
	 * 状态
	 * 0 用户已提交  1管理员 已处理
	 */
	private int status=0;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}
	
}
