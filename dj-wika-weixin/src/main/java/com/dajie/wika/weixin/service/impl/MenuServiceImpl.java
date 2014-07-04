package com.dajie.wika.weixin.service.impl;

import com.dajie.wika.weixin.constant.ReturnCode;
import com.dajie.wika.weixin.constant.WeixinUrl;
import com.dajie.wika.weixin.model.Error;
import com.dajie.wika.weixin.service.MenuService;
import com.dajie.wika.weixin.service.TokenService;
import com.dajie.wika.weixin.util.DataUtil;
import com.dajie.wika.weixin.util.HttpClientUtil;
import com.dajie.wika.weixin.util.JsonUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wills on 2/10/14.
 */
@Component("menuService")
public class MenuServiceImpl implements MenuService {

    private static final org.slf4j.Logger logger= LoggerFactory.getLogger(MenuServiceImpl.class);

    @Autowired
    private TokenService tokenService;

    @Override
    public int createMenu() {
        String menuInfo=DataUtil.getMenuInfo();
        String createUrl= WeixinUrl.getCreateMenuUrl(tokenService.getToken().getAccess_token());
        String result=HttpClientUtil.doPost(createUrl,menuInfo);
        logger.debug("create menu:"+result);
        return parseResultCode(result);
    }

    @Override
    public int deleteMenu() {
        String deleteUrl=WeixinUrl.getDeleteMenuUrl(tokenService.getToken().getAccess_token());
        String result=HttpClientUtil.doGet(deleteUrl);
        logger.debug("delete menu:"+result);
        return parseResultCode(result);
    }

    @Override
    public String getMenu() {
        String getUrl=WeixinUrl.getGetMenuUrl(tokenService.getToken().getAccess_token());
        return HttpClientUtil.doGet(getUrl);
    }

    private int parseResultCode(String result){
        Error resultCode=JsonUtil.fromJson(result,Error.class);
        return resultCode.getErrcode()==0? ReturnCode.SUCC:ReturnCode.FAIL;
    }
}
