/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.profile;

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
import com.dajie.wika.service.CorpSearchService;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Jan 3, 2014 4:42:24 PM
 */

public class SearchCorpName extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ResolveShortUrl.class);

    private CorpSearchService corpSearchService;

    public void setCorpSearchService(CorpSearchService corpSearchService) {
        this.corpSearchService = corpSearchService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(corpSearchService, "corpSearchService is required");
    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
        Map<String, String> stringParams = context.getStringParams();

        String key = stringParams.get("key");
        Object result = null;
        ApiResult apiResult = null;
        try {
            long t = System.currentTimeMillis();
            result = corpSearchService.getCorpName(key);
            McpUtils.rpcTimeCost(t, "corpSearchService.getCorpName");
        } catch (Exception e) {
            // 异常记录日志， 返回错误信息
            logger.error("RPC error ", e);
            apiResult = new ApiResult(ApiResultCode.E_SYS_RPC_ERROR);
            return apiResult;
        }

        apiResult = new ApiResult(ApiResultCode.SUCCESS, result);
        return apiResult;
    }

}
