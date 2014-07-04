/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.mobile.mcp.api.command.user;

import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.api.entity.ApiResultCode;
import com.dajie.mobile.mcp.utils.McpUtils;
import com.dajie.wika.service.TemplateService;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Jan 8, 2014 2:50:13 PM
 */

public class UnlockWikaTemplate extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(UnlockWikaTemplate.class);

    private TemplateService templateService;

    public void setTemplateService(TemplateService templateService) {
        this.templateService = templateService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(templateService, "templateService is required");
    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
        // 取参数
        int userId = context.getUserId();
        Map<String, String> stringParams = context.getStringParams();
        // 兼容IOS客户端用的排序算法没有区分大小写
        int WTId = NumberUtils.toInt(stringParams.get("wtId"));
        Object result = null;
        ApiResult apiResult = null;
        // 执行RPC调用
        try {
            long t = System.currentTimeMillis();
            result = templateService.unlockWikaTemplate(userId, WTId);
            McpUtils.rpcTimeCost(t, "templateService.unlockWikaTemplate");
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
