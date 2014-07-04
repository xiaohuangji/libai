/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.system;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ApiResultCode;
import com.dajie.mobile.mcp.utils.McpUtils;
import com.dajie.wika.service.SystemService;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 12:06:09 PM
 */

public class Feedback extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(Feedback.class);

    private SystemService systemService;
    
    public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	@Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
        // TODO Auto-generated method stub
    	Map<String, String> stringParams = context.getStringParams();
    	int userId=context.getUserId();

        String content = stringParams.get("content");
        String contact = stringParams.get("contact");
      	Object result = null;
        ApiResult apiResult = null;
    	try{
    		 long t = System.currentTimeMillis();
    		 result=systemService.feedback(userId, content, contact);
    		 McpUtils.rpcTimeCost(t, "system.feedback");
    	}
    	catch (Exception e) {
            // 异常记录日志， 返回错误信息
            logger.error("RPC error ", e);
            apiResult = new ApiResult(ApiResultCode.E_SYS_RPC_ERROR);
            return apiResult;
        }
    	
    	apiResult = new ApiResult(ApiResultCode.SUCCESS, result);
        return apiResult;
    }

}
