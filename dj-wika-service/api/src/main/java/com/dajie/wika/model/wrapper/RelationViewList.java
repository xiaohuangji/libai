package com.dajie.wika.model.wrapper;

import java.io.Serializable;
import java.util.List;

public class RelationViewList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 226321784491013379L;

	// 主要的列表信息
	private List<RelationUserView> relationUserViews;

	// 附加的统计信息
	private int followingCount;

	private int followerCount;

	private int friendCount;

	public List<RelationUserView> getRelationUserViews() {
		return relationUserViews;
	}

	public void setRelationUserViews(List<RelationUserView> relationUserViews) {
		this.relationUserViews = relationUserViews;
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

}
