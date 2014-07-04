package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.RelationFollowingDAO;

@Component("relationFollwingDAO")
public class RelationFollowingDAOImpl extends Dao implements
		RelationFollowingDAO {

	private final String SQL_PREFIX = "com.dajie.wika.dao.RelationFollowingDAO.";

	public RelationFollowingDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@PostConstruct
	public void init() {
		this.initResource();
	}

	@Override
	public int insert(int userId, int followingId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("followingId", followingId);
		param.put("createTime", System.currentTimeMillis());
		return insert(SQL_PREFIX + "insert", param);
	}

	@Override
	public int delete(int userId, int followingId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("followingId", followingId);
		return delete(SQL_PREFIX + "delete", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getFollowings(int userId) {
		return selectList(SQL_PREFIX + "getFollowings", userId);
	}

	@Override
	public List<Integer> getFollowingsByPage(int userId, int lastFollowingId,
			int pageSize) {
		return null;
	}

	@Override
	public Integer getFollowingCountByInterval(int userId, long startTime,
			long endTime) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		return (Integer) selectOne(SQL_PREFIX + "getFollowingCountByInterval",
				param);
	}

	@Override
	public int isFollowing(int userId, int followingId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("followingId", followingId);
		return (Integer) selectOne(SQL_PREFIX + "isFollowing", param);
	}
}
