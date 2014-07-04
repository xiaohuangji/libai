package com.dajie.wika.model.message;

/**
 * wika被推荐时间生的消息
 * a向b推荐c
 * b收到的消息
 * userId表示c,即被推荐的人
 * @author xing.feng
 *
 */
public class MsgPayload2 extends MsgPayload{

	/**
	 * 被推荐人Id
	 */
	private int userId;
	
	/**
	 * 推荐理由
	 */
	private String reason;
	
	public MsgPayload2(int userId,String reason){
		this.userId=userId;
		this.reason=reason;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	
	}
}
