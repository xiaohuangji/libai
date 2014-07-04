package com.dajie.wika.service.utils;

public class MsgBodyUtil {

	private static final String MSG_BODY_1="新的朋友";
	
	private static final String MSG_BODY_1_SMS="%sXX向你发送了好友邀请,点击接受 http://www.wika001.com/system/download";
	
	private static final String MSG_BODY_1_PUSH="%s向你发送了好友邀请";
	
	private static final String MSG_BODY_2="由%s转发给我";
	
	private static final String MSG_BODY_2_SMS="%s向你转发了%s，互相认识一下吧，点击查看 http://www.wika001.com/system/download";
	
	private static final String MSG_BODY_2_PUSH="%s向你转发了%s，互相认识一下吧";
	
	private static final String MSG_BODY_3="%s把我转发给%s";
	
	private static final String MSG_BODY_3_PUSH="%s将你的微卡转发给了%s";
	
	private static final String MSG_BODY_4="更新了资料";
	
	private static final String MSG_BODY_4_PUSH="你的好友%s刚刚更新了微卡";
	
	private static final String MSG_BODY_68="微卡皮肤更新啦";
	
	private static final String MSG_BODY_69="微卡模板更新啦";
	
	
	/**
	 * @param msgType 消息类型
	 * @param name1
	 * @param name2
	 * @return
	 */
	static public String genMsgBody(int type,String name1,String name2){
		String body=null;
		switch(type){
		case 1:
			body=MSG_BODY_1;
			break;
		case 2:
			body=String.format(MSG_BODY_2,name1);
			break;
		case 3:
			body=String.format(MSG_BODY_3,name1,name2);
			break;
		case 4:
			body=MSG_BODY_4;
			break;
		case 68 :
			body=MSG_BODY_68;
			break;
		case 69 :
			body=MSG_BODY_69;
			break;
		default:
			break;
		}
		return body;
	}
	
	static public String genSmsMsgBody(int type,String name1,String name2){
		String body=null;
		switch(type){
		case 1:
			body=String.format(MSG_BODY_1_SMS,name1);
			break;
		case 2:
			body=String.format(MSG_BODY_2_SMS,name1);
			break;
		default:
			break;
		}
		return body;
	}
	
	static public String genPushMsgBody(int type,String name1,String name2){
		String body=null;
		switch(type){
		case 1:
			body=String.format(MSG_BODY_1_PUSH,name1,null);
			break;
		case 2:
			body=String.format(MSG_BODY_2_PUSH,name1,name2);
			break;
		case 3:
			body=String.format(MSG_BODY_3_PUSH,name1,name2);
			break;
		case 4:
			body=String.format(MSG_BODY_4_PUSH,name1,null);
			break;
		default:
			break;
		}
		return body;
	}
	
}
