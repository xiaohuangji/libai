package com.dajie.wika.model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable{

	protected long id;
	
	protected int fromId;
	
	protected int toId;
	
	protected int type;
	
	protected Date createTime;
	
	protected String payload;
	
	protected String content;
	
	public Message(){
		
	}
	
	public Message(int fromId,int type){
		this.fromId = fromId;
	    this.type=type;
	}
	
	public Message(int fromId,int toId,int type){
		this.fromId = fromId;
		this.toId = toId;
	    this.type=type;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getFromId() {
		return fromId;
	}

	public void setFromId(int fromId) {
		this.fromId = fromId;
	}

	public int getToId() {
		return toId;
	}

	public void setToId(int toId) {
		this.toId = toId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
