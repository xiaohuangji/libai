/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.UnlockTemplateDAO;


/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Jan 8, 2014 11:40:44 AM
 */
@Component("unlockTemplateDAO")
public class UnlockTemplateDAOImpl extends Dao implements UnlockTemplateDAO {

    private static final String SQL_PREFIX = "com.dajie.wika.dao.UnlockTemplateDAO.";
    
    public UnlockTemplateDAOImpl() {
        super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
    }
    
    @Override
    public String getUnlockedWikaTids(int userId) {      
        return (String) selectOne(SQL_PREFIX + "getUnlockedWikaTids", userId);
    }
    
    @Override
    public String getUnlockedQRTids(int userId) {       
        return (String) selectOne(SQL_PREFIX + "getUnlockedQRTids", userId);
    }
    
    @Override
    public int insertUnlockedWikaTids(int userId, String tids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("tids", tids);
        return insert(SQL_PREFIX + "insertUnlockedWikaTids", map);
    }

    @Override
    public int insertUnlockedQRTids(int userId, String tids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("tids", tids);
        return insert(SQL_PREFIX + "insertUnlockedQRTids", map);
    }

    @Override
    public int updateUnlockedWikaTids(int userId, String tids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("tids", tids);
        return update(SQL_PREFIX + "updateUnlockedWikaTids", map);
    }

    @Override
    public int updateUnlockedQRTids(int userId, String tids) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("tids", tids);
        return update(SQL_PREFIX + "updateUnlockedQRTids", map);
    }

}

