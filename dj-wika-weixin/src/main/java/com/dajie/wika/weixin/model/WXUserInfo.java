package com.dajie.wika.weixin.model;

/**
 * Created by wills on 2/14/14.
 */
public class WXUserInfo {

    private String openid;

    private String nickname;

    private int sex;

    private String language;

    private String city;

    private String province;

    private String country;

    private String headimgurl;

    private String headimgurl_upload;

    private long subscribe_time;

    private int subscribe;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public long getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(long subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public int getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(int subscribe) {
        this.subscribe = subscribe;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getHeadimgurl_upload() {
        return headimgurl_upload;
    }

    public void setHeadimgurl_upload(String headimgurl_upload) {
        this.headimgurl_upload = headimgurl_upload;
    }
}
