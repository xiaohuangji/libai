package com.dajie.wika.service;

import java.util.List;

import com.dajie.wika.enums.UserCountUpdateEventEnum;
import com.dajie.wika.enums.UserValueUpdateEventEnum;
import com.dajie.wika.model.UserCountBase;
import com.dajie.wika.model.UserCountYesterday;
import com.dajie.wika.model.UserValue;
import com.dajie.wika.model.UserValueDetail;

/**
 * 用户计数相关服务
 * @author xing.feng
 *
 */
public interface UserCountService {
	
    /**
     * 更新userCountBase
     * @param userId
     * @param userCountEventType
     * @return
     */
	public int updateUserCountBase(int userId,UserCountUpdateEventEnum userCountUpdateEventEnum);

	/**
	 * 获取用户基本计数信息
	 * @param userId
	 * @return
	 */
	public UserCountBase getUserCountBase(int userId);
	
	/**
	 * 获取用户统计计数信息
	 * @param userId
	 * @return
	 */
	public UserCountYesterday getUserCountYesterday(int userId);
	
	/**
	 * 更新用户身价
	 * 由其他模块调此接口。内部处理各type对应的分数
	 * @param userId
	 * @return
	 */
	public int updateUserValue(int userId,UserValueUpdateEventEnum userValueUpdateEventEnum);
	
	/**
	 * 用户完善个人信息时需要根据完善程度进行身价更新，此接口是专门定制的
	 * @param userId
	 * @param userInfoNum
	 * @return
	 */
	public int modifyUserInfoValue(int userId,int userInfoNum);
	
	/**
	 * 获取用户身价详情
	 * @param userId
	 * @return
	 */
	public UserValueDetail getUserValueDetail(int userId);
	
	/**
	 * 获取用户身价走势数据
	 * @param userId
	 * offset limit 为分页参数
	 * @return
	 */
	public List<UserValue> getUserValueTrend(int userId,int offset,int limit);
	
	/**
	 * 获取用户最新的身价排名
	 * @param userId
	 * @return
	 */
	public float getUserValueRank(int userId);
	
	/**
	 * 将昨日的身价归档
	 * @return
	 */
	public int archiveYesterdayUserValue();
	
}
