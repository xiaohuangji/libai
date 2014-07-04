package com.dajie.wika.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.UserValueTrendDAO;
import com.dajie.wika.model.UserValue;
import com.dajie.wika.service.utils.TimeUtil;

@Component("userValueTrendDAO")
public class UserValueTrendImplDAO extends Dao implements UserValueTrendDAO {

	private static final String SQL_PREFIX = "com.dajie.wika.dao.UserValueTrendDAO.";
	
	public UserValueTrendImplDAO() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@Override
	public List<UserValue> getUserValueTrend(int userId, int offset, int limit) {
		// TODO Auto-generated method stub
		return (List<UserValue>)selectList(SQL_PREFIX+"getUserValueTrend", userId,new RowBounds(
                offset, limit));
	}

	@Override
	public int insertUserValue(List<UserValue> userValues) {
		if(userValues==null||userValues.size()==1){
			return 0;
		}
		for(UserValue userValue:userValues){
			userValue.setDayStartTime(TimeUtil.getStartTime(-1));
		}
		return insert(SQL_PREFIX+"insertUserValue", userValues);
	}

	@Override
	public int removeOldUserValue(int day) {
		// TODO Auto-generated method stub
		long dayStartTime = TimeUtil.getStartTime(-1*day);
		return delete(SQL_PREFIX+"removeOldUserValue", dayStartTime);
	}

}
