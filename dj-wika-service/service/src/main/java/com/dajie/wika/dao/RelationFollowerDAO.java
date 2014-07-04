package com.dajie.wika.dao;

import java.util.List;

public interface RelationFollowerDAO {
	int isFollower(int userId, int followerId);

	int insert(int userId, int followerId);

	int delete(int userId, int followerId);

	List<Integer> getFollowers(int userId);

	List<Integer> getFollowersByPage(int userId, int lastFollowerId,
			int pageSize);

	Integer getFollowerCountByInterval(int userId, long startTime, long endTime);

	int getNewFollowerCount(int userId);
	
	int cleanNewFollower(int userId);
}
