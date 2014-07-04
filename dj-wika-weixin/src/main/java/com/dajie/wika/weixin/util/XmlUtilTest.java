package com.dajie.wika.weixin.util;

import com.dajie.wika.weixin.model.ReplyMessage.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wills on 2/10/14.
 */
public class XmlUtilTest {

    public static void main(String[] args) {
//        ReplyImageMessage message=new ReplyImageMessage();
//        message.setCreateTime(System.currentTimeMillis());
//        message.setFromUserName("fromusername");
//        message.setToUserName("tousername");
//        message.setMsgType("text");
//        ReplyImage replyImage=new ReplyImage();
//        replyImage.setMediaId("imageiD");
//        message.setImage(replyImage);
//
//        ReplyMusicMessage message1=new ReplyMusicMessage();
//        message1.setCreateTime(System.currentTimeMillis());
//        message1.setFromUserName("fromusername");
//        message1.setToUserName("tousername");
//        message1.setMsgType("text");
//        ReplyMusic replyMusic=new ReplyMusic();
//        replyMusic.setTitle("music title");
//        message1.setMusic(replyMusic);
//
//        System.out.println(XmlUtil.genReplyMessage(message));
//        System.out.println(XmlUtil.genReplyMessage(message1));

        //testArticlesMessage();
        testFromXml();
    }

    private static void testFromXml(){
        String xml=" <xml>\n" +
                " <ToUserName><![CDATA[toUser]]></ToUserName>\n" +
                " <FromUserName><![CDATA[fromUser]]></FromUserName>\n" +
                " <CreateTime>1348831860</CreateTime>\n" +
                " <MsgType><![CDATA[image]]></MsgType>\n" +
                " <PicUrl><![CDATA[this is a url]]></PicUrl>\n" +
                " <MediaId><![CDATA[media_id]]></MediaId>\n" +
                " <MsgId>1234567890123456</MsgId>\n" +
                " </xml>";
        Map map=(Map)XmlUtil.fromXml(xml);
        System.out.println(map.get("PicUrl"));
    }

    private static void testArticlesMessage(){
        ReplyArticlesMessage message=new ReplyArticlesMessage();
        message.setCreateTime(System.currentTimeMillis());
        message.setFromUserName("fromusername");
        message.setToUserName("tousername");
        message.setMsgType("news");



        Item item=new Item();
        item.setTitle("1");item.setDescription("1ddd");item.setUrl("1url");
        Item item2=new Item();
        item2.setTitle("2");item2.setDescription("2ddd");item2.setUrl("2url");

//        Item[] items=new Item[2];
//        items[0]=item;
//        items[1]=item2;
        List<Item> articles=new ArrayList<Item>();
        articles.add(item);
        articles.add(item2);


        message.setArticleCount(articles.size());
        message.setArticles(articles);

        System.out.println(XmlUtil.toXml(message));
    }
}
