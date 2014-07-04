package com.dajie.wika.weixin.model.ServiceMessage;

/**
 * Created by wills on 2/10/14.
 */
public class ServiceText {

    private String content;

    public ServiceText(String content) {
        this.content = content;
    }

    ServiceText(){

    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
