package com.dajie.wika.weixin.model;

import java.util.Date;

/**
 * Created by wills on 2/17/14.
 */
public class WXUserTrace {

    private String openid;

    private int type;

    private int recall_flag;

    private Date update_time;

    public int getRecall_flag() {
        return recall_flag;
    }

    public void setRecall_flag(int recall_flag) {
        this.recall_flag = recall_flag;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}
