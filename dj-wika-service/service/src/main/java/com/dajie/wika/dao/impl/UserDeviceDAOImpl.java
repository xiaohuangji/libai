package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.UserDeviceDAO;
import com.dajie.wika.model.UserDevice;

@Component("userDeviceDAO")
public class UserDeviceDAOImpl extends Dao implements UserDeviceDAO {

	private final String SQL_PREFIX = "com.dajie.wika.dao.UserDeviceDAO.";

	public UserDeviceDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@PostConstruct
	public void init() {
		this.initResource();
	}

	@Override
	public int setUserDevice(UserDevice userDevice) {
		return insert(SQL_PREFIX + "setUserDevice", userDevice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserDevice> getDeviceByUserId(int userId) {
		return selectList(SQL_PREFIX + "getDeviceByUserId", userId);
	}

	@Override
	public UserDevice getDeviceByUserIdAndType(int userId, int deviceType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("deviceType", deviceType);
		return (UserDevice) selectOne(SQL_PREFIX + "getDeviceByUserIdAndType",
				param);
	}

	@Override
	public UserDevice getDeviceByTokenAndType(String deviceToken, int deviceType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deviceToken", deviceToken);
		param.put("deviceType", deviceType);
		return (UserDevice) selectOne(SQL_PREFIX + "getDeviceByTokenAndType",
				param);
	}

	@Override
	public int updateUserDevice(UserDevice userDevice) {
		return update(SQL_PREFIX + "updateUserDevice", userDevice);
	}

	@Override
	public int deleteByUserIdAndType(int userId, int deviceType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("deviceType", deviceType);
		return delete(SQL_PREFIX + "deleteByUserIdAndType", param);
	}

	@Override
	public int deleteByTokenAndType(int deviceToken, int deviceType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deviceToken", deviceToken);
		param.put("deviceType", deviceType);
		return delete(SQL_PREFIX + "deleteByTokenAndType", param);
	}

	@Override
	public int deleteByUserId(int userId) {
		return delete(SQL_PREFIX + "deleteByUserId", userId);
	}

}
