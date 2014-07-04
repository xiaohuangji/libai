package com.dajie.wika.model.message;


/**
 * app更新信息
 * @author xing.feng
 *
 */
public class MsgPayload89 extends MsgPayload{

	private String versionCode;
	
	private String releaseLog;
	
	private String downloadUrl;
	
	public MsgPayload89(String versionCode,String releaseLong,String downloadUrl){
		this.versionCode=versionCode;
		this.releaseLog=releaseLong;
		this.downloadUrl=downloadUrl;
	}
	
}
