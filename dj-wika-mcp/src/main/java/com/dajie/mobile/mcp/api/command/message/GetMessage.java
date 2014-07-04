/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.message;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ApiResultCode;
import com.dajie.mobile.mcp.utils.McpUtils;
import com.dajie.wika.service.stub.MessageServiceStub;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 12:01:22 PM
 */

public class GetMessage extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(GetMessage.class);

    private MessageServiceStub messageServiceStub;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
        // TODO Auto-generated method stub
    	Map<String, String> stringParams = context.getStringParams();
    	int userId=context.getUserId();

        String offset = stringParams.get("offset");
        String limit = stringParams.get("limit");
      	Object result = null;
        ApiResult apiResult = null;
    	try{
    		 long t = System.currentTimeMillis();
    		 result=messageServiceStub.getMessage(userId, Long.valueOf(offset), Integer.valueOf(limit));
    		 McpUtils.rpcTimeCost(t, "message.getMessage");
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


	public void setMessageServiceStub(MessageServiceStub messageServiceStub) {
		this.messageServiceStub = messageServiceStub;
	}

    
}
