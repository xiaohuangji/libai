/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.wika.cache;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CacheFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(CacheFactory.class);

	@Autowired
	private JedisCacheUtil jedisCacheUtil;

	public CacheFactory() {
	}

	/**
	 * 更新phonecode
	 */
	public void updatePhoneCode(String phone, String code) {
		if (logger.isInfoEnabled()) {
			logger.info("updatePhoneCode phone=" + phone + " ,code=" + code);
		}
		jedisCacheUtil.set(CacheKey.getPhoneCodeKey(phone), code, 60*10);
	}

	/**
	 * 获取phoneCode
	 */
	public String getPhoneCode(String phone) {
		String code = jedisCacheUtil.get(CacheKey.getPhoneCodeKey(phone));
		if (logger.isInfoEnabled()) {
			logger.info("getPhoneCode phone=" + phone + " ,code=" + code);
		}
		return code;
	}

	/**
	 * 更新phonecode次数
	 */
	public void updatePhoneCodeCount(String phone, String nowCount) {
		if (logger.isInfoEnabled()) {
			logger.info("updatePhoneCodeCount phone=" + phone + " , nowCount="
					+ nowCount);
		}
		if (NumberUtils.toInt(nowCount) == 0) {
			jedisCacheUtil.set(CacheKey.getPhoneCodeCountKey(phone), "1",
					3600 * 24);
		} else {
			jedisCacheUtil.setIncr(CacheKey.getPhoneCodeCountKey(phone));
		}

	}

	/**
	 * 获取phoneCode次数
	 */
	public String getPhoneCodeSendCount(String phone) {
		String count = jedisCacheUtil.get(CacheKey.getPhoneCodeCountKey(phone));
		if (logger.isInfoEnabled()) {
			logger.info("getPhoneCodeSendCount phone=" + phone + " ,count="
					+ count);
		}
		return count;
	}
}
