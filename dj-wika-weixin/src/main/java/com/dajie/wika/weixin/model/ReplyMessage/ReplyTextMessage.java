package com.dajie.wika.weixin.model.ReplyMessage;

import com.dajie.wika.weixin.constant.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by wills on 2/10/14.
 */
@XStreamAlias("xml")
public class ReplyTextMessage extends  ReplyMessage {

    @XStreamAlias("Content")
    private String content;

    public ReplyTextMessage() {
        super.setMsgType(MessageType.TEXT);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
