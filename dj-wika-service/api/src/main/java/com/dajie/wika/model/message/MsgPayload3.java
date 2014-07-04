package com.dajie.wika.model.message;

/**
 * wika被推荐时间生的消息
 * a向b推荐c
 * c收到的消息
 * userId表示b,即推荐给谁
 *
 */
public class MsgPayload3 extends MsgPayload{

	private  int userId;
	
	public MsgPayload3(int userId){
		this.userId=userId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
}
