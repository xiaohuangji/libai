package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.RelationFollowerDAO;

@Component("relationFollowerDAO")
public class RelationFollowerDAOImpl extends Dao implements RelationFollowerDAO {

	private final String SQL_PREFIX = "com.dajie.wika.dao.RelationFollowerDAO.";

	public RelationFollowerDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@PostConstruct
	public void init() {
		this.initResource();
	}

	@Override
	public int insert(int userId, int followerId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("followerId", followerId);
		param.put("createTime", System.currentTimeMillis());
		return insert(SQL_PREFIX + "insert", param);
	}

	@Override
	public int delete(int userId, int followerId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("followerId", followerId);
		return delete(SQL_PREFIX + "delete", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getFollowers(int userId) {
		return selectList(SQL_PREFIX + "getFollowers", userId);
	}

	@Override
	public List<Integer> getFollowersByPage(int userId, int lastFollowerId,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getFollowerCountByInterval(int userId, long startTime,
			long endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		return (Integer) selectOne(SQL_PREFIX + "getFollowerCountByInterval",
				param);
	}

	@Override
	public int isFollower(int userId, int followerId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("followerId", followerId);
		return (Integer) selectOne(SQL_PREFIX + "isFollower", param);
	}

	@Override
	public int getNewFollowerCount(int userId) {
		return (Integer) selectOne(SQL_PREFIX + "getNewFollowerCount",userId);
	}

	@Override
	public int cleanNewFollower(int userId) {
		return (Integer)update(SQL_PREFIX + "cleanNewFollower",userId);
	}
}
