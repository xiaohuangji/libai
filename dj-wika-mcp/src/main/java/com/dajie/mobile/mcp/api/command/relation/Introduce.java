/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.relation;

import java.util.List;
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
import com.google.gson.reflect.TypeToken;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 12:05:16 PM
 */

public class Introduce extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(Introduce.class);

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
    	String sourceId=stringParams.get("sourceId");
    	String destId=stringParams.get("destId");
    	List<Integer> destIds = McpUtils.gson.fromJson(destId,  new TypeToken<List<Integer>>(){}.getType());  
    	String content=stringParams.get("content");

      	Object result = null;
        ApiResult apiResult = null;
    	try{
    		 long t = System.currentTimeMillis();
    		 result=relationService.introduce(userId, Integer.valueOf(sourceId),destIds, content);
    		 McpUtils.rpcTimeCost(t, "relation.introduce");
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
