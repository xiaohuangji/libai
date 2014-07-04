package com.dajie.wika.dao;

public interface IdSequenceDAO {
	
	public long getCurId(int type);
	
	public int updateToDB(long id,int type);
}
