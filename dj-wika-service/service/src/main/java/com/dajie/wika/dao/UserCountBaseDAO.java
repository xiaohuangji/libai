package com.dajie.wika.dao;

import com.dajie.wika.model.UserCountBase;

public interface UserCountBaseDAO {

	public UserCountBase getUserBase(int userId);
	
	public int incrUserBaseCount(int userId,String type,int incrValue);
	
	public int initUserBaseCount(int userId);
}
