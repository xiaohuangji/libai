package com.dajie.wika.weixin.model.ServiceMessage;

/**
 * Created by wills on 2/10/14.
 */
public class ServiceVoiceMessage extends ServiceMessage{

    private ServiceVoice voice;

    public ServiceVoice getVoice() {
        return voice;
    }

    public void setVoice(ServiceVoice voice) {
        this.voice = voice;
    }
}
