package com.dajie.wika.push.client;

import com.dajie.wika.model.Message;
import com.dajie.wika.model.UserDevice;

public interface PushClient {

	public int pushone(UserDevice userDevice,Message message,int unreadMsgCount);
}
