package com.dajie.wika.service;

import java.util.List;

import com.dajie.wika.model.Message;

public interface MessageService {

	/**
	 * 获取消息列表
	 * @param userId
	 * @param start
	 * @param offset
	 * @return
	 */
	public List<Message> getMessage(int userId,long timestamp,int limit);
	
	/**
	 * 
	 * @param fromId
	 * @param toId
	 * @param messageType
	 * @param payload
	 * @return
	 */
	public int SendMessage(Message message);
	
	/**
	 * 发消息但不触发push
	 * @param message
	 * @param push
	 * @return
	 */
	public int SendMessageNoPush(Message message);
	
	/**
	 * 获取用户的消息未读数
	 * @param userId
	 * @return
	 */
	public int getUnReadMessageCount(int userId);
	
	/**
	 * 将用户未读消息数置为0
	 * @param userId
	 * @return
	 */
	public int unReadMsgCountToZero(int userId);
	
	/**
	 * 将未读消息数置加1
	 * @param userId
	 * @return
	 */
	public int incrUnreadMsgCount(int userId);
	
	/**
	 * 用户更新信息，触发的消息包装
	 * 这种接口应该放到各种业务里面，或者单独提取出来生成消息工厂类。
	 * 尼玛时间有限，先这样了
	 * @param userId
	 * @return
	 */
	public int sendUserUpdateMessage(int userId);
}
