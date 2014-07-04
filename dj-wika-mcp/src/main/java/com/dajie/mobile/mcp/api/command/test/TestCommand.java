/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.command.test;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.dajie.mobile.mcp.api.command.AbstractApiCommand;
import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;
import com.dajie.mobile.mcp.utils.McpUtils;

/**
 * @author wei.cheng
 * 
 */
public class TestCommand extends AbstractApiCommand implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(TestCommand.class);
   

    @Override
    public void afterPropertiesSet() throws Exception {
        //Assert.notNull(commandLookupService, "commandLookupService is required!");
    }

    @Override
    public ApiResult onExecute(ApiCommandContext context) {
        long t = System.currentTimeMillis();
        Map<String, String> m = new HashMap<String, String>();
        McpUtils.rpcTimeCost(t,
                String.format("[%s][test]", this.getClass().getName()));
      
        int n = 2;        
        
        return new ApiResult(0, n);
    }
    
}
