package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.RelationFriendDAO;

@Component("relationFriendDAO")
public class RelationFriendDAOImpl extends Dao implements RelationFriendDAO {

	private final String SQL_PREFIX = "com.dajie.wika.dao.RelationFriendDAO.";

	public RelationFriendDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@PostConstruct
	public void init() {
		this.initResource();
	}

	@Override
	public int insert(int userId, int friendId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("friendId", friendId);
		param.put("createTime", System.currentTimeMillis());
		return insert(SQL_PREFIX + "insert", param);
	}

	@Override
	public int delete(int userId, int friendId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("friendId", friendId);
		return delete(SQL_PREFIX + "delete", param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getFriends(int userId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		return selectList(SQL_PREFIX + "getFriends", param);
	}

	@Override
	public List<Integer> getFriendsByPage(int userId, int lastFriendId,
			int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
