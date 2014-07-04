package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.UserBaseDAO;
import com.dajie.wika.model.UserBase;
import com.dajie.wika.service.utils.SecurityUtils;

@Component("userBaseDAO")
public class UserBaseDAOImpl extends Dao implements UserBaseDAO {

	private final String SQL_PREFIX = "com.dajie.wika.dao.UserBaseDAO.";

	public UserBaseDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@PostConstruct
	public void init() {
		this.initResource();
	}

	@Override
	public int insert(UserBase userBase) {
		// 加密后插入
		encryptUserBase(userBase);
		int insertReturn = insert(SQL_PREFIX + "insertUserBase", userBase);
		// 解密后再使用
		decryptUserBase(userBase);
		return insertReturn;
	}

	@Override
	public UserBase getUserBaseById(int userId) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("userId", userId);
		UserBase userBase = (UserBase) selectOne(
				SQL_PREFIX + "getUserBaseById", map);
		decryptUserBase(userBase);
		return userBase;
	}

	@Override
	public UserBase getUserBaseByMobile(String mobile) {
		Map<String, Object> map = new HashMap<String, Object>(1);
		map.put("mobile", encryptMobile(mobile));
		UserBase userBase = (UserBase) selectOne(SQL_PREFIX
				+ "getUserBaseByMobile", map);
		decryptUserBase(userBase);
		return userBase;
	}

	@Override
	public int update(UserBase userBase) {
		encryptUserBase(userBase);
		int updateReturn = update(SQL_PREFIX + "updateUserBase", userBase);
		decryptUserBase(userBase);
		return updateReturn;
	}

	@Override
	public int updatePwd(int userId, String pwd) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("userId", userId);
		map.put("password", pwd);
		return update(SQL_PREFIX + "updatePwd", map);
	}

	@Override
	public int updateMobile(int userId, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("userId", userId);
		map.put("mobile", encryptMobile(mobile));
		return update(SQL_PREFIX + "updateMobile", map);
	}

	@Override
	public int updateWikaTemplate(int userId, int wikaTemplate) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("userId", userId);
		map.put("wikaTemplate", wikaTemplate);
		return update(SQL_PREFIX + "updateWikaTemplate", map);
	}

	@Override
	public int updateUserQRCode(int userId, int faceCodeType, String faceQRCode) {
		Map<String, Object> map = new HashMap<String, Object>(3);
		map.put("userId", userId);
		map.put("faceCodeType", faceCodeType);
		map.put("faceQRCode", faceQRCode);
		return update(SQL_PREFIX + "updateUserQRCode", map);
	}

	@Override
	public int updateActivition(int userId, int activation) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("userId", userId);
		map.put("activation", activation);
		return update(SQL_PREFIX + "updateActivition", map);
	}

	@Override
	public int updateAvatar(int userId, String avatar) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("userId", userId);
		map.put("avatar", avatar);
		return update(SQL_PREFIX + "updateAvatar", map);
	}

	@Override
	public int updateAvatarAndQRCode(int userId, String avatar,
			int faceCodeType, String faceQRCode) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("userId", userId);
		map.put("avatar", avatar);
		map.put("faceCodeType", faceCodeType);
		map.put("faceQRCode", faceQRCode);
		return update(SQL_PREFIX + "updateAvatarAndQRCode", map);
	}

	@Override
	public int updateUserBaseNoAvatar(UserBase userBase) {
		encryptUserBase(userBase);
		int updateReturn = update(SQL_PREFIX + "updateUserBaseNoAvatar", userBase);
		decryptUserBase(userBase);
		return updateReturn;
	}

	@Override
	public int updateMobileAndPwd(int userId, String mobile, String password) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("mobile", encryptMobile(mobile));
		param.put("password", password);
		return update(SQL_PREFIX + "updateMobileAndPwd", param);
	}

	@Override
	public int cleanUnconfirmedMobile(String mobile) {
		String mobileEnCode = encryptMobile(mobile);
		return update(SQL_PREFIX + "cleanUnconfirmedMobile", mobileEnCode);
	}

	/***** 手机号和邮箱 加密解密 **/
	private void encryptUserBase(UserBase userBase) {
		if (null == userBase) {
			return;
		} else {
			String encodeMobile = SecurityUtils.encodePhone(userBase
					.getMobile());
			String encodeEmail = SecurityUtils
					.encryptEmail(userBase.getEmail());
			userBase.setMobile(encodeMobile);
			userBase.setEmail(encodeEmail);
		}
	}

	private void decryptUserBase(UserBase userBase) {
		if (null == userBase) {
			return;
		} else {
			String decodeMobile = SecurityUtils.decodePhone(userBase
					.getMobile());
			String decodeEmail = SecurityUtils
					.decryptEmail(userBase.getEmail());
			userBase.setMobile(decodeMobile);
			userBase.setEmail(decodeEmail);
		}
	}

	private String encryptMobile(String mobile) {
		return SecurityUtils.encodePhone(mobile);
	}

	private String decryptMobile(String mobile) {
		return SecurityUtils.decodePhone(mobile);
	}
}
