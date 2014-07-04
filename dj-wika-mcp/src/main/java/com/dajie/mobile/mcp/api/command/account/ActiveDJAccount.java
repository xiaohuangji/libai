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
import com.dajie.wika.constants.SourceTypeConstant;
import com.dajie.wika.service.AccountService;

public class ActiveDJAccount extends AbstractApiCommand implements InitializingBean  {

    private static final Logger logger = LoggerFactory.getLogger(BindMobile.class);

    private AccountService accountService;
    
    private final int DEFAULT_CALL_SOURCE = SourceTypeConstant.APP_SOURCE;
    
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
        String mobile = stringParams.get("mobile");
        String verifyCode = stringParams.get("verifyCode");
        String password = stringParams.get("password");
        
        int source = DEFAULT_CALL_SOURCE;

		try {
			source = Integer.parseInt(stringParams.get("source"));
		} catch (Exception e) {
		}

        Object result = null;
        ApiResult apiResult = null;

        // 执行RPC调用       
        try {
            long t = System.currentTimeMillis();
            result = accountService.activeDJAccount(userId, mobile, verifyCode, password,source);
            McpUtils.rpcTimeCost(t, "accountService.activeDJAccount");
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
