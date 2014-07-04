package com.dajie.wika.model;

import java.io.Serializable;

public class UserOccupation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -620205787235618139L;

	private int userId;

	// 公司信息
	private String corp;

	// 行业信息 ,已有字典
	private int industry;

	// 职位信息 ,手动输入
	private String position;

	// 部门 ,手动输入
	private String department;

	// 职位类型 ,已有字典
	private int jobType;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public int getIndustry() {
		return industry;
	}

	public void setIndustry(int industry) {
		this.industry = industry;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

}
