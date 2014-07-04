package com.dajie.wika.dao;

import java.util.List;

import com.dajie.wika.model.UserDevice;

public interface UserDeviceDAO {

	int setUserDevice(UserDevice userDevice);

	List<UserDevice> getDeviceByUserId(int userId);

	UserDevice getDeviceByUserIdAndType(int userId, int deviceType);

	UserDevice getDeviceByTokenAndType(String deviceToken, int deviceType);

	int updateUserDevice(UserDevice userDevice);

	int deleteByUserIdAndType(int userId, int deviceType);

	int deleteByTokenAndType(int deviceToken, int deviceType);

	int deleteByUserId(int userId);
}
