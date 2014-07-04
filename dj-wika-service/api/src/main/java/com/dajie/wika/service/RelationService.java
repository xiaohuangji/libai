package com.dajie.wika.service;

import java.util.List;

import com.dajie.wika.model.wrapper.RelationViewList;

public interface RelationService {

	/**
	 * 关注
	 * 
	 * @param userId
	 * @param followId
	 * @return
	 */
	public int follow(int userId, int followId);

	/**
	 * 取消关注
	 * 
	 * @param userId
	 * @param unfollowingId
	 * @return
	 */
	public int unfollow(int userId, int unfollowId);

	/**
	 * 获取认识我的人
	 * 
	 * @param userId
	 * @return
	 */
	public RelationViewList getFollowers(int userId);

	/**
	 * 获取我认识的人
	 * 
	 * @param userId
	 * @return
	 */
	public RelationViewList getFollowings(int userId);

	/**
	 * 获取相互认识的朋友
	 * 
	 * @param userId
	 * @param offset
	 *            limit 分页参数
	 * @return
	 */
	public RelationViewList getFriends(int userId);

	/**
	 * 获取following的id
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> getFollowingIds(int userId);

	/**
	 * 获取follower的id
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> getFollowerIds(int userId);

	/**
	 * 获取friend的id
	 * 
	 * @param userId
	 * @return
	 */
	public List<Integer> getFriendIds(int userId);

	/**
	 * 打招呼
	 * 
	 * @param userId发起人
	 * @return
	 */
	public int sayHi(int userId, int toId);

	/**
	 * 介绍sourceId给destId
	 * 
	 * @param userId动作发起人
	 * @param sourceId被推荐人
	 * @param destId
	 * @return
	 */
	public int introduce(int userId, int sourceId, List<Integer> destId, String content);

	/**
	 * userId是否已经关注过followId
	 * 
	 * @param userId
	 * @param followId
	 * @return
	 */
	boolean isFollowing(int userId, int followId);

	/**
	 * userId是否被followerId关注
	 * 
	 * @param userId
	 * @param followerId
	 * @return
	 */
	boolean isFollower(int userId, int followerId);

	/**
	 * 获取关系类型 ,对于visitedId可言
	 * 
	 * @see com.dajie.wika.constants.RelationType
	 * @param userId
	 * @param visitId
	 * @return
	 */
	int getRelation(int userId, int visitedId);

	/**
	 * 获取新的想认识你的人的个数
	 * @param userId
	 * @return
	 */
	int getNewFollowerCount(int userId);

	/**
	 * 清楚新的想认识你的人
	 * @param userId
	 */
	void cleanNewFollower(int userId);
	
	/**
	 * 分享wika到微信
	 * @param userId
	 * @return
	 */
	int shareWika(int userId);
	
	
}
