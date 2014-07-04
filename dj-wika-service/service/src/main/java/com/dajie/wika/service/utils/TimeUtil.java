package com.dajie.wika.service.utils;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	private static long millsecondsOfDay=24*60*60*1000;
	
	/**
	 * flag传0表示获取当天的开始时间
	 * -1表示昨天的开始时间
	 * +1表示明天的开始时间
	 * 依次累推
	 * @param flag
	 * @return
	 */
	public  static long getStartTime(int flag){  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        todayStart.set(Calendar.AM_PM, Calendar.AM);
        return todayStart.getTime().getTime()+flag*millsecondsOfDay;  
    }  
      
    public static long getEndTime(int flag){  
        Calendar todayEnd = Calendar.getInstance();  
        todayEnd.set(Calendar.HOUR, 11);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
        todayEnd.set(Calendar.MILLISECOND, 999);  
        todayEnd.set(Calendar.AM_PM, Calendar.PM);
        return todayEnd.getTime().getTime()+flag*millsecondsOfDay; 
    } 
    
    public static void main(String[] args) { 
    	long start=TimeUtil.getStartTime(0);
    	long end=TimeUtil.getEndTime(0);
    	long cur=System.currentTimeMillis();
    	System.out.println(start);
    	System.out.println(new Date(start));
    	
    	
    	System.out.println(end);
    	System.out.println(new Date(end));
    	
    	System.out.println((cur-start)/1000);
    	
    	int s=new Long((TimeUtil.getEndTime(0) - System.currentTimeMillis()) / 1000).intValue();
    	System.out.println(s);
    	//System.out.println(System.currentTimeMillis());
    	//System.out.println(TimeUtil.getStartTime(1));
	}
}
