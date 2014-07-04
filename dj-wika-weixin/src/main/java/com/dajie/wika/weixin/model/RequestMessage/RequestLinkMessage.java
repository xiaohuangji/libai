package com.dajie.wika.weixin.model.RequestMessage;

import com.dajie.wika.weixin.constant.MessageType;

/**
 * Created by wills on 2/10/14.
 */
public final class RequestLinkMessage extends RequestNormalMessage {

    private String title;

    private String url;

    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
