package com.dajie.wika.model.message;


/**
 * 召回消息
 * 包括一些统计信息
 * visitedCount表示访问人数
 * @author xing.feng
 *
 */
public class MsgPayload79 extends MsgPayload{

	private int visitedCount;
	
	public MsgPayload79(int visitedCount){
		this.visitedCount=visitedCount;
	}
}
