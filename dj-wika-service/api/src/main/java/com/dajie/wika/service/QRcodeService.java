package com.dajie.wika.service;

import java.io.IOException;
import java.util.List;

import com.dajie.wika.model.UserAvatarSet;
import com.dajie.wika.model.UserQRSet;

public interface QRcodeService {

	/**
	 * 用户上传头像时，触发这个动作
	 * @param userId
	 * @param avatar
	 * @return
	 * @throws IOException 
	 */
	public UserAvatarSet genQRcode(int userId,String avatar,int gender);
	
	/**
	 * 获取所有的已生成的qrcode
	 * @param userId
	 * @return
	 */
	public List<UserQRSet> getAllQRcode(int userId);
	
	/**
	 * 获取qrurL
	 * @param userId
	 * @param qrId
	 * @return
	 */
	public String getQRcodeUrl(int userId,int qrId);
}
