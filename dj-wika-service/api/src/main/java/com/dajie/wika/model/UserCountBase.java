package com.dajie.wika.model;

import java.io.Serializable;
import java.util.Date;

public class UserCountBase implements Serializable{

	private int userId;
	
	/**
	 * 关注数
	 */
	private int followingCount;
	
	/**
	 * 被关注数
	 */
	private int followerCount;
	
	/**
	 * 熟人数
	 */
	private int friendCount;
	
	/**
	 * 
	 */
	private int introduceCount;
	
	/**
	 * 主页被访问数
	 */
	private int visitedCount;
	
	/**
	 * 更新时间
	 */
	private Date updateTime;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getFollowingCount() {
		return followingCount;
	}

	public void setFollowingCount(int followingCount) {
		this.followingCount = followingCount;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	public int getFriendCount() {
		return friendCount;
	}

	public void setFriendCount(int friendCount) {
		this.friendCount = friendCount;
	}

	public int getIntroduceCount() {
		return introduceCount;
	}

	public void setIntroduceCount(int introduceCount) {
		this.introduceCount = introduceCount;
	}

	public int getVisitedCount() {
		return visitedCount;
	}

	public void setVisitedCount(int visitedCount) {
		this.visitedCount = visitedCount;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
