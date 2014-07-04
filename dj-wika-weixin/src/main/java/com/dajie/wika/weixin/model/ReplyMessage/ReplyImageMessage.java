package com.dajie.wika.weixin.model.ReplyMessage;

import com.dajie.wika.weixin.constant.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by wills on 2/10/14.
 */
@XStreamAlias("xml")
public class ReplyImageMessage extends ReplyMessage{

    @XStreamAlias("Image")
    private ReplyImage image;

    public ReplyImageMessage() {
        super.setMsgType(MessageType.IMAGE);
    }

    public ReplyImage getImage() {
        return image;
    }

    public void setImage(ReplyImage image) {
        this.image = image;
    }
}
