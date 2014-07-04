/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ApiResultCode;
import com.dajie.mobile.mcp.utils.McpUtils;
import com.dajie.wika.service.MessageService;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 12:01:22 PM
 */

public class GetUnreadMsgCount extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(GetUnreadMsgCount.class);

    private MessageService messageService;
    
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
        // TODO Auto-generated method stub
    	int userId=context.getUserId();    
    	Object result = null;
        ApiResult apiResult = null;
    	try{
    		 long t = System.currentTimeMillis();
    		 result=messageService.getUnReadMessageCount(userId);
    		 McpUtils.rpcTimeCost(t, "message.getUnreadMessageCount");
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

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
    
}
