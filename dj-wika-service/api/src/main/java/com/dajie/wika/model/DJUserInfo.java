package com.dajie.wika.model;

/**
 * wika帐号需要使用的大街信息
 * 
 * @author li.hui
 * 
 */
public class DJUserInfo {

	/** 大街注册用户未完成引导时的基本信息 **/
	// 大街帐号id
	private int djId;

	// 姓名
	private String name;

	// 性别
	private int gender;

	// 邮箱
	private String email;

	/** 大街帐号其它基本信息 **/

	private String mobile;

	private String avatar;

	private String location;

	private String corp;

	private String position;

	private int jobType;

	private int industry;

	public int getDjId() {
		return djId;
	}

	public void setDjId(int djId) {
		this.djId = djId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getJobType() {
		return jobType;
	}

	public void setJobType(int jobType) {
		this.jobType = jobType;
	}

	public int getIndustry() {
		return industry;
	}

	public void setIndustry(int industry) {
		this.industry = industry;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
