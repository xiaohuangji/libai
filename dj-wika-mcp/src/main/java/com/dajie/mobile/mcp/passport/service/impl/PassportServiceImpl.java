/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.passport.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.dajie.framework.cache.StringCache;
import com.dajie.mobile.mcp.passport.entity.UserPassport;
import com.dajie.mobile.mcp.passport.service.PassportService;
import com.dajie.mobile.mcp.passport.utils.TicketUtils;

/**
 * @author wei.cheng
 * 
 */
public class PassportServiceImpl implements PassportService, InitializingBean {

	private static final Logger logger = LoggerFactory
			.getLogger(PassportServiceImpl.class);

	private StringCache<Map<String, Object>, UserPassport> passportCache;

	public void setPassportCache(
			StringCache<Map<String, Object>, UserPassport> passportCache) {
		this.passportCache = passportCache;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean destroyTicket(String ticket) {
		boolean rt = false;
		if (StringUtils.isEmpty(ticket)) {
			return rt;
		}
		UserPassport userPassport = getPassportByTicket(ticket);
		if (userPassport != null) {
			int uid = TicketUtils.decryptTicket(ticket);
			if (uid <= 0) {
				return rt;
			}
			try {
				Map<String, Object> passportKey = new HashMap<String, Object>();
				passportKey.put("userId", uid);
				passportCache.clear(passportKey);
				rt = true;
			} catch (Exception e) {
				logger.error("[" + this.getClass().getName() + "]", e);
				rt = false;
			}

		}
		return rt;
	}

	@Override
	public UserPassport getPassportByTicket(String ticket) {
		UserPassport userPassport = null;
		if (StringUtils.isEmpty(ticket)) {
			return userPassport;
		}
		int uid = TicketUtils.decryptTicket(ticket);
		if (uid <= 0) {
			return userPassport;
		}
		try {
			Map<String, Object> passportKey = new HashMap<String, Object>();
			passportKey.put("userId", uid);
			userPassport = (UserPassport) passportCache.get(passportKey);
		} catch (Exception e) {
			logger.error("[" + this.getClass().getName() + "]", e);
		}
		if (userPassport == null)
			return userPassport;
		if (userPassport.getTicket() == null)
			return null;
		if (!StringUtils.equals(ticket, userPassport.getTicket()))
			return null;

		return userPassport;
	}

	@Override
	public int getUserIdByTicket(String ticket) {
		int userId = 0;
		UserPassport userPassport = getPassportByTicket(ticket);
		if (userPassport == null) {
			return userId;
		}
		userId = userPassport.getUserId();
		return userId;
	}

	@Override
	public String createTicket(UserPassport userPassport) {
		String ticket = null;
		if (userPassport == null || userPassport.getUserId() == 0) {
			return ticket;
		}
		ticket = TicketUtils.generateTicket(userPassport.getUserId());
		userPassport.setTicket(ticket);
		if (!StringUtils.isEmpty(ticket)) {
			try {
				Map<String, Object> passportKey = new HashMap<String, Object>();
				passportKey.put("userId", userPassport.getUserId());
				passportCache.put(passportKey, userPassport);
			} catch (Exception e) {
				logger.error("[" + this.getClass().getName() + "]", e);
			}
		}
		return ticket;
	}

	@Override
	public String getTicketByUserId(int userId) {
		String ticket = null;

		if (userId != 0) {
			UserPassport userPassport = null;
			try {
				Map<String, Object> passportKey = new HashMap<String, Object>();
				passportKey.put("userId", userId);
				userPassport = (UserPassport) passportCache.get(passportKey);
			} catch (Exception e) {
				logger.error("[" + this.getClass().getName() + "]", e);
			}
			if (userPassport != null) {
				ticket = userPassport.getTicket();
				int uid = TicketUtils.decryptTicket(ticket);
				if (uid <= 0) {
					return null;
				}
				if (uid != userId) {
					return null;
				}
			}
		}
		return ticket;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(passportCache, "passportCache is required!");
	}

}
