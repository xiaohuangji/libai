package com.dajie.wika.push.client;

import com.dajie.wika.model.Message;
import com.dajie.wika.model.UserDevice;

public class AndroidPushClient implements PushClient {

	private static  AndroidPushClient pushClient=null;
	
	private AndroidPushClient(){
		
	}
	
	public static AndroidPushClient getInstance(){
         if(pushClient==null){
                 pushClient=new AndroidPushClient();
         }
         return pushClient;
	}
	 
	@Override
	public int pushone(UserDevice userDevice, Message message,int unreadMsgCount) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
