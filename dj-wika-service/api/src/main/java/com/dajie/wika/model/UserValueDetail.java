package com.dajie.wika.model;

import java.io.Serializable;
import java.util.List;

public class UserValueDetail implements Serializable{
	
	private int userId;

	private int userInfoValue;
	
	private int shareWikaValue;
	
	private int visitedValue;

	private int changeQRValue;
	
	private int changeWikaValue;
	
	private int followingValue;
	
	private int totalValue;
	
	private long updateTime;

	public int getUserInfoValue() {
		return userInfoValue;
	}

	public void setUserInfoValue(int userInfoValue) {
		this.userInfoValue = userInfoValue;
	}

	public int getShareWikaValue() {
		return shareWikaValue;
	}

	public void setShareWikaValue(int shareWikaValue) {
		this.shareWikaValue = shareWikaValue;
	}

	public int getVisitedValue() {
		return visitedValue;
	}

	public void setVisitedValue(int visitedValue) {
		this.visitedValue = visitedValue;
	}

	public int getChangeQRValue() {
		return changeQRValue;
	}

	public void setChangeQRValue(int changeQRValue) {
		this.changeQRValue = changeQRValue;
	}

	public int getChangeWikaValue() {
		return changeWikaValue;
	}

	public void setChangeWikaValue(int changeWikaValue) {
		this.changeWikaValue = changeWikaValue;
	}

	public int getFollowingValue() {
		return followingValue;
	}

	public void setFollowingValue(int followingValue) {
		this.followingValue = followingValue;
	}

	public int getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(int totalValue) {
		this.totalValue = totalValue;
	}

	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}	
}
