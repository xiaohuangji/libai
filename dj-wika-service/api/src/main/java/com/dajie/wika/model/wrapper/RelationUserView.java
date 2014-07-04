package com.dajie.wika.model.wrapper;

import java.io.Serializable;

public class RelationUserView implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2721494624493081831L;

	private int userId;
	
	private String avatar;

	private String name;

	private String corp;

	private String position;

	private int relationType;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getRelationType() {
		return relationType;
	}

	public void setRelationType(int relationType) {
		this.relationType = relationType;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	
}
