package com.dajie.wika.weixin.model.ReplyMessage;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by wills on 2/10/14.
 */
public class ReplyImage {

    @XStreamAlias("mediaId")
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
