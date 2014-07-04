/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.wika.dao;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Jan 8, 2014 11:33:22 AM
 */

public interface UnlockTemplateDAO {

    // 获取用户已经解锁的微卡模版tids
    public String getUnlockedWikaTids(int userId);

    // 获取用户已经解锁的脸码模版tids
    public String getUnlockedQRTids(int userId);

    // 用户初次解锁，插入用户解锁的微卡模版tids
    public int insertUnlockedWikaTids(int userId, String tids);

    // 用户初次解锁，插入用户解锁的脸码模版tids
    public int insertUnlockedQRTids(int userId, String tids);

    // 更新用户解锁的微卡模版tids
    public int updateUnlockedWikaTids(int userId, String tids);

    // 更新用户解锁的脸码模版tids
    public int updateUnlockedQRTids(int userId, String tids);

}
