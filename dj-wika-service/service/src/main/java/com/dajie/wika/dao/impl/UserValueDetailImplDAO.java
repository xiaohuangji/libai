package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.UserValueDetailDAO;
import com.dajie.wika.model.UserValue;
import com.dajie.wika.model.UserValueDetail;

@Component("userValueDetailDAO")
public class UserValueDetailImplDAO  extends Dao implements UserValueDetailDAO{

	private static final String SQL_PREFIX = "com.dajie.wika.dao.UserValueDetailDAO.";
	
	public UserValueDetailImplDAO() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@Override
	public int incrUserValue(int userId, String type, int incrvalue) {
		// TODO Auto-generated method stub
		 Map<String, Object> map = new HashMap<String, Object>(3);
	     map.put("userId", userId);
	     map.put("type", type);
	     map.put("incrvalue", incrvalue);
		return update(SQL_PREFIX+"incrValue", map);
	}
	
	@Override
	public int updateUserValue(int userId, String type, int typevalue,int totalvalue) {
		// TODO Auto-generated method stub
		 Map<String, Object> map = new HashMap<String, Object>(4);
	     map.put("userId", userId);
	     map.put("type", type);
	     map.put("typevalue", typevalue);
	     map.put("totalvalue", totalvalue);
		 return update(SQL_PREFIX+"updateValue", map);
	}

	@Override
	public UserValueDetail getUserValueDetail(int userId) {
		// TODO Auto-generated method stub
		return (UserValueDetail) selectOne(SQL_PREFIX+"getUserValue", userId);
	}

	@Override
	public int initUserValue(int userId) {
		// TODO Auto-generated method stub
		return insert(SQL_PREFIX+"insert", userId);
	}

	@Override
	public List<UserValue> getCurUserValue(int offset,int limit){
		return (List<UserValue>)selectList(SQL_PREFIX+"getCurUserValue", new RowBounds(
                offset, limit));
	}

	@Override
	public Integer getRank(int userId) {
		// TODO Auto-generated method stub
		return (Integer)selectOne(SQL_PREFIX+"getRank", userId);
	}

	@Override
	public Integer getTotalUserCount() {
		// TODO Auto-generated method stub
		return (Integer)selectOne(SQL_PREFIX+"getTotalUserCount");
	}
}
