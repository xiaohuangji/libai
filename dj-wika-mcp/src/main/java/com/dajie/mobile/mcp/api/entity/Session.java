/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.entity;

import java.io.Serializable;

/**
 * @author wei.cheng
 * 
 */
public class Session implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final long TIMEOUT = 30L * 24 * 60 * 60 * 1000L;

    private int userId;

    private long timeStamp;

    private String key;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
