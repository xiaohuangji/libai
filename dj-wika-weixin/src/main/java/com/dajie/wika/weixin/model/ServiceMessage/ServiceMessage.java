package com.dajie.wika.weixin.model.ServiceMessage;

/**
 * Created by wills on 2/10/14.
 * 由于微信要求这种格式，所以没有用驼峰格式
 */
public abstract class ServiceMessage {

    private String touser;

    private String msgtype;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }


    public String getMsgtype() {
        return msgtype;
    }


    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}

