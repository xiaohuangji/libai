/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.relation;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ApiResultCode;
import com.dajie.mobile.mcp.utils.McpUtils;
import com.dajie.wika.service.RelationService;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 12:05:00 PM
 */

public class SayHi extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(SayHi.class);

    private RelationService relationService;
    
    public RelationService getRelationService() {
		return relationService;
	}

	public void setRelationService(RelationService relationService) {
		this.relationService = relationService;
	}

	@Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
     	Map<String, String> stringParams = context.getStringParams();
    	int userId=context.getUserId();
    	String toId=stringParams.get("toId");

      	Object result = null;
        ApiResult apiResult = null;
    	try{
    		 long t = System.currentTimeMillis();
    		 result=relationService.sayHi(userId,Integer.valueOf(toId));
    		 McpUtils.rpcTimeCost(t, "relation.sayhi");
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
