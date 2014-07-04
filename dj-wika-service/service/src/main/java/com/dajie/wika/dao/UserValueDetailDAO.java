package com.dajie.wika.dao;

import java.util.List;

import com.dajie.wika.model.UserValue;
import com.dajie.wika.model.UserValueDetail;

public interface UserValueDetailDAO {

	public int incrUserValue(int userId,String type,int value);
	
	public int updateUserValue(int userId, String type, int typevalue,int totalvalue);
	
	public UserValueDetail getUserValueDetail(int userId);
	
	public int initUserValue(int userId);
	
	public List<UserValue> getCurUserValue(int offset,int limit);
	
	public Integer getRank(int userId);
	
	public Integer getTotalUserCount();
	
}
