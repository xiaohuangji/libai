package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.UserCountBaseDAO;
import com.dajie.wika.model.UserCountBase;

@Component("userCountBaseDAO")
public class UserCountBaseDAOImpl extends Dao implements UserCountBaseDAO {

	private static final String SQL_PREFIX = "com.dajie.wika.dao.UserCountBaseDAO.";
	
	public UserCountBaseDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@Override
	public UserCountBase getUserBase(int userId) {
		return (UserCountBase)selectOne(SQL_PREFIX+"getUserCount", userId);
	}

	@Override
	public int incrUserBaseCount(int userId, String type,int incrValue) {
		// TODO Auto-generated method stub
		 Map<String, Object> map = new HashMap<String, Object>(3);
	     map.put("userId", userId);
	     map.put("type", type);
	     map.put("incrvalue", incrValue);
		 return update(SQL_PREFIX+"incrValue", map);
	}
	
	@Override
	public int initUserBaseCount(int userId) {
		// TODO Auto-generated method stub
		return insert(SQL_PREFIX+"insert", userId);
	}

}
