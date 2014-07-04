package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.MessageDAO;
import com.dajie.wika.model.Message;

@Component("messageDAO")
public class MessageDAOImpl extends Dao implements MessageDAO{

	private static final String SQL_PREFIX = "com.dajie.wika.dao.MessageDAO.";
	
	public MessageDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

    @PostConstruct
    public void init() {
        this.initResource();
    }

    
	@Override
	public int insert(Message message) {
	   if(null == message) {
	       return 0;
	   }
	   return insert(SQL_PREFIX + "insert", message);
	}

	@Override
	public List<Message> getMessages(int userId, long timestamp, int limit) {
		// TODO Auto-generated method stub
		 Map<String, Object> map = new HashMap<String, Object>(5);
	        map.put("userId", userId);
	        //写死的一个时间
	        timestamp=timestamp/1000;//转换成秒级时间
	        map.put("timestamp", timestamp==0?1386475200L:timestamp);
	        map.put("limit", limit);
	     return (List<Message>) selectList(SQL_PREFIX + "getMessages", map);
	}

}
