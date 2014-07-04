/**
 * $Id: ParamsUtils.java 118779 2012-11-26 14:05:51Z wei.cheng@dajie-inc.com $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.math.NumberUtils;

/**
 * 
 * 
 * @author <a href="mailto:wei.cheng3@renren-inc.com">程伟</a>
 * @version 2012-9-19下午01:06:29
 */
public class ParamsUtils {

    public static Map<String, Integer> convertStringToMap(String s) {

        JSONObject jsonMap = JSONObject.fromObject(s);

        Map<String, Integer> map = new HashMap<String, Integer>();
        for (Iterator<?> iter = jsonMap.keys(); iter.hasNext();) {
            String key = (String) iter.next();
            //获取key的value
            int value = (Integer) jsonMap.get(key);
            map.put(key, value);
        }
        return map;
    }

    public static List<Long> convertStringToList(String s) {

        JSONArray jsonList = JSONArray.fromObject(s);

        List<Long> l = new ArrayList<Long>();
        for (Iterator<?> iter = jsonList.iterator(); iter.hasNext();) {
            long value = NumberUtils.toLong(iter.next().toString());
            l.add(value);
        }
        return l;

    }

    public static boolean hasKey(JSONObject jo, String key) {
        Set<?> keySet = jo.keySet();
        if (keySet.contains(key)) {
            return true;
        } else {
            return false;
        }
    }

    public static String parse(JSONObject jo, String key) {
        String s = "";
        Set<?> keySet = jo.keySet();
        if (keySet.contains(key)) {
            s = jo.getString(key).replace('|', '_');
        }
        return s;
    }
}
