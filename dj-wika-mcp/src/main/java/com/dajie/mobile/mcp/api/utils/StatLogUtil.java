/**
 * $Id: StatLogUtil.java 112872 2012-11-01 08:05:53Z wei.cheng3@XIAONEI.OPI.COM $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dajie.mobile.mcp.api.entity.ClientInfo;

/**
 * 统计log
 * 
 * @author wei.cheng
 * 
 */
public final class StatLogUtil {

    private static final Logger stdLogger = LoggerFactory.getLogger(StatLogUtil.class);

    // time|service|service_api|uid|app_id|uniq_id|mac|model|os|from_id|version|ip|business_type|identifier|sample|value|extra1|extra2|extra3|extra4|extra5|apiResultCode
    private static final String logFormat = "%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s";

    public static String log(Logger logger, long time, String serviceApi, String uid, String appId,
            ClientInfo clientInfo, String ip, String businessType, String identifier,
            String sample, String value, String extra1, String extra2, String extra3,
            String extra4, String extra5, String apiResultCode) {

        String rt = "";
        try {
            String uniqId = "";
            String mac = "";
            String model = "";
            String os = "";
            String fromId = "";
            String version = "";

            if (clientInfo != null) {
                uniqId = clientInfo.getUniqid();
                mac = clientInfo.getMac();
                model = clientInfo.getModel();
                os = clientInfo.getOs();
                fromId = clientInfo.getFrom() + "";
                version = clientInfo.getVersion();
            }

            rt = String.format(logFormat, time + "", "WKmcp",
                    StringUtils.defaultString(serviceApi), StringUtils.defaultString(uid),
                    StringUtils.defaultString(appId), StringUtils.defaultString(uniqId),
                    StringUtils.defaultString(mac), StringUtils.defaultString(model),
                    StringUtils.defaultString(os), StringUtils.defaultString(fromId),
                    StringUtils.defaultString(version), StringUtils.defaultString(ip),
                    StringUtils.defaultString(businessType), StringUtils.defaultString(identifier),
                    StringUtils.defaultString(sample), StringUtils.defaultString(value),
                    StringUtils.defaultString(extra1), StringUtils.defaultString(extra2),
                    StringUtils.defaultString(extra3), StringUtils.defaultString(extra4),
                    StringUtils.defaultString(extra5), StringUtils.defaultString(apiResultCode));
            logger.info(rt);

            if (stdLogger.isDebugEnabled()) {
                stdLogger.debug(String.format("[StatLogUtil]:[time=%s]", time)); // 在stdout里也能定位到stat日志里同一time的请求
            }

        } catch (Exception e) {
            stdLogger.error("accessLog", e);
        }
        return rt;
    }

}
