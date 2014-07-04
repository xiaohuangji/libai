package com.dajie.wika.weixin.service.impl;

import com.dajie.wika.weixin.constant.MessageType;
import com.dajie.wika.weixin.constant.ReturnCode;
import com.dajie.wika.weixin.constant.WeixinUrl;
import com.dajie.wika.weixin.dao.UserDAO;
import com.dajie.wika.weixin.model.ServiceMessage.ServiceText;
import com.dajie.wika.weixin.model.ServiceMessage.ServiceTextMessage;
import com.dajie.wika.weixin.model.WXUserTrace;
import com.dajie.wika.weixin.service.RecallService;
import com.dajie.wika.weixin.service.TokenService;
import com.dajie.wika.weixin.util.DataUtil;
import com.dajie.wika.weixin.util.HttpClientUtil;
import com.dajie.wika.weixin.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by wills on 2/14/14.
 */
@Component("recallService")
public class RecallServiceImpl implements RecallService {

    private static final Logger logger= LoggerFactory.getLogger(RecallServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TokenService tokenService;

    private static final long MILLISECONDSOFDAY=86400;

    @Override
    public int sendRecallServiceMessage() {
        //召回
        long endtime=System.currentTimeMillis()/1000-MILLISECONDSOFDAY*1;
        long startTime=System.currentTimeMillis()/1000-MILLISECONDSOFDAY*2;
        List<WXUserTrace> users=userDAO.getNeedRecallUser(startTime, endtime);
        if(users==null){
            return ReturnCode.SUCC;
        }

        String posturl= WeixinUrl.getServiceUrl(tokenService.getToken().getAccess_token());
        ServiceTextMessage serviceTextMessage1=new ServiceTextMessage();
        ServiceTextMessage serviceTextMessage2=new ServiceTextMessage();
        serviceTextMessage1.setMsgtype(MessageType.TEXT);
        serviceTextMessage2.setMsgtype(MessageType.TEXT);
        serviceTextMessage1.setText(new ServiceText(DataUtil.getTextResponse("Recall0")));
        serviceTextMessage2.setText(new ServiceText(DataUtil.getTextResponse("Recall1")));

        for(WXUserTrace user:users){
            logger.info("send recall message,openid:"+user.getOpenid());
            if(user.getRecall_flag()==0){
                serviceTextMessage1.setTouser(user.getOpenid());
                String result=HttpClientUtil.doPost(posturl, JsonUtil.toJson(serviceTextMessage1));
                logger.debug("result:"+result);
            }else{
                serviceTextMessage2.setTouser(user.getOpenid());
                HttpClientUtil.doPost(posturl, JsonUtil.toJson(serviceTextMessage2));
            }
        }

        return ReturnCode.SUCC;
    }

    public static void main(String[] args) {
        ServiceTextMessage serviceTextMessage=new ServiceTextMessage();
        serviceTextMessage.setMsgtype(MessageType.TEXT);
        serviceTextMessage.setText(new ServiceText("test"));
        serviceTextMessage.setTouser("ouRijjh-KxSotZ79RX_x9leBZa24");

        String posturl= WeixinUrl.getServiceUrl("t7a0nudS5dMrq9lqcvLJKgtQA01xwaeyLUcgrDvwaN-7KMMm5sJviWLSQdqvdOAt9A2WKcSOpkVIb8nYb1vY0C9aWyvyPsdgXQYjbnbaEF_b2q99YDh4duq8ipBFtksY4SDFNrHmc4KUJjXSsbdWCQ");
        System.out.println(HttpClientUtil.doPost(posturl,JsonUtil.toJson(serviceTextMessage)));
    }
}
