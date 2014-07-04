package com.dajie.wika.weixin.controllers;

import com.dajie.wika.weixin.model.Token;
import com.dajie.wika.weixin.service.TokenService;
import com.dajie.wika.weixin.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wills on 2/10/14.
 */
@Controller
@RequestMapping("token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("get")
    @ResponseBody
    public String getToken(){
        Token token=tokenService.getToken();
        return JsonUtil.toJson(token);
    }

    @RequestMapping("refresh")
    @ResponseBody
    public String refreshToken(){
        Token token=tokenService.refreshToken();
        return JsonUtil.toJson(token);

    }

}
