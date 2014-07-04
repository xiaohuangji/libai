/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.profile;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ApiResultCode;
import com.dajie.mobile.mcp.utils.McpUtils;
import com.dajie.wika.service.stub.UserCountServiceStub;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 12:02:23 PM
 */

public class GetUserCount extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(GetUserCount.class);

    private UserCountServiceStub userCountServiceStub;
    
    
    public void setUserCountServiceStub(UserCountServiceStub userCountServiceStub) {
		this.userCountServiceStub = userCountServiceStub;
	}

	@Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
       	Map<String, String> stringParams = context.getStringParams();
    	int visitedId=context.getUserId();
    	String id=stringParams.get("userId");
    	int userId=visitedId;
    	if(id!=null){//如果不传userId，表示查看自己的profile。
    		userId=Integer.valueOf(id);
    	}

      	Object result = null;
        ApiResult apiResult = null;
    	try{
    		 long t = System.currentTimeMillis();
    		 result=userCountServiceStub.getUserCountWrapper(userId,visitedId);
    		 McpUtils.rpcTimeCost(t, "profile.getusercount");
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
