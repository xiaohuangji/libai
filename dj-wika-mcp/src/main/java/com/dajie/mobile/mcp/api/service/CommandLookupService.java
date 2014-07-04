/**
 * $Id: CommandLookupService.java 108669 2012-10-16 09:56:23Z wei.cheng@dajie-inc.com $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.service;

import com.dajie.mobile.mcp.api.command.ApiCommand;

/**
 * @author Marshal(shuai.ma@renren-inc.com) Initial Created at 2012-6-13
 */
public interface CommandLookupService {

    /**
     * lookup api command from methodValue
     * 
     * @param methodValue
     * @return
     */
    public ApiCommand lookupApiCommand(String methodValue);

    /**
     * 是否需要登录
     * 
     * @param methodvalue
     * @return
     */
    public boolean isNeedLogin(String methodValue);

    /**
     * 取所有的接口名称列表
     * 
     * @return
     */
    public String[] getCommands();
}
