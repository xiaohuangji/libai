package com.dajie.wika.service;

import com.dajie.wika.model.UserOccupation;


/**
 * 职场信息相关
 * xing.feng
 */
public interface OccupationService {

	public int updateOccupation(UserOccupation userOccupation);
	
	public UserOccupation getOccupation(int userId);
}
