package com.dajie.wika.service.stub;

import com.dajie.wika.model.wrapper.UserProfile;

public interface ProfileServiceStub {

	/**
	 * 获取用户基本信息
	 * @param userId 
	 * @param visitedId 查看者id
	 * @return
	 */
	public UserProfile getProfile(int userId,int visitedId);
}
