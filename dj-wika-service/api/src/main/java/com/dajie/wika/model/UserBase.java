package com.dajie.wika.model;

import java.io.Serializable;
import java.util.Date;

public class UserBase implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7775796384969518985L;

	/**
	 * userId
	 */
	private int userId;

	/**
	 * wikaId,可以修改自定义
	 */
	private String wikaId;

	/**
	 * 密码
	 */

	private transient String password;
	/**
	 * 名字
	 */
	private String name;

	/**
	 * 性别
	 */
	private int gender;

	/**
	 * 头像url
	 */
	private String avatar;

	/**
	 * 微卡模板
	 */
	private int wikaTemplate;

	/**
	 * 脸码的类型
	 */
	private int faceCodeType;
	/**
	 * wikaurl
	 */
	private String faceQRCode;

	/**
	 * email
	 */
	private String email;

	/**
	 * 生日
	 */
	private Date birth;
	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 手机号是否被确认
	 */
	private transient int mobileConfirmed;
	
	/**
	 * 所在城市
	 */
	private String location;

	/**
	 * 用户类型
	 * 
	 * @see {UserTypeConstant}
	 */
	private int type;

	/**
	 * 用户状态
	 * 
	 * @see {UserStatusConstant}
	 */
	private int status;

	/**
	 * 用户特征
	 */
	private int feature;

	private int activation;
	/**
	 * 用户注册时间
	 */
	private Date createTime;

	/**
	 * 简介
	 */
	private String introduce;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getWikaId() {
		return wikaId;
	}

	public void setWikaId(String wikaId) {
		this.wikaId = wikaId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getFaceQRCode() {
		return faceQRCode;
	}

	public void setFaceQRCode(String faceQRCode) {
		this.faceQRCode = faceQRCode;
	}

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public int getFeature() {
		return feature;
	}

	public void setFeature(int feature) {
		this.feature = feature;
	}

	public int getActivation() {
		return activation;
	}

	public void setActivation(int activation) {
		this.activation = activation;
	}

	public int getWikaTemplate() {
		return wikaTemplate;
	}

	public void setWikaTemplate(int wikaTemplate) {
		this.wikaTemplate = wikaTemplate;
	}

	public int getFaceCodeType() {
		return faceCodeType;
	}

	public void setFaceCodeType(int faceCodeType) {
		this.faceCodeType = faceCodeType;
	}

	public int getMobileConfirmed() {
		return mobileConfirmed;
	}

	public void setMobileConfirmed(int mobileConfirmed) {
		this.mobileConfirmed = mobileConfirmed;
	}

}
