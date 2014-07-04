package com.dajie.wika.weixin.model.ReplyMessage;

import com.dajie.wika.weixin.constant.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by wills on 2/10/14.
 */
@XStreamAlias("xml")
public class ReplyVideoMessage extends ReplyMessage {

    @XStreamAlias("Video")
    private ReplyVideo video;

    public ReplyVideoMessage() {
        super.setMsgType(MessageType.VIDEO);
    }

    public ReplyVideo getVideo() {
        return video;
    }

    public void setVideo(ReplyVideo video) {
        this.video = video;
    }

}
