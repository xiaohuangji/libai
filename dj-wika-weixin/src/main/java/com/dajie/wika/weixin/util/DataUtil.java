package com.dajie.wika.weixin.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DataUtil {

	private static final Logger logger=LoggerFactory.getLogger(DataUtil.class);
	
	private static String absolutePath =Thread.currentThread()
            .getContextClassLoader().getResource("").getPath();
	
	private static final String menuFile="menu.txt";

    private static final String subscribeFile="subscribe.txt";

    private static final String response="response.txt";

    private static  String menuInfo=null;

    private static  String subscribeInfo=null;

    private static Map<String,String> responseMap=null;

    public static String getSubscribeResponse(String openid){
        if(subscribeInfo==null){
            subscribeInfo=getInfoFromFile(subscribeFile);
        }
        return String.format(subscribeInfo,openid,openid,openid,openid);
    }

	public static String getMenuInfo() {
        if(menuInfo==null){
            menuInfo=getInfoFromFile(menuFile);
        }
        return menuInfo;
	}

    public static String getTextResponse(String text){
        if(responseMap==null){
            String info=getInfoFromFile(response);
            responseMap=JsonUtil.jsonToMap(info);
        }
        return responseMap.get(text);
    }

    private static String getInfoFromFile(String fileName){
        try{
            File file = new File(absolutePath+fileName);
            StringBuilder sb = new StringBuilder();
            String s ="";

            InputStreamReader read = new InputStreamReader (new FileInputStream(file),"UTF-8");
            BufferedReader br = new BufferedReader(read);
            while( (s = br.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
            br.close();
            String str = sb.toString();
            return str;
        }catch(Exception e){
            //System.out.print(e);
            logger.error("load info from file fail",e);
            return null;
        }
    }
	
	public static void main(String[] args) {
        System.out.println(DataUtil.getMenuInfo());
        System.out.print(DataUtil.getTextResponse("11A"));
	}
}
