package com.dajie.wika.dao;

import com.dajie.wika.model.UserBase;

public interface UserBaseDAO {
	int insert(UserBase userBase);

	UserBase getUserBaseById(int userId);

	UserBase getUserBaseByMobile(String mobile);

	int update(UserBase userBase);
	
	int updateUserBaseNoAvatar(UserBase userBase);

	int updatePwd(int userId, String pwd);

	int updateMobile(int userId, String mobile);

	int updateWikaTemplate(int userId, int wikaTemplate);

	int updateUserQRCode(int userId, int faceCodeType, String faceQRCode);

	int updateActivition(int userId, int activation);
	
	int updateAvatar(int userId,String avatar);
	
	int updateAvatarAndQRCode(int userId,String avatar,int faceCodeType,String faceQRCode);
	
	int updateMobileAndPwd(int userId,String mobile,String password);
	
	int cleanUnconfirmedMobile(String mobile);
}
