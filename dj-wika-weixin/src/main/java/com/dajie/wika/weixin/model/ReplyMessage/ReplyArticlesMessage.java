package com.dajie.wika.weixin.model.ReplyMessage;

import com.dajie.wika.weixin.constant.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.List;

/**
 * Created by wills on 2/10/14.
 */
@XStreamAlias("xml")
public class ReplyArticlesMessage extends ReplyMessage {

    @XStreamAlias("ArticleCount")
    private int articleCount;

    @XStreamAlias("Articles")
    private List<Item> articles;

    public ReplyArticlesMessage() {
        super.setMsgType(MessageType.NEWS);
    }

    public int getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(int articleCount) {
        this.articleCount = articleCount;
    }

    public List<Item> getArticles() {
        return articles;
    }

    public void setArticles(List<Item> articles) {
        this.articles = articles;
    }
}
