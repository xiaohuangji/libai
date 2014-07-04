package com.dajie.wika.weixin.service;

import com.dajie.wika.weixin.model.WXUserInfo;
import com.dajie.wika.weixin.model.WXUserTrace;

import java.util.List;

/**
 * Created by wills on 2/14/14.
 */
public interface UserService {

    public WXUserInfo getUserInfo(String openid);

    public int importUserInfoFromWX(String openid);

    public int recordUserTrace(String openid,int type,int recall_flag);

    public WXUserTrace getUserTrace(String openid);

    public int unSubscribe(String openid);

    public int updateUserHeadUrl(String openid,String headimgurl);
}
