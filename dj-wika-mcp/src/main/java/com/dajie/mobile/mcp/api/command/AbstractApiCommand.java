/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.command;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ClientInfo;
import com.dajie.mobile.mcp.api.utils.StatLogUtil;
import com.dajie.mobile.mcp.constants.HttpConstants;

/**
 * @author wei.cheng
 * 
 */
public abstract class AbstractApiCommand implements ApiCommand {

    private static final Logger userAccesslogger = LoggerFactory.getLogger("mcp_user_access_log");

    private static final Logger statAccessLogger = LoggerFactory.getLogger("mcp_stat_access_log");

    /**
     * 子类不可重写
     */
    @Override
    public final ApiResult execute(ApiCommandContext context) {
        
        long starTime = System.currentTimeMillis();
       
        ApiResult apiResult = null;

        // ======statAccessLog 统计用 start======
        String methodName = context.getMethodName();      
        String extra1 = "";
        String extra2 = "";
        String extra3 = "";
        String extra4 = "";
        String extra5 = "";
        int value = 1;
        String identifier = "";
        Map<String, String> stringParams = context.getStringParams();
        String clientIp = stringParams.get(HttpConstants.CLIENT_IP);
        ClientInfo clientInfo = context.getClientInfo();
        // ======statAccessLog 统计用 end======

        this.beforeExecute(context);
        apiResult = this.onExecute(context);
        this.afterExecute(context, apiResult);

        // ======statAccessLog 统计用 start======
      
      

        String statLogStr = StatLogUtil.log(statAccessLogger, System.currentTimeMillis(),
                methodName, context.getUserId() + "", context.getMcpAppInfo().getAppId() + "",
                clientInfo, clientIp, "WikaServer", identifier, "1", value + "", extra1, extra2,
                extra3, extra4 , extra5, apiResult.getCode() + "");
        // ======statAccessLog 统计用 end======

        // ======userAccessLog mcp自用统计 start======
        // 在stataccess统计的基础上添加了返回结果和消耗时间，也是以“|”分割
        userAccesslogger.info(statLogStr + "|" + (System.currentTimeMillis() - starTime));
        // ======userAccessLog mcp自用统计 end======

        //        currentApiCommandContextHolder.remove();
        return apiResult;
    }

    protected void beforeExecute(ApiCommandContext context) {

    }

    protected void afterExecute(ApiCommandContext context, ApiResult apiResult) {

    }

    public abstract ApiResult onExecute(ApiCommandContext context);

}
