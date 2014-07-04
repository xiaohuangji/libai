package com.dajie.wika.service.stub.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.wika.constants.MessageType;
import com.dajie.wika.model.Message;
import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.message.MsgPayload2;
import com.dajie.wika.model.message.MsgPayload3;
import com.dajie.wika.model.wrapper.MessageWrapper;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.MessageService;
import com.dajie.wika.service.stub.MessageServiceStub;
import com.dajie.wika.service.utils.MsgBodyUtil;
import com.dajie.wika.service.utils.WikaGson;

@Service("messageServiceStub")
public class MessageServiceStupImpl implements MessageServiceStub{

	private static final String newWikaIcon="http://1.f1.dajieimg.com/group1/M00/48/62/CgpAo1LY9nuAbPvJAAABCsDgQos573.png";
	
	private static final String newQRIcon="http://4.f1.dajieimg.com/group1/M00/48/62/CgpAo1LY90qAYj45AAABCsDgQos676.png";
	
	private static final String sysName="微卡";
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public List<MessageWrapper> getMessage(int userId, long timestamp, int limit) {
		// TODO Auto-generated method stub
		List<MessageWrapper> returnMsg=new ArrayList<MessageWrapper>();
		List<Message> msgList= messageService.getMessage(userId, timestamp, limit);
		if(msgList==null)
			return null;
		for(Message msg:msgList){
			MessageWrapper mWrapper=new MessageWrapper();
			try {
				BeanUtils.copyProperties(mWrapper, msg);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(msg.getType()==MessageType.NEWWIKA_68||msg.getType()==MessageType.NEWQR_69){
				mWrapper.setFromName(sysName);
				mWrapper.setFromAvatar(msg.getType()==MessageType.NEWWIKA_68?newWikaIcon:newQRIcon);
				mWrapper.setTimestamp(msg.getCreateTime().getTime());
				mWrapper.setContent(genMsgBody(msg));
			}else{
				UserBase  userBase=accountService.getUserBaseById(msg.getFromId());
				if(userBase!=null){
					mWrapper.setFromName(userBase.getName());
					mWrapper.setFromAvatar(userBase.getAvatar());
					mWrapper.setTimestamp(msg.getCreateTime().getTime());
					mWrapper.setContent(genMsgBody(msg));
				}		
			}
			returnMsg.add(mWrapper);
		}
		
		return returnMsg;
	}

	private String genMsgBody(Message msg){
		try{
			int userId=0;
			String name1=null;
			String name2=null;
			switch(msg.getType()){
			case 1:
				return MsgBodyUtil.genMsgBody(1, null,null);
			case 2:
				name1=accountService.getUserBaseById(msg.getFromId()).getName();
				userId=WikaGson.fromJson(msg.getPayload(),MsgPayload2.class).getUserId();
				name2=accountService.getUserBaseById(userId).getName();
				return MsgBodyUtil.genMsgBody(2, name2,name1);
			case 3:
				name1=accountService.getUserBaseById(msg.getFromId()).getName();
				userId=WikaGson.fromJson(msg.getPayload(),MsgPayload3.class).getUserId();
				name2=accountService.getUserBaseById(userId).getName();
				return MsgBodyUtil.genMsgBody(3, name2,name1);
			case 4:
				return MsgBodyUtil.genMsgBody(4, null,null);
			case 68:
				return MsgBodyUtil.genMsgBody(68, null, null);
			case 69:
				return MsgBodyUtil.genMsgBody(69, null, null);
			default:
				return "您有一条新消息。";
		}
		}catch(Exception e){
			return "您有一条新消息";
		}
		
	}
}
