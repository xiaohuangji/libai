package com.dajie.wika.dao;

import java.util.List;

import com.dajie.wika.model.Message;

public interface MessageDAO {

	public int insert(Message message);
	
	public List<Message> getMessages(int userId,long timestamp,int limit);
}
