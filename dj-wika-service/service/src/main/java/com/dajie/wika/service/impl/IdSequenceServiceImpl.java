package com.dajie.wika.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.wika.cache.CacheKey;
import com.dajie.wika.cache.JedisCacheUtil;
import com.dajie.wika.dao.IdSequenceDAO;
import com.dajie.wika.dao.UserCountBaseDAO;
import com.dajie.wika.dao.UserValueDetailDAO;
import com.dajie.wika.service.IdSequenceService;

@Service("idSequenceService")
public class IdSequenceServiceImpl implements IdSequenceService{

	@Autowired
	IdSequenceDAO idSequenceDAO;
	
	@Autowired
	UserValueDetailDAO userValueDetailDAO;
	
	@Autowired
	UserCountBaseDAO userCountBaseDAO;
	
	private static final int MSGID=1;
	
	private static final int USERID=2;
	
	@Autowired
	private JedisCacheUtil jedisCacheUtil;
	
	@Override
	public long getNextMsgId() {
		return getIdseqId(MSGID);
	}

	@Override
	public int getNextUserId() {
		// TODO Auto-generated method stub
		int userId=new Long(getIdseqId(USERID)).intValue();
		//获取userId时初始化身份计算记录
		userValueDetailDAO.initUserValue(userId);
		userCountBaseDAO.initUserBaseCount(userId);
		return userId;
	}
	
	private long getIdseqId(int type){
		Long id=jedisCacheUtil.incr(CacheKey.getIdseqkey(type));
		if(id==1){//表示缓存不存在或失效，重新从数据库加载,并填入缓存
			id=idSequenceDAO.getCurId(type)+1;
			jedisCacheUtil.set(CacheKey.getIdseqkey(type), String.valueOf(id));
		}else{
			idSequenceDAO.updateToDB(id, type);
		}
		return id;
	}

}
