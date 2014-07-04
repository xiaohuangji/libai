/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.relation;

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
import com.dajie.wika.service.RelationService;
import com.dajie.wika.service.stub.RelationServiceStub;

/**
 * 
 * @author li.hui
 * 
 */
public class Follow extends AbstractApiCommand implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(Follow.class);

	private RelationServiceStub relationServiceStub;

	public void setRelationServiceStub(RelationServiceStub relationServiceStub) {
		this.relationServiceStub = relationServiceStub;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(relationServiceStub, "relationService is required");
	}

	@Override
	public ApiResult onExecute(ApiCommandContext context) {
		// 取参数
		int userId = context.getUserId();
		Map<String, String> stringParams = context.getStringParams();
		int followId = Integer.parseInt(stringParams.get("followId"));
		Object result = null;
		ApiResult apiResult = null;

		// 执行RPC调用
		try {
			long t = System.currentTimeMillis();
			result = relationServiceStub.follow(userId, followId);
			McpUtils.rpcTimeCost(t, "relationServiceStub.follow");
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
