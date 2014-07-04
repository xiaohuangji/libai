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
import com.dajie.wika.service.stub.ProfileServiceStub;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Dec 16, 2013 12:02:12 PM
 */

public class GetUserProfile extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(GetUserProfile.class);

    private ProfileServiceStub profileServiceStub;
    
    
    public void setProfileServiceStub(ProfileServiceStub profileServiceStub) {
		this.profileServiceStub = profileServiceStub;
	}

	@Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
       	Map<String, String> stringParams = context.getStringParams();
    	Integer visitedId=context.getUserId();
    	if(visitedId==null){//如果未传票，说明是无登陆访问
    		visitedId=0;
    	}
    	String id=stringParams.get("userId");
    	int userId=visitedId;
    	if(id!=null){//如果不传userId，表示查看自己的profile。
    		userId=Integer.valueOf(id);
    	}

      	Object result = null;
        ApiResult apiResult = null;
    	try{
    		 long t = System.currentTimeMillis();
    		 result=profileServiceStub.getProfile(userId,visitedId);
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
