package com.dajie.wika.service.impl;

import java.util.List;

import javassist.compiler.NoFieldException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.common.dubbo.tolerance.Idempotent;
import com.dajie.wika.cache.CacheKey;
import com.dajie.wika.cache.JedisCacheUtil;
import com.dajie.wika.constants.DeviceType;
import com.dajie.wika.constants.MessageType;
import com.dajie.wika.constants.UserActivationConstant;
import com.dajie.wika.constants.returncode.ResultCodeConstant;
import com.dajie.wika.dao.MessageDAO;
import com.dajie.wika.model.Message;
import com.dajie.wika.model.NotificationSettings;
import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.UserDevice;
import com.dajie.wika.model.message.MsgPayload2;
import com.dajie.wika.model.message.MsgPayload3;
import com.dajie.wika.push.client.ApplePushClient;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.IdSequenceService;
import com.dajie.wika.service.MessageService;
import com.dajie.wika.service.RelationService;
import com.dajie.wika.service.SMSSendService;
import com.dajie.wika.service.UserSettingsService;
import com.dajie.wika.service.utils.MsgBodyUtil;
import com.dajie.wika.service.utils.WikaGson;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDAO messageDAO;

	@Autowired
	private IdSequenceService idSequenceService;

	@Autowired
	private UserSettingsService userSettingsService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private JedisCacheUtil jedisCacheUtil;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private SMSSendService smsSendService;

	@Override
	public List<Message> getMessage(int userId, long timestamp, int limit) {
		// 每次get的时候将unreadCount置为0
		unReadMsgCountToZero(userId);
		return messageDAO.getMessages(userId, timestamp, limit);
	}


	@Override
	@Idempotent(value = false)
	public int SendMessageNoPush(Message message) {
		// 判断用户通知开关
		NotificationSettings  notifactionSettings=userSettingsService.getNotificationsSettings(message.getToId());
		if(notifactionSettings.isNewFollowerNotice()==false&&message.getType()==MessageType.COLLECTED_1){
			return ResultCodeConstant.OP_FAIL;
		}
		if(notifactionSettings.isContactWikaUpdateNotice()==false&&message.getType()==MessageType.FRIENDUPDATE_4){
			return ResultCodeConstant.OP_FAIL;
		}
		int toId = message.getToId();
		// 消息存储
		long msgId = idSequenceService.getNextMsgId();
		// 获取msgId
		message.setId(msgId);
		messageDAO.insert(message);
		// 未读消息计数累加并返回
		incrUnreadMsgCount(toId);
		return ResultCodeConstant.OP_SUCC;
	}
	
	@Override
	@Idempotent(value = false)
	public int SendMessage(Message message) {
		if(SendMessageNoPush(message)==ResultCodeConstant.OP_FAIL){
			return ResultCodeConstant.OP_FAIL;
		}
		
		// 发送push
		UserDevice userDevice = userSettingsService.getDeviceTokenByUserIdAndType(message.getToId(),DeviceType.IOS);
		if (userDevice != null) {
			message.setContent(genMsgBody(message));
			ApplePushClient.getInstance().pushone(userDevice, message, getUnReadMessageCount(message.getToId()));
		}
		//固定的消息类型，触发短信提醒逻辑
		//短信通知先关掉
		/*int msgType=message.getType();
		if(msgType==MessageType.COLLECTED_1||msgType==MessageType.RECOMMENDED_3||msgType==MessageType.NEWQR_69||
				msgType==MessageType.NEWWIKA_68){
			//发送短信
			UserBase ub=accountService.getUserBaseById(message.getToId());
			if(ub.getActivation()==UserActivationConstant.APP_AVTIVE){
				//未下载app,发短信
				String content=genSmsMsgBody(message);
				if(content!=null)
					smsSendService.smsSend(ub.getMobile(),content);
			}
		}*/
		return ResultCodeConstant.OP_SUCC;
	}

	public String genMsgBody(Message msg){
		try{
			int userId=0;
			String name1=accountService.getUserBaseById(msg.getFromId()).getName();;
			String name2=null;
			switch(msg.getType()){
			case 1:
				//name1=accountService.getUserBaseById(msg.getFromId()).getName();
				return MsgBodyUtil.genPushMsgBody(1, name1,null);
			case 2:				
				userId=WikaGson.fromJson(msg.getPayload(),MsgPayload2.class).getUserId();
				name2=accountService.getUserBaseById(userId).getName();
				return MsgBodyUtil.genPushMsgBody(2, name2,name1);
			case 3:
				//name1=accountService.getUserBaseById(msg.getFromId()).getName();
				userId=WikaGson.fromJson(msg.getPayload(),MsgPayload3.class).getUserId();
				name2=accountService.getUserBaseById(userId).getName();
				return MsgBodyUtil.genPushMsgBody(3, name2,name1);
			case 4:
				//name1=accountService.getUserBaseById(msg.getFromId()).getName();
				return MsgBodyUtil.genPushMsgBody(4, name1,null);
			default:
				return "您有一条新消息。";
		}
		}catch(Exception e){
			return "您有一条新消息";
		}
		
	}
	
	@Override
	public int getUnReadMessageCount(int userId) {
		// TODO Auto-generated method stub
		String countCache=jedisCacheUtil.get(CacheKey.getUnreadMsgCountKey(userId));
		return countCache==null?0:Integer.valueOf(countCache);
	}

	@Override
	public int unReadMsgCountToZero(int userId) {
		// TODO Auto-generated method stub
		jedisCacheUtil.delete(CacheKey.getUnreadMsgCountKey(userId));
		return ResultCodeConstant.OP_SUCC;
	}

	@Override
	public int incrUnreadMsgCount(int userId) {
		// TODO Auto-generated method stub
		Long result=jedisCacheUtil.incr(CacheKey.getUnreadMsgCountKey(userId));
		return result==null?1:result.intValue();
	}
	
	private String genSmsMsgBody(Message msg){
		try{
			String name1=accountService.getUserBaseById(msg.getFromId()).getName();
			int userId=0;
			String name2=null;
			switch(msg.getType()){
			case 1:
				return MsgBodyUtil.genSmsMsgBody(1, name1,null);
			case 2:
				userId=WikaGson.fromJson(msg.getPayload(),MsgPayload2.class).getUserId();
				name2=accountService.getUserBaseById(userId).getName();
				return MsgBodyUtil.genSmsMsgBody(2, name1,name2);
			default:
				return null;
		}
		}catch(Exception e){
			return null;
		}
		
	}
	
	@Override
	public int sendUserUpdateMessage(int userId){
		List<Integer> idList=relationService.getFriendIds(userId);
		if(idList==null) return ResultCodeConstant.OP_SUCC;
		Message message=new Message(userId, MessageType.FRIENDUPDATE_4);
		for(Integer toId:idList){
			message.setToId(toId);
			SendMessage(message);
		}
		return ResultCodeConstant.OP_SUCC;
	}

}
