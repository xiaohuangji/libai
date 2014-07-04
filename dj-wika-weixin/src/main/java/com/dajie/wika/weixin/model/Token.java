package com.dajie.wika.weixin.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wills on 2/10/14.
 * 与数据库写法一致
 */
public class Token implements Serializable{

    private String access_token;

    private Date update_time ;

    private int type=1;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
