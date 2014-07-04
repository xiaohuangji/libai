package com.dajie.wika.weixin.service.impl;

import com.alibaba.fastjson.JSON;
import com.dajie.wika.weixin.constant.WeixinUrl;
import com.dajie.wika.weixin.dao.TokenDAO;
import com.dajie.wika.weixin.model.Token;
import com.dajie.wika.weixin.service.TokenService;
import com.dajie.wika.weixin.util.HttpClientUtil;
import com.dajie.wika.weixin.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wills on 2/10/14.
 */
@Component("tokenService")
public class TokenServiceImpl implements TokenService {

    private static final Logger logger= LoggerFactory.getLogger(TokenServiceImpl.class);

    private static final int TOKENEXPIRE=3600*1000;//一个小时

    @Autowired
    private TokenDAO tokenDAO;

    @Override
    public Token getToken() {
        Token token= tokenDAO.getToken();
        if(token==null){
            logger.debug("token is null,refresh token");
            token=refreshToken();
        }else{
            if(System.currentTimeMillis()-token.getUpdate_time().getTime() >TOKENEXPIRE){
                logger.debug("token has been expired ,refresh token");
                token=refreshToken();
            }
        }
        return token;
    }

    @Override
    public Token refreshToken() {
        //
        String tokenJson=HttpClientUtil.doGet(WeixinUrl.getTokenUrl());
        if(tokenJson!=null){
            Token token= JsonUtil.fromJson(tokenJson,Token.class);
            tokenDAO.setToken(token);
            return token;
        }
        return null;
    }
}
