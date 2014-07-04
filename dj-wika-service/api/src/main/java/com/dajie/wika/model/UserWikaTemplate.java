/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.wika.model;

/**
 * @author <a href="mailto:chengwei.hust@gmail.com">程伟</a>
 * @version Jan 7, 2014 6:31:53 PM
 */

public class UserWikaTemplate extends WikaTemplate {

    private static final long serialVersionUID = -8547165449513427125L;

    //用户微卡模版的状态 0.不可用 1.可用 3.当前正在使用
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
