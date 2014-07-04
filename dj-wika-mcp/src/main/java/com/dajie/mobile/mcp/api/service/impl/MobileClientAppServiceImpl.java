/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dajie.mobile.mcp.api.entity.MobileClientAppInfo;
import com.dajie.mobile.mcp.api.service.MobileClientAppService;
import com.dajie.mobile.mcp.utils.McpUtils;

/**
 * @author wei.cheng
 * 
 */
public class MobileClientAppServiceImpl implements MobileClientAppService, Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MobileClientAppServiceImpl.class);

    // private Map<String, MobileClientAppInfo> appKeyAppInfoMap = null;

    private Map<Integer, MobileClientAppInfo> appIdAppInfoMap = null;

    private Map<Integer, List<String>> appAuthMap = null;

    private synchronized void loadApp() {
        logger.info("loadApp start");
        long startTime = System.currentTimeMillis();

        // TODO: 加载app
        // test app
        appIdAppInfoMap = new HashMap<Integer, MobileClientAppInfo>();
        MobileClientAppInfo mcai = new MobileClientAppInfo();
        mcai.setAppId(1);
        mcai.setAppName("testapp");
        mcai.setAppUrl("ttt");
        mcai.setSecretKey("1qaz2wsx");
        appIdAppInfoMap.put(mcai.getAppId(), mcai);
        appAuthMap = new HashMap<Integer, List<String>>();
        List<String> methods = new ArrayList<String>();
        methods.add("test.cmd");
        methods.add("account.*");
        methods.add("system.*");
        methods.add("message.*");
        methods.add("profile.*");
        methods.add("relation.*");
        methods.add("usersettings.*");
        methods.add("user.*");
        appAuthMap.put(mcai.getAppId(), methods);

        // android app
        MobileClientAppInfo mcaiAndroid = new MobileClientAppInfo();
        mcaiAndroid.setAppId(219262);
        mcaiAndroid.setAppName("android");
        //        mcaiAndroid.setAppUrl("android");
        mcaiAndroid.setSecretKey("92012b7cf01347119629c8e3bff75dc3");
        appIdAppInfoMap.put(mcaiAndroid.getAppId(), mcaiAndroid);
        appAuthMap.put(mcaiAndroid.getAppId(), methods);

        // IOS app
        MobileClientAppInfo mcaiIOS = new MobileClientAppInfo();
        mcaiIOS.setAppId(219260);
        mcaiIOS.setAppName("IOS");
        //        mcaiIOS.setAppUrl("IOS");
        mcaiIOS.setSecretKey("e645614242e64822847479cdf4cb3d8e");
        appIdAppInfoMap.put(mcaiIOS.getAppId(), mcaiIOS);
        appAuthMap.put(mcaiIOS.getAppId(), methods);

        logger.info("loadApp end timecost:" + (System.currentTimeMillis() - startTime));
    }

    @Override
    public void run() {
        this.loadApp();
    }

    @Override
    public MobileClientAppInfo getAppInfo(int appId) {
        // if (StringUtils.isEmpty(appKey) || this.appKeyAppInfoMap == null) {
        // return null;
        // }
        // return this.appKeyAppInfoMap.get(appKey);
        if (appId == 0 || this.appIdAppInfoMap == null) {
            return null;
        }
        return this.appIdAppInfoMap.get(appId);
    }

    @Override
    public boolean isAllowedApiMethod(int appId, String methodName) {
        if (this.appAuthMap == null) {
            return false;
        }
        Collection<String> apiMethods = this.appAuthMap.get(appId);
        if (apiMethods == null) {
            return false;
        }
        for (String method : apiMethods) {
            if (McpUtils.leftMatch(methodName, method)) {
                return true;
            }
        }
        return false;
    }
}
