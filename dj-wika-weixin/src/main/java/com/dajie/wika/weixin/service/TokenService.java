package com.dajie.wika.weixin.service;

import com.dajie.wika.weixin.model.Token;
/**
 * Created by wills on 2/10/14.
 */
public interface TokenService {

    /**
     * 获取已经拉取到的token
     * @return
     */
    public Token getToken();

    /**
     * 拉取token，存入系统，并返回
     * @return
     */
    public Token refreshToken();
}
