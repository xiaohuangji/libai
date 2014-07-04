package com.dajie.wika.wap.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    public   static   String filter(String   str)    {            
         // String   regEx  =  "[^a-zA-Z0-9]";                     
           // 清除掉所有特殊字符  
		  String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";  
		  Pattern   p   =   Pattern.compile(regEx);     
		  Matcher   m   =   p.matcher(str);     
		  return   m.replaceAll("").trim();
    }
    
    public static void main(String[] args) {
		//String test="fsdf12123!@#$${}<>";
		//System.out.println(StringUtil.filter(test));
    	Integer s=3000;
    	Integer ss=3000;
    	System.out.println(s==ss);
	}
    
}
