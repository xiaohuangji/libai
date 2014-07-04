package com.dajie.wika.weixin.util;

import com.dajie.wika.weixin.constant.WeixinUrl;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;

/**
 * Created by wills on 2/11/14.
 */
public class HttpClientUtil {

    private static final Logger logger= LoggerFactory.getLogger(HttpClientUtil.class);

    private static HttpClient httpClient=null;

    private static HttpClient getHttpClient(){
        if(httpClient==null){
            HttpParams httpParams=new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,20*1000);
            HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
            httpClient=new DefaultHttpClient();
        }
        return httpClient;
    }

    public static String doGet(String url){
        HttpGet httpGet=new HttpGet(url);
        try {
            HttpResponse httpResponse=getHttpClient().execute(httpGet);
            int httpCode=httpResponse.getStatusLine().getStatusCode();
            if (httpCode== 200) {
                return EntityUtils.toString(httpResponse.getEntity(),"utf-8");
            }
            logger.warn("http get error:"+url+"---"+httpCode);
        }catch(Exception e){
            logger.warn("http get exception",e);
        }
        return null;
    }

    public static byte[] doGetByte(String url){
        HttpGet httpGet=new HttpGet(url);
        try {
            HttpResponse httpResponse=getHttpClient().execute(httpGet);
            int httpCode=httpResponse.getStatusLine().getStatusCode();
            if (httpCode== 200) {
                byte[] content = EntityUtils.toByteArray(httpResponse.getEntity());
                return content;
            }
            logger.warn("http get error:"+url+"---"+httpCode);
        }catch(Exception e){
            logger.warn("http get exception",e);
        }
        return null;
    }

    public static String doPost(String url,String data){
        try {
            HttpPost httpPost=new HttpPost(url);
            httpPost.setEntity(new StringEntity(data,"UTF-8"));
            HttpResponse httpResponse=getHttpClient().execute(httpPost);
            int httpCode=httpResponse.getStatusLine().getStatusCode();
            if (httpCode== 200) {
                return EntityUtils.toString(httpResponse.getEntity());
            }
            logger.warn("http post error:"+url+"---"+httpCode);
        }catch(Exception e){
            logger.warn("http post exception",e);
        }
        return null;
    }

    public static void main(String[] args) {
        //System.out.println(HttpClientUtil.doGet(WeixinUrl.getTokenUrl()));
        System.out.println(HttpClientUtil.doPost(WeixinUrl.getServiceUrl("sss"),"1111"));
    }
}
