package com.dajie.wika.dao;

import java.util.List;

import com.dajie.wika.model.UserValue;

public interface UserValueTrendDAO {

	public List<UserValue> getUserValueTrend(int userId,int offset,int limit);
	
	public int insertUserValue(List<UserValue> userValues);
	
	/**
	 * 清除老的记录
	 * @param day 表示清除day之前的纪录。
	 * day=30时表示uservaluetrend表只保留30天的数据，清除之前的
	 * @return
	 */
	public int removeOldUserValue(int day);
}
