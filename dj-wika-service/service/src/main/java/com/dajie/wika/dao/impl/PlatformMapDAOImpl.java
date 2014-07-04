package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.PlatformMapDAO;
import com.dajie.wika.model.PlatformMap;

@Component("platformMapDAO")
public class PlatformMapDAOImpl extends Dao implements PlatformMapDAO {
	private final String SQL_PREFIX = "com.dajie.wika.dao.PlatformMapDAO.";

	public PlatformMapDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@PostConstruct
	public void init() {
		this.initResource();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlatformMap> getPlatformMapByUserId(int userId) {
		return selectList(SQL_PREFIX + "getPlatformMapByUserId", userId);
	}

	@Override
	public PlatformMap getPlatformMapByUserIdAndType(int userId, int platformType) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("userId", userId);
		param.put("platformType", platformType);
		return (PlatformMap) selectOne(SQL_PREFIX
				+ "getPlatformMapByUserIdAndType", param);
	}

	@Override
	public PlatformMap getPlatformMap(String platformUid, int platformType) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("platformUid", platformUid);
		param.put("platformType", platformType);
		return (PlatformMap) selectOne(SQL_PREFIX + "getPlatformMap", param);
	}

	@Override
	public int insert(PlatformMap map) {
		return insert(SQL_PREFIX + "insertMap", map);
	}

	@Override
	public int update(PlatformMap map) {
		return update(SQL_PREFIX + "updatePlatformMap", map);
	}

	@Override
	public int delete(int userId, int platformType) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("userId", userId);
		param.put("platformType", platformType);
		return delete(SQL_PREFIX + "deleteByUserIdAndType", param);
	}

	@Override
	public int delete(String platformUid, int type) {
		Map<String, Object> param = new HashMap<String, Object>(2);
		param.put("platformUid", platformUid);
		param.put("platformType", type);
		return delete(SQL_PREFIX + "deleteByPlatformIdAndType", param);
	}

}
