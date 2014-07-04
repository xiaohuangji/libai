package com.dajie.wika.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.UserOccupationDAO;
import com.dajie.wika.model.UserOccupation;

@Component("userOccupationDAO")
public class UserOccupationDAOImpl extends Dao implements UserOccupationDAO {
	private final String SQL_PREFIX = "com.dajie.wika.dao.UserOccupationDAO.";

	public UserOccupationDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@PostConstruct
	public void init() {
		this.initResource();
	}

	@Override
	public int insert(UserOccupation userOccupation) {
		return insert(SQL_PREFIX + "insertUserOccupation", userOccupation);
	}

	@Override
	public UserOccupation getByUserId(int userId) {
		return (UserOccupation) selectOne(SQL_PREFIX + "getByUserId", userId);
	}

	@Override
	public int update(UserOccupation userOccupation) {
		return update(SQL_PREFIX + "updateUserOccupation", userOccupation);
	}

	@Override
	public int delete(int userId) {
		return delete(SQL_PREFIX + "delete", userId);
	}

}
