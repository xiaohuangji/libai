package com.dajie.wika.dao;

import java.util.List;

import com.dajie.wika.model.UserQRSet;

public interface QRcodeDAO {

	public int insertQRcode(List<UserQRSet> userQRSets);
	
	public List<UserQRSet> getQRcode(int userId);
	
	public String getQRcodeUrl(int userId,int qrId);
}
