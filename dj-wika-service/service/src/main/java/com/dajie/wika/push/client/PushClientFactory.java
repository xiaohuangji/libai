package com.dajie.wika.push.client;

import com.dajie.wika.constants.DeviceType;
import com.dajie.wika.constants.returncode.ResultCodeConstant;
import com.dajie.wika.model.Message;
import com.dajie.wika.model.UserDevice;

public class PushClientFactory {

	  public static int pushone(UserDevice userDevice ,Message message,int unreadMsgCount){
          PushClient  pushClient=null;
          if(userDevice.getDeviceType()==DeviceType.IOS){
                  pushClient=ApplePushClient.getInstance();
          }else if(userDevice.getDeviceType()==DeviceType.ANDROID){
                  pushClient=AndroidPushClient.getInstance();
          }else{
                  return ResultCodeConstant.OP_FAIL;
          }
          return pushClient.pushone(userDevice, message,unreadMsgCount);
  }
}
