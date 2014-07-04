package com.dajie.wika.weixin.model.ReplyMessage;

import com.dajie.wika.weixin.constant.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by wills on 2/10/14.
 */
@XStreamAlias("xml")
public class ReplyMusicMessage extends ReplyMessage {

    @XStreamAlias("Music")
    private ReplyMusic music;

    public ReplyMusicMessage() {
        super.setMsgType(MessageType.MUSIC);
    }

    public ReplyMusic getMusic() {
        return music;
    }

    public void setMusic(ReplyMusic music) {
        this.music = music;
    }
}
