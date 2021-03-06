/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.account;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
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
 * @version Dec 16, 2013 12:00:27 PM
 */

public class UpdateUserInfo extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(UpdateUserInfo.class);

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
        int userId = context.getUserId();
        Map<String, String> stringParams = context.getStringParams();
        String name = stringParams.get("name");
        String email = stringParams.get("email");
        int gender = NumberUtils.toInt(stringParams.get("gender"));
        String location = stringParams.get("location");
        String introduce = stringParams.get("introduce");
        String corp = stringParams.get("corp");
        int industry =NumberUtils.toInt(stringParams.get("industry"));
        String position = stringParams.get("position");
        String department = stringParams.get("department");  
        int jobType = NumberUtils.toInt(stringParams.get("jobType"));     
        Object result = null;
        ApiResult apiResult = null;

        // 执行RPC调用       
        try {
            long t = System.currentTimeMillis();
            result = accountService.updateUserInfo(userId, null, name, email, gender, location, introduce, 0, corp, industry, position, jobType, department);
            McpUtils.rpcTimeCost(t, "accountService.updateUserInfo");
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
