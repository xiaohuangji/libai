package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.IdSequenceDAO;

@Component("idSequenceDAO")
public class IdSequenceDAOImpl extends Dao implements IdSequenceDAO {

	private final String SQL_PREFIX = "com.dajie.wika.dao.IdSequenceDAO.";

	public IdSequenceDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@PostConstruct
	public void init() {
		this.initResource();
	}

	@Override
	public long getCurId(int type) {
		// TODO Auto-generated method stub
		Object id = selectOne(SQL_PREFIX + "getCur", type);
		return (Long) id;
	}

	@Override
	public int updateToDB(long id, int type) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("id", id);
		map.put("type", type);
		return update(SQL_PREFIX + "updateToDB", map);
	}

}
