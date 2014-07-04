package com.dajie.wika.weixin.model.RequestMessage;

import com.dajie.wika.weixin.constant.MessageType;

/**
 * Created by wills on 2/10/14.
 */
public class RequestEventMessage extends RequestMessage {

    private String event;

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
