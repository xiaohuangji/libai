/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.dao;

import java.util.List;

import com.dajie.mobile.mcp.api.entity.MobileClientAppInfo;

/**
 * @author wei.cheng
 * 
 */
public interface AppInfoDAO {

    public List<MobileClientAppInfo> getAll();
}
