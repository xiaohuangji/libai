/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.dajie.mobile.mcp.api.service.McpCacheManager;

/**
 * @author wei.cheng
 * 
 */
public class McpCacheManagerImpl implements McpCacheManager {

    // private static final Logger logger =
    // LoggerFactory.getLogger(McpCacheManagerImpl.class);

    private Map<String, Object> sigCache = new HashMap<String, Object>();

    private Map<String, Object> userSecretKeyCache = new HashMap<String, Object>();

    @Override
    public Map<String, Object> getSigCache() {
        return sigCache;
    }

    @Override
    public Map<String, Object> getUserSecretKeyCache() {
        return userSecretKeyCache;
    }

}
