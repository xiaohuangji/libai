package com.dajie.wika.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.wika.dao.UserOccupationDAO;
import com.dajie.wika.model.UserOccupation;
import com.dajie.wika.service.OccupationService;

@Service("occupationService")
public class OccupationServiceImpl implements OccupationService {

	@Autowired
	private UserOccupationDAO userOccupationDAO;
	
	@Override
	public int updateOccupation(UserOccupation userOccupation) {
		// TODO Auto-generated method stub
		return userOccupationDAO.update(userOccupation);
	}

	@Override
	public UserOccupation getOccupation(int userId) {
		// TODO Auto-generated method stub
		return userOccupationDAO.getByUserId(userId);
	}

}
