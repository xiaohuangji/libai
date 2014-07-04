/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.account;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ApiResultCode;
import com.dajie.mobile.mcp.passport.entity.UserPassport;
import com.dajie.mobile.mcp.passport.service.PassportService;
import com.dajie.mobile.mcp.utils.McpUtils;
import com.dajie.wika.constants.SourceTypeConstant;
import com.dajie.wika.model.wrapper.LoginReturn;
import com.dajie.wika.model.wrapper.RegisterReturn;
import com.dajie.wika.service.AccountService;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 12:00:13 PM
 */

public class Register extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(Register.class);

    private PassportService passportService;

    private AccountService accountService;

    private final int DEFAULT_CALL_SOURCE = SourceTypeConstant.APP_SOURCE;
    
    public void setPassportService(PassportService passportService) {
        this.passportService = passportService;
    }
    
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(passportService, "passportService is required");
        Assert.notNull(accountService, "accountService is required");
    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
        // 取参数
        Map<String, String> stringParams = context.getStringParams();
        String mobile = stringParams.get("mobile");
        String verifyCode = stringParams.get("verifyCode");
        String password = stringParams.get("password");
        int source = DEFAULT_CALL_SOURCE;
        try {
			source = Integer.parseInt(stringParams.get("source"));
		} catch (Exception e) {
			// TODO: handle exception
		}
        Object result = null;
        ApiResult apiResult = null;

        // 执行RPC调用   
        RegisterReturn us = null;
        try {
            long t = System.currentTimeMillis();
            us = accountService.register(mobile, verifyCode, password,source);
            McpUtils.rpcTimeCost(t, "accountService.register");
        } catch (Exception e) {
            // 异常记录日志， 返回错误信息
            logger.error("RPC error ", e);
            apiResult = new ApiResult(ApiResultCode.E_SYS_RPC_ERROR);
            return apiResult;
        }
        
        if (us == null) {
            return new ApiResult(ApiResultCode.E_BIZ_REGISTER_FAILED);
        }

        if (us.getUserId() == 0) {
            return new ApiResult(ApiResultCode.SUCCESS, us);
        }
        
        int userId = us.getUserId();

        UserPassport userPassport = new UserPassport();

        userPassport.setUserId(userId);
        userPassport.setCreateTime(System.currentTimeMillis());
        String userSecretKey = McpUtils.generateSecretKey();
        userPassport.setUserSecretKey(userSecretKey);
        String ticket = passportService.getTicketByUserId(userId);
        if (StringUtils.isEmpty(ticket)) {
            ticket = passportService.createTicket(userPassport);
        } else {
            UserPassport up = passportService.getPassportByTicket(ticket);
            if (up == null) {
                // 票已经失效,重新创建
                //              passportService.destroyTicket(ticket);
                ticket = passportService.createTicket(userPassport);
            } else {
                // 用以前的key
                userPassport.setUserSecretKey(up.getUserSecretKey());
            }
        }

        if (StringUtils.isEmpty(ticket)) {
            return new ApiResult(ApiResultCode.E_BIZ_REGISTER_FAILED);
        }

        userPassport.setTicket(ticket);

        if (logger.isDebugEnabled()) {
            logger.debug(String.format("[%s]:[userPassport=%s]", this.getClass().getName(),
                    userPassport));
        }
        apiResult = new ApiResult(ApiResultCode.SUCCESS, buildResult(userPassport, us));
        return apiResult;

    }
    
    private Map<String, Object> buildResult(UserPassport userPassport, RegisterReturn us) {
        Map<String, Object> rtMap = new HashMap<String, Object>();
        rtMap.put("t", userPassport.getTicket());        
        rtMap.put("secretKey", userPassport.getUserSecretKey());
        rtMap.put("time", System.currentTimeMillis());
        rtMap.put("userId", us.getUserId());
        rtMap.put("returnCode", us.getReturnCode());
        rtMap.put("shortUrl", us.getProfileShortUrl());
        return rtMap;
    }

}
