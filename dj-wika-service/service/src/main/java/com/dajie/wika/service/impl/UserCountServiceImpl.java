package com.dajie.wika.service.impl;

import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.common.dubbo.tolerance.Idempotent;
import com.dajie.wika.cache.CacheKey;
import com.dajie.wika.cache.JedisCacheUtil;
import com.dajie.wika.constants.returncode.ResultCodeConstant;
import com.dajie.wika.dao.RelationFollowerDAO;
import com.dajie.wika.dao.RelationFollowingDAO;
import com.dajie.wika.dao.UserCountBaseDAO;
import com.dajie.wika.dao.UserValueDetailDAO;
import com.dajie.wika.dao.UserValueTrendDAO;
import com.dajie.wika.enums.UserCountUpdateEventEnum;
import com.dajie.wika.enums.UserValueUpdateEventEnum;
import com.dajie.wika.model.UserCountBase;
import com.dajie.wika.model.UserCountYesterday;
import com.dajie.wika.model.UserValue;
import com.dajie.wika.model.UserValueDetail;
import com.dajie.wika.service.UserCountService;
import com.dajie.wika.service.utils.TimeUtil;
import com.dajie.wika.service.utils.WikaGson;

@Service("userCountService")
public class UserCountServiceImpl implements UserCountService {

	/**
	 * 只保留30天的记录
	 */
	private static final int TREND_RESERVE_DAY=30;
	
	@Autowired
	private UserValueDetailDAO userValueDetailDAO;

	@Autowired
	private UserValueTrendDAO userValueTrendDAO;

	@Autowired
	private UserCountBaseDAO userCountBaseDAO;

	@Autowired
	private RelationFollowerDAO relationFollowerDAO;

	@Autowired
	private RelationFollowingDAO relationFollowingDAO;

	@Autowired
	private JedisCacheUtil jedisCacheUtil;

	@Override
	@Idempotent(value = false)
	public int updateUserCountBase(int userId,
			UserCountUpdateEventEnum type) {
		userCountBaseDAO.initUserBaseCount(userId);
		userCountBaseDAO.incrUserBaseCount(userId, UserCountUpdateEventEnum
				.getMysqlColumnByType(type),UserCountUpdateEventEnum.getValueByType(type));
		jedisCacheUtil.delete(CacheKey.getUserCountBaseKey(userId));
		return ResultCodeConstant.OP_FAIL;
	}

	@Override
	public UserCountBase getUserCountBase(int userId) {
		//
		String countBaseCache = jedisCacheUtil.get(CacheKey
				.getUserCountBaseKey(userId));
		if (countBaseCache != null) {
			return WikaGson.fromJson(countBaseCache, UserCountBase.class);
		} else {
			UserCountBase countBase = userCountBaseDAO.getUserBase(userId);
			if (countBase != null) {
				jedisCacheUtil.set(CacheKey.getUserCountBaseKey(userId),
						WikaGson.toJson(countBase));
			}
			return countBase;
		}
	}

	@Override
	public UserCountYesterday getUserCountYesterday(int userId) {
		// 一天计算一次放缓存，一天过期时间
		String countYesterdayCache = jedisCacheUtil.get(CacheKey
				.getUserCountYesterdaysKey(userId));
		if (countYesterdayCache != null) {
			return WikaGson.fromJson(countYesterdayCache,
					UserCountYesterday.class);
		} else {
			long startTime = TimeUtil.getStartTime(-1);
			long endTime = TimeUtil.getEndTime(-1);
			Integer yesterdayFollowerCount = relationFollowerDAO
					.getFollowerCountByInterval(userId, startTime, endTime);
			Integer yesterdayFollowingCount = relationFollowingDAO
					.getFollowingCountByInterval(userId, startTime, endTime);
			UserCountYesterday countYesterday = new UserCountYesterday();
			countYesterday
					.setYesterdayFollowerCount(yesterdayFollowerCount == null ? 0
							: yesterdayFollowerCount);
			countYesterday
					.setYesterdayFollowingCount(yesterdayFollowingCount == null ? 0
							: yesterdayFollowerCount);
			// 获取今天剩余时间
			int remainOfToday = new Long(
					(TimeUtil.getEndTime(0) - System.currentTimeMillis()) / 1000)
					.intValue();
			jedisCacheUtil.set(CacheKey.getUserCountYesterdaysKey(userId),
					WikaGson.toJson(countYesterday), remainOfToday);
			return countYesterday;
		}
	}

	@Override
	@Idempotent(value = false)
	public int updateUserValue(int userId, UserValueUpdateEventEnum type) {
		// TODO Auto-generated method stu
		userValueDetailDAO.incrUserValue(userId,
				UserValueUpdateEventEnum.getMysqlColumnByType(type),
				UserValueUpdateEventEnum.getValueByType(type));
		return ResultCodeConstant.OP_SUCC;
	}
	
	@Override
	@Idempotent(value = false)
	public int modifyUserInfoValue(int userId,int userInfoNum){
		UserValueDetail detail=getUserValueDetail(userId);
		int unitPrice=UserValueUpdateEventEnum.getValueByType(UserValueUpdateEventEnum.ADDUSERINFO);
		int oldUserInfoNum=detail.getUserInfoValue()/unitPrice;
		if( userInfoNum > oldUserInfoNum ){
			int typevalue=userInfoNum*unitPrice;
			int totalvalue=detail.getTotalValue()+(userInfoNum-oldUserInfoNum)*unitPrice;
			userValueDetailDAO.updateUserValue(userId, UserValueUpdateEventEnum.getMysqlColumnByType(UserValueUpdateEventEnum.ADDUSERINFO), 
					typevalue, totalvalue);		
		}
		return ResultCodeConstant.OP_SUCC;
		
	}

	@Override
	public UserValueDetail getUserValueDetail(int userId) {
		return userValueDetailDAO.getUserValueDetail(userId);
	}

	@Override
	public List<UserValue> getUserValueTrend(int userId, int offset, int limit) {
		// TODO Auto-generated method stub
		return userValueTrendDAO.getUserValueTrend(userId, offset, limit);
	}
	
	@Override
	public float getUserValueRank(int userId){
		int rank=userValueDetailDAO.getRank(userId);
		int totalUser=userValueDetailDAO.getTotalUserCount();
		//这里以后加缓存，一天计算一次。
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		String k = df.format((float)rank / totalUser);
		float result= 1-new Float(k);
		return (result>=0.99?0.99f:result);
	}

	@Override
	public int archiveYesterdayUserValue() {
		if (!lockRedisLock()){//如果没有获取到锁，表示被其他程序执行，不执行归档操作。
			return ResultCodeConstant.OP_SUCC;
		}
		int offset=0;
		int limit=100;
		int size=0;
		do{
			List<UserValue> userValues=userValueDetailDAO.getCurUserValue(offset, limit);
			if(userValues==null){
				return 0;
			}
			size=userValues.size();
			userValueTrendDAO.insertUserValue(userValues);
		}while(size==limit);
		//remove records  30 days ago 
		userValueTrendDAO.removeOldUserValue(TREND_RESERVE_DAY);
		unlockReisLock();//释放锁
		return ResultCodeConstant.OP_SUCC;
	}
	
	private boolean lockRedisLock(){
		return jedisCacheUtil.setnx(CacheKey.getUserValueCrontabKey(), String.valueOf(System.currentTimeMillis()));
	}
	
	private void unlockReisLock(){
		 jedisCacheUtil.delete(CacheKey.getUserValueCrontabKey());
	}
}
