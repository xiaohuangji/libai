/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.service;

import java.util.Map;

/**
 * @author wei.cheng
 * 
 */
public interface McpCacheManager {

    // TODO: sigcache的实现
    public Map<String, Object> getSigCache();

    public Map<String, Object> getUserSecretKeyCache();
}
