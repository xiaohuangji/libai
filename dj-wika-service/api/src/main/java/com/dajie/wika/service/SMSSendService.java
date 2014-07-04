package com.dajie.wika.service;

public interface SMSSendService {
	/**
	 * 短信下发
	 * 
	 * @param mobile
	 * @param msg
	 * @return
	 */
	int smsSend(String mobile, String msg);
}
