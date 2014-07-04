package com.dajie.wika.service.stub.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.wika.model.UserValueDetail;
import com.dajie.wika.model.wrapper.UserCountWrapper;
import com.dajie.wika.service.UserCountService;
import com.dajie.wika.service.stub.UserCountServiceStub;

@Service("userCountServiceStub")
public class UserCountServiceStubImpl implements UserCountServiceStub{

	private static final int TREND_GET_DAY=12;
	
	@Autowired
	private UserCountService userCountService;
	
	@Override
	public UserCountWrapper getUserCountWrapper(int userId,int visited) {
		// TODO Auto-generated method stub
		UserCountWrapper userCountWrapper=new UserCountWrapper(userId);
		userCountWrapper.setUserCountBase(userCountService.getUserCountBase(userId));
		userCountWrapper.setUserCountYesterday(userCountService.getUserCountYesterday(userId));
		UserValueDetail detail=userCountService.getUserValueDetail(userId);
		if(userId==visited){//it means someone is visiting his own profile
			userCountWrapper.setUserValueDetail(detail);
		}
		userCountWrapper.setUserValue(detail.getTotalValue());
		userCountWrapper.setUserValues(userCountService.getUserValueTrend(userId, 0, TREND_GET_DAY));
		userCountWrapper.setRank(userCountService.getUserValueRank(userId));
		return userCountWrapper;
	}

}
