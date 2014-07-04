package com.dajie.wika.model.message;

/**
 * 账号异常消息
 * @author xing.feng
 *
 */
public class MsgPayload99 extends MsgPayload{

	private String reason;
	
	public MsgPayload99(String reason){
		this.reason=reason;
	}
}
