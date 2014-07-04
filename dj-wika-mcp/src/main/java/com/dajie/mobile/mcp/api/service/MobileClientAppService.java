/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.service;

import com.dajie.mobile.mcp.api.entity.MobileClientAppInfo;

/**
 * @author wei.cheng
 * 
 */
public interface MobileClientAppService {

    public MobileClientAppInfo getAppInfo(int appId);

    public boolean isAllowedApiMethod(int appId, String methodName);
}
