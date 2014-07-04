package com.dajie.wika.weixin.model.RequestMessage;

import com.dajie.wika.weixin.constant.MessageType;

/**
 * Created by wills on 2/10/14.
 */
public final class RequestVoiceMessage extends RequestNormalMessage {

    private String mediaId;

    private String format;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
