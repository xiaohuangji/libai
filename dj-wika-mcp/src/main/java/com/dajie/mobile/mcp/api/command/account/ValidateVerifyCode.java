/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.account;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ApiResultCode;
import com.dajie.mobile.mcp.utils.McpUtils;
import com.dajie.wika.service.AccountService;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 11:59:53 AM
 */

public class ValidateVerifyCode extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ValidateVerifyCode.class);

    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(accountService, "accountService is required");
    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
        // 取参数
        Map<String, String> stringParams = context.getStringParams();
        String mobile = stringParams.get("mobile");
        String verifyCode = stringParams.get("verifyCode");
        Object result = null;
        ApiResult apiResult = null;

        // 执行RPC调用       
        try {
            long t = System.currentTimeMillis();
            result = accountService.validateVerifyCode(mobile, verifyCode);
            McpUtils.rpcTimeCost(t, "accountService.validateVerifyCode");
        } catch (Exception e) {
            // 异常记录日志， 返回错误信息
            logger.error("RPC error ", e);
            apiResult = new ApiResult(ApiResultCode.E_SYS_RPC_ERROR);
            return apiResult;
        }

        // 正常返回接口数据
        apiResult = new ApiResult(ApiResultCode.SUCCESS, result);
        return apiResult;
    }

}
