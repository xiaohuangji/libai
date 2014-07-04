package com.dajie.wika.weixin.model.ServiceMessage;

/**
 * Created by wills on 2/10/14.
 */
public class ServiceNewsMessage extends ServiceMessage {

    private ServiceNews news;

    public ServiceNews getNews() {
        return news;
    }

    public void setNews(ServiceNews news) {
        this.news = news;
    }
}
