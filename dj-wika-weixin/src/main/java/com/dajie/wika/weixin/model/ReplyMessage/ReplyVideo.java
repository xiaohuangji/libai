package com.dajie.wika.weixin.model.ReplyMessage;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by wills on 2/10/14.
 */
public class ReplyVideo {

    @XStreamAlias("mediaId")
    private String mediaId;

    @XStreamAlias("Title")
    private String title;

    @XStreamAlias("Description")
    private String description;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
