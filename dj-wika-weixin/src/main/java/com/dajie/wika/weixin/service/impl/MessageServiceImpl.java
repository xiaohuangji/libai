package com.dajie.wika.weixin.service.impl;

import com.dajie.wika.weixin.constant.EventType;
import com.dajie.wika.weixin.constant.MessageType;
import com.dajie.wika.weixin.model.ReplyMessage.Item;
import com.dajie.wika.weixin.model.ReplyMessage.ReplyArticlesMessage;
import com.dajie.wika.weixin.model.ReplyMessage.ReplyMessage;
import com.dajie.wika.weixin.model.ReplyMessage.ReplyTextMessage;
import com.dajie.wika.weixin.model.RequestMessage.*;
import com.dajie.wika.weixin.model.WXUserTrace;
import com.dajie.wika.weixin.service.MessageService;
import com.dajie.wika.weixin.service.UserService;
import com.dajie.wika.weixin.util.DataUtil;
import com.dajie.wika.weixin.util.XmlUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;

/**
 * Created by wills on 2/11/14.
 * 接受用户主动消息
 */
@Component("messageService")
public class MessageServiceImpl implements MessageService {

    private static final Logger logger=LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private UserService userService;

    @Override
    public String processRequest(String requestXmlMsg) {
        RequestMessage requestMessage=parseRequestMsg(requestXmlMsg);
        if(logger.isDebugEnabled()){

            logger.debug("recevice message,type;"+requestMessage.getMsgType());
        }
        return disPatch(requestMessage);
    }

    private String disPatch(RequestMessage requestMessage){
        //处理事件消息
        if(requestMessage.getMsgType().equals(MessageType.EVENT)){
            String eventType=((RequestEventMessage) requestMessage).getEvent();
            //处理关注订阅消息
            if(eventType.equals(EventType.SUBSCRIBE)){
                logger.debug("handle subscribe message");
                //摘取数据库存入系统
                userService.importUserInfoFromWX(requestMessage.getFromUserName());
                return getSubscribeResponse(requestMessage);
            }//处理取消订阅消息
            else if(eventType.equals(EventType.UNSUBSCRIBE)){
                logger.debug("handle unsubscribe message");
                userService.unSubscribe(requestMessage.getFromUserName());
                return null;
            }//处理自定义点击事件消息
            else if(eventType.equals(EventType.CLICK)){
                return handleClickRequest((RequestEventClickMessage)requestMessage);
            }
        //处理文本消息
        }else if(requestMessage.getMsgType().equals(MessageType.TEXT)){
           logger.debug("handle text message");
           //收到文本信息
           return handleTextRequest((RequestTextMessage)requestMessage);
        }//处理图片消息
        else if(requestMessage.getMsgType().equals(MessageType.IMAGE)){
            //获取上传图片状态标识
            WXUserTrace ut=userService.getUserTrace(requestMessage.getFromUserName());
            if(ut.getType()==1&&(System.currentTimeMillis()-ut.getUpdate_time().getTime())<60000){
                //处理图片信息
                logger.debug("handle image message");
                userService.updateUserHeadUrl(requestMessage.getFromUserName(),((RequestImageMessage)requestMessage).getPicUrl());
                return getFinishedQRResponse(requestMessage,false);
            }
        }
        logger.debug("handle unhandled type message");
        return getTextResponse(requestMessage,"已收到，感谢反馈~");
    }


    private String handleClickRequest(RequestEventClickMessage requestEventClickMessage){
        if(requestEventClickMessage.getEventKey().equals("KEY_MYVCARD_REGISTER")){
            return getMyvcardRegisterResponse(requestEventClickMessage);
        }else if(requestEventClickMessage.getEventKey().equals("KEY_MYVCARD_LOGIN")){
            return getMyvcardLoginResponse(requestEventClickMessage);
        }else if(requestEventClickMessage.getEventKey().equals("KEY_DOWNLOAD")){
            return getDownloadResponse(requestEventClickMessage);
        }else if(requestEventClickMessage.getEventKey().equals("KEY_NEWFRIEND")){
            return getNewFriendResponse(requestEventClickMessage);
        }
        else{
            return getHowToPlayResponse(requestEventClickMessage);
        }
    }

    private String handleTextRequest(RequestTextMessage requestMessage){
        String text=requestMessage.getContent();
        if(text.equals("微")){
           //将微信openid以及headurl与系统绑定
            return getFinishedQRResponse(requestMessage,true);
        }
        if(text.equals("帮助")||text.equals("help")){
            return getSubscribeResponse(requestMessage);
        }
        if(text.equals("怎么玩")){
            return getHowToPlayResponse(requestMessage);
        }
        if(text.equals("我要微卡")){
            return getMyvcardRegisterResponse(requestMessage);
        }
        if(text.equals("换主题")){
            return getChangeWikaTemplateResponse(requestMessage);
        }
        if(text.startsWith("下载")){
            return getDownloadResponse(requestMessage);
        }
        if(text.equals("新朋友")||text.equals("想认识我的人")||text.equals("认识我")){
            return getNewFriendResponse(requestMessage);
        }

        if(text.equals("脸码")||text.equals("制作脸码")){
            text="制作脸码";
        }
        String mapStr= DataUtil.getTextResponse(text);
        if(text.equals("图")){
            userService.recordUserTrace(requestMessage.getFromUserName(),1,0);
        }else if(text.equals("11A")||text.equals("12A")||text.equals("11B")||text.equals("12B")){
            userService.recordUserTrace(requestMessage.getFromUserName(),0,1);
        }
        else{
            userService.recordUserTrace(requestMessage.getFromUserName(),0,0);
        }
        return getTextResponse(requestMessage,mapStr==null?"已收到，感谢反馈~":mapStr );
    }

    private String getChangeWikaTemplateResponse(RequestMessage requestMessage){
        String url="http://www.weika001.com/user/login-unlock?openid="+requestMessage.getFromUserName();
        String description="如果您已经申领了一张您的专属微卡，可以点击此处进行更换主题。";
        String title="更换微卡主题";
        String picUrl="http://5.f1.dajieimg.com/n/wika_avatar/T121DTB5YT1R4cSCrK_c.png";
        return makeNewsResponse(requestMessage,url,title,description,picUrl);
    }

    private String getNewFriendResponse(RequestMessage requestMessage){
        String url="http://www.weika001.com/relation/i-know?following=0&openid="+requestMessage.getFromUserName();
        String description="点击此处查看还有谁想认识你，俗话说得好，在家靠父母，出门靠朋友！在中国靠关系，你懂得～赶紧看看你的关系够不够硬～";
        String title="还有谁想认识你";
        String picUrl="http://5.f1.dajieimg.com/n/wika_avatar/T1mfCTB5hT1R4cSCrK_c.png";
        return makeNewsResponse(requestMessage,url,title,description,picUrl);
    }

    private String getDownloadResponse(RequestMessage requestMessage){
        ReplyTextMessage replyTextMessage=new ReplyTextMessage();
        extractRequestToReply(requestMessage, replyTextMessage);
        replyTextMessage.setContent("在家靠谱父母，出门靠朋友！全方位打造你的专属品牌形象——微卡。下载app即可拥有300w+的高端人脉~ 猛戳下载地址→_→\n" +
                "iPhone版下载地址：https://itunes.apple.com/us/app/wei-ka-neng-sao-tou-xiang/id805428016?ls=1&mt=8\n" +
                "Android版下载地址：http://www.weika001.com/system/download");
        replyTextMessage.setCreateTime(System.currentTimeMillis() / 1000);
        return XmlUtil.toXml(replyTextMessage);
    }

    private String getHowToPlayResponse(RequestMessage requestMessage){
        String url="http://t.cn/8FON1uG";
        String description="微卡，展示和丰富手机上的个人职业形象，同时让你枯燥烦闷的职场生活娱乐起来。";
        String title="微卡怎么玩";
        String picUrl="http://6.f1.dajieimg.com/group1/M00/4B/C2/CgpAo1MDJxOAa3IcAAAAe6qVTF0598c.png";
        return makeNewsResponse(requestMessage,url,title,description,picUrl);
    }

    private String getMyvcardRegisterResponse(RequestMessage requestMessage){
        String url="www.weika001.com/user/myvcard?openid="+requestMessage.getFromUserName();
        String description="微卡，展示和丰富手机上的个人职业形象，同时让你枯燥烦闷的职场生活娱乐起来。点击此处立即免费制作。";
        String title="免费制作我的微卡";
        String picUrl="http://1.f1.dajieimg.com/group1/M00/3E/BC/CgpAmVMDJv2ATGkVAAAAe6qVTF0133c.png";
        return makeNewsResponse(requestMessage,url,title,description,picUrl);
    }

    private String getMyvcardLoginResponse(RequestMessage requestMessage){
        String url="www.weika001.com/user/myvcard";
        String description="点击此处查看我的微卡，你可以将自己的专属微卡分享给你微信的小伙们哦～";
        String title="查看我的微卡";
        String picUrl="http://7.f1.dajieimg.com/group1/M00/4B/C2/CgpAo1MDJrOAJG4-AAAAe6qVTF0369c.png";
        return makeNewsResponse(requestMessage,url,title,description,picUrl);
    }

    private String getFinishedQRResponse(RequestMessage requestMessage,boolean sync){
        String syncFlag="1";
        if(!sync){
            syncFlag="2";
        }
        String url="www.weika001.com/user/myvcard?openid="+requestMessage.getFromUserName()+"&sync="+syncFlag;
        String description="制作完成，点击立即查看带新的二维码头像的微卡！";
        String title="制作完成，查看我的脸码";
        String picUrl="http://3.f1.dajieimg.com/group1/M00/4B/C2/CgpAo1MDJyqAf-dCAAAAe6qVTF0035c.png";
        return makeNewsResponse(requestMessage,url,title,description,picUrl);
    }

    private String makeNewsResponse(RequestMessage requestMessage,String url,String title,String description,String picUrl){
        ReplyArticlesMessage replyArticlesMessage=new ReplyArticlesMessage();
        extractRequestToReply(requestMessage,replyArticlesMessage);
        List<Item> articles=new ArrayList<Item>();
        Item item=new Item();
        item.setUrl(url);
        item.setDescription(description);
        item.setTitle(title);
        item.setPicUrl(picUrl);
        articles.add(item);
        replyArticlesMessage.setArticles(articles);
        replyArticlesMessage.setArticleCount(articles.size());
        return XmlUtil.toXml(replyArticlesMessage);
    }

    private String getTextResponse(RequestMessage requestMessage,String content){
        ReplyTextMessage replyTextMessage=new ReplyTextMessage();
        extractRequestToReply(requestMessage, replyTextMessage);
        replyTextMessage.setContent(content);
        replyTextMessage.setCreateTime(System.currentTimeMillis() / 1000);
        return XmlUtil.toXml(replyTextMessage);
    }

    private String getSubscribeResponse(RequestMessage requestMessage){
        ReplyTextMessage replyTextMessage=new ReplyTextMessage();
        extractRequestToReply(requestMessage,replyTextMessage);
        replyTextMessage.setContent(DataUtil.getSubscribeResponse(requestMessage.getFromUserName()));
        replyTextMessage.setCreateTime(System.currentTimeMillis()/1000);
        return XmlUtil.toXml(replyTextMessage);
    }

    private RequestMessage parseRequestMsg(String requestXmlMsg){
        try{
            //System.out.println(requestXmlMsg);
            Map<String,String> map= XmlUtil.fromXml(requestXmlMsg);
            String msgType=map.get("msgType");
            RequestMessage requestMessage=null;
            if(msgType.equals(MessageType.EVENT)){
                String event=map.get("event");
                if(event.equals(EventType.CLICK)){
                    requestMessage=new RequestEventClickMessage();
                }else{
                    requestMessage=new RequestEventMessage();
                }
            }else if(msgType.equals(MessageType.TEXT)){
                requestMessage=new RequestTextMessage();
            }else if(msgType.equals(MessageType.IMAGE)){
                requestMessage=new RequestImageMessage();
            }else if(msgType.equals(MessageType.LINK)){
                requestMessage=new RequestLinkMessage();
            }else if(msgType.equals(MessageType.LOCATION)){
                requestMessage=new RequestLocationMessage();
            }else if(msgType.equals(MessageType.VOICE)){
                requestMessage=new RequestVoiceMessage();
            }else if(msgType.equals(MessageType.VIDEO)){
                requestMessage=new RequestVideoMessage();
            }
            if(requestMessage!=null){
                BeanUtils.populate(requestMessage,map);
            }
            return requestMessage;
        }catch(Exception e){
            logger.warn("parse requestMsg error",e);
            return null;
        }
    }

    private void extractRequestToReply(RequestMessage requestMessage,ReplyMessage replyMessage){
        replyMessage.setToUserName(requestMessage.getFromUserName());
        replyMessage.setFromUserName(requestMessage.getToUserName());
    }

}
