package com.dajie.wika.weixin.model.ReplyMessage;

import com.dajie.wika.weixin.constant.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by wills on 2/10/14.
 */
@XStreamAlias("xml")
public class ReplyVoiceMessage extends ReplyMessage{

    @XStreamAlias("Voice")
    private ReplyVoice voice;

    public ReplyVoiceMessage() {
        super.setMsgType(MessageType.VOICE);
    }

    public ReplyVoice getVoice() {
        return voice;
    }

    public void setVoice(ReplyVoice voice) {
        this.voice = voice;
    }

}
