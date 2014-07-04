package com.dajie.wika.weixin.model.RequestMessage;

import com.dajie.wika.weixin.constant.MessageType;

/**
 * Created by wills on 2/10/14.
 */
public final class RequestImageMessage extends RequestNormalMessage {

    private String  picUrl;

    private String mediaId;

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
