package com.dajie.wika.weixin.model.ServiceMessage;

/**
 * Created by wills on 2/10/14.
 */
public class ServiceTextMessage extends ServiceMessage {

    private ServiceText text;

    public ServiceText getText() {
        return text;
    }

    public void setText(ServiceText text) {
        this.text = text;
    }
}
