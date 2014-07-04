package com.dajie.wika.dao;

import com.dajie.wika.model.UserOccupation;

public interface UserOccupationDAO {
	int insert(UserOccupation userOccupation);

	UserOccupation getByUserId(int userId);

	int update(UserOccupation userOccupation);

	int delete(int userId);

}
