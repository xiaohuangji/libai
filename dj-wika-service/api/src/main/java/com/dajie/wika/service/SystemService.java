package com.dajie.wika.service;


public interface SystemService {
	
	/**
	 * 反馈内容
	 * @param userId
	 * @param content
	 * @return
	 */
	public int feedback(int userId,String content,String contact);
	
}
