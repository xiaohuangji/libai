package com.dajie.wika.dao;

import java.util.List;

public interface RelationFriendDAO {
	int insert(int userId, int friendId);

	int delete(int userId, int friendId);

	List<Integer> getFriends(int userId);

	List<Integer> getFriendsByPage(int userId, int lastFriendId, int pageSize);
}
