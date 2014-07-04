package com.dajie.wika.weixin.service.impl;

import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.QRcodeService;
import com.dajie.wika.service.ResourceService;
import com.dajie.wika.weixin.constant.ReturnCode;
import com.dajie.wika.weixin.constant.Weixin;
import com.dajie.wika.weixin.constant.WeixinUrl;
import com.dajie.wika.weixin.dao.UserDAO;
import com.dajie.wika.weixin.model.PicUrls;
import com.dajie.wika.weixin.model.WXUserInfo;
import com.dajie.wika.weixin.model.WXUserTrace;
import com.dajie.wika.weixin.service.TokenService;
import com.dajie.wika.weixin.service.UserService;
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
@Component("userService")
public class UserServiceImpl implements UserService {

    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private QRcodeService qRcodeService;

    @Autowired
    private AccountService accountService;

    @Override
    public WXUserInfo getUserInfo(String openid) {
        return userDAO.getUserInfo(openid);
    }

    @Override
    public int importUserInfoFromWX(String openid) {
        try{
            String result=HttpClientUtil.doGet(WeixinUrl.getUserInfoUrl(tokenService.getToken().getAccess_token(),openid));
            WXUserInfo userInfo=JsonUtil.fromJson(result,WXUserInfo.class);
            userDAO.insertUserInfo(userInfo);
            logger.debug("import userinfo succ:"+openid);
            return ReturnCode.SUCC;
        }catch(Exception e){
            logger.warn("import userinfo from weixin fail",e);
        }
        return ReturnCode.FAIL;
    }

    @Override
    public int updateUserHeadUrl(String openid, String headimgurl) {
        userDAO.updateUserHeadUrl(openid,headimgurl);
        Integer userId=accountService.getUserIdByWeixinOpenid(openid);
        //更新微系统头像
        if(userId!=null){
            String avatar=resourceService.uploadFile(headimgurl);
            String qrUrl=qRcodeService.genDefaultQRcode(userId,avatar,1);
            accountService.updateAvatarAndQRCode(userId,avatar,1,qrUrl);
        }
        return ReturnCode.SUCC;
    }

    @Override
    public int recordUserTrace(String openid, int type,int recall_flag) {
        userDAO.recordUserTrace(openid,type,recall_flag);
        logger.debug("update user trace and type :"+openid);
        return ReturnCode.SUCC;
    }

    @Override
    public WXUserTrace getUserTrace(String openid) {
        return userDAO.getUserTrace(openid);
    }

    @Override
    public int unSubscribe(String openid) {
        userDAO.unSubscribe(openid);
        logger.debug("unsubscribe event:"+openid);
        return ReturnCode.SUCC;
    }
}
