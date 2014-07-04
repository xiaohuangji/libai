/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.command;

import com.dajie.mobile.mcp.api.entity.ApiCommandContext;
import com.dajie.mobile.mcp.api.entity.ApiResult;

/**
 * 命令接口
 * 
 * @author wei.cheng
 */
public interface ApiCommand {

    /**
     * Execute the command and return result
     * 
     * @param context
     * @return
     */
    ApiResult execute(ApiCommandContext context);

}
