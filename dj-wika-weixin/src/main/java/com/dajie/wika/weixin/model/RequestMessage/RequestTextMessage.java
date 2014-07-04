package com.dajie.wika.weixin.model.RequestMessage;

import com.dajie.wika.weixin.constant.MessageType;

/**
 * Created by wills on 2/10/14.
 */
public final class RequestTextMessage extends RequestNormalMessage {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
