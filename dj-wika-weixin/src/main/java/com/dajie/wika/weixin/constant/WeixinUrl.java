package com.dajie.wika.weixin.constant;

/**
 * Created by wills on 2/8/14.
 */
public class WeixinUrl {

    /**
     * get
     */
    private  static final String tokenUrl="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /**
     * post
     */
    private static final String serviceUrl="https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";

    /**
     * post
     */
    private static final String createMenuUrl="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";

    /**
     * get
     */
    private static final String getMenuUrl="https://api.weixin.qq.com/cgi-bin/menu/get?access_token=%s";

    /**
     * get
     */
    private static final String deleteMenuUrl="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%s";

    /**
     * get 获取用户信息
     */
    private static final String getUserInfoUrl="https://api.weixin.qq.com/cgi-bin/user/info?access_token=%s&openid=%s&lang=zh_CN";

    /**
     * get 获取资源url
     */
    private static final String getResourceUrl="http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=%s&media_id=%s";

    public static String getGetResourceUrl(String token,String mediaId){
        return String.format(getResourceUrl,token,mediaId);
    }

    public static String getUserInfoUrl(String token,String openid){
        return String.format(getUserInfoUrl,token,openid);
    }

    public static String getTokenUrl(){
        return String.format(tokenUrl,Weixin.APPID,Weixin.AppSecret);
    }

    public static String getServiceUrl(String token){
        return String.format(serviceUrl,token);
    }

    public static String getCreateMenuUrl(String token){
        return String.format(createMenuUrl,token);
    }

    public static String getGetMenuUrl(String token){
        return String.format(getMenuUrl,token);
    }

    public static String getDeleteMenuUrl(String token){
        return String.format(deleteMenuUrl,token);
    }
}
