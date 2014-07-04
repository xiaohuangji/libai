package com.dajie.wika.weixin.model.RequestMessage;

/**
 * Created by wills on 2/10/14.
 */
public final class RequestEventClickMessage extends RequestEventMessage {

    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
