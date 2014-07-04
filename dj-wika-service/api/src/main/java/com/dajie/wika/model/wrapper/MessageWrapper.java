package com.dajie.wika.model.wrapper;

import com.dajie.wika.model.Message;

public class MessageWrapper extends Message {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3633970334379806905L;

/*	public MessageWrapper(int fromId, int toId, int type) {
		super(fromId, toId, type);
	}
	*/
	public MessageWrapper(){
		
	}
	private long timestamp;

	private String fromName;

	private String fromAvatar;

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromAvatar() {
		return fromAvatar;
	}

	public void setFromAvatar(String fromAvatar) {
		this.fromAvatar = fromAvatar;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
