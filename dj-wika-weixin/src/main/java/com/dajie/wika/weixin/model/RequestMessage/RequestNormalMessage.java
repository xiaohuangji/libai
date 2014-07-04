package com.dajie.wika.weixin.model.RequestMessage;

import java.io.Serializable;

/**
 * Created by wills on 2/10/14.
 */
public class RequestNormalMessage extends RequestMessage{

    private String msgId;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }
}
