package com.dajie.wika.dao;

import java.util.List;

public interface RelationFollowingDAO {
	int isFollowing(int userId,int followingId);
	
	int insert(int userId, int followingId);

	int delete(int userId, int followingId);

	List<Integer> getFollowings(int userId);

	List<Integer> getFollowingsByPage(int userId, int lastFollowingId,int pageSize);
	
	Integer getFollowingCountByInterval(int userId,long startTime,long endTime);
}
