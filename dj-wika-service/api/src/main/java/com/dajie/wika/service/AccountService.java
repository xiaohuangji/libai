package com.dajie.wika.service;

import java.util.List;

import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.UserOccupation;
import com.dajie.wika.model.wrapper.ActiveDJReturn;
import com.dajie.wika.model.wrapper.LoginReturn;
import com.dajie.wika.model.wrapper.RegisterReturn;

/**
 * 账号相关
 * 
 * @author xing.feng
 * 
 */
public interface AccountService {

	/**
	 * wika登录 手机号密码登录
	 * 
	 * @param mobile
	 * @param password
	 * @param source
	 * @return 返回状态码
	 */
	public LoginReturn login(String mobile, String password,int source);

	/**
	 * 通过大街账号登录 acount可能是email或手机号
	 * 
	 * @param account
	 * @param password
	 * @param source
	 * @return 返回状态码
	 */
	public LoginReturn loginWithDJ(String account, String password,int source);

	/**
	 * 给指定手机号发送验证码
	 * 
	 * @param mobile
	 * @return
	 */
	public int getVerifyCode(String mobile);

	/**
	 * wiki注册
	 * 
	 * @param mobile
	 * @param verifyCode
	 * @param password
	 * @param source
	 * @return
	 */
	public RegisterReturn register(String mobile, String verifyCode,
			String password,int source);

	/**
	 * 密码忘记后通过发送验证码改密码
	 * 
	 * @param mobile
	 * @param verifyCode
	 * @param newPassword
	 * @return
	 */
	public int changePasswordWithVerifyCode(String mobile, String verifyCode,
			String newPassword);

	/**
	 * 校验验证码是否正确
	 * 
	 * @param mobile
	 * @param verifyCode
	 * @return
	 */
	public boolean validateVerifyCode(String mobile, String verifyCode);

	/**
	 * 修改密码
	 * 
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	public int changePassword(int userId, String oldPassword, String newPassword);

	/**
	 * 绑定手机号
	 * 
	 * @param userId
	 * @param mobile
	 * @param verifyCode
	 * @param password
	 * @return
	 */
	public int bindMobile(int userId, String mobile, String verifyCode,
			String password);

	/**
	 * 更新用户信息
	 */
	int updateUserInfo(int userId, String wikaId, String name,
			String email, int gender, String location,
			String description, long birth, String corp, int industry,
			String position, int jobType, String department);

	/**
	 * 修改脸码
	 * @param userId
	 * @param faceCodeType
	 * @param faceQRCode
	 * @return
	 */
	int updateUserQRCode(int userId,int faceCodeType,String faceQRCode);
	
	/**
	 * 修改微卡模板
	 * @param userId
	 * @param wikaTemplate
	 * @return
	 */
	int updateWikaTemplate(int userId,int wikaTemplate);
	
	/**
	 * 更新头像
	 * @param userId
	 * @param avatar
	 * @return
	 */
	int updateAvatar(int userId,String avatar);
	
	/***
	 * 更改头像和脸码
	 * @param userId
	 * @param avatar
	 * @param faceCodeType
	 * @param faceQRCode
	 * @return
	 */
	int updateAvatarAndQRCode(int userId,String avatar,int faceCodeType,String faceQRCode);
	
	/**
	 * 更新用户基本信息
	 * 
	 * @param userBase
	 * @return
	 */
	public int updateUserBase(UserBase userBase);

	/**
	 * 用户设置成wap级激活，只对未激活的用户有效，app级不变
	 * @param userId
	 * @return
	 */
	public void setWapActivity(int userId);
	
	public UserBase getUserBaseById(int userId);

	public List<UserBase> getUserBasesByIds(List<Integer> userIds);

	public UserBase getUserBaseByMobile(String mobile);

	public List<UserOccupation> getOccupationsByUserIds(List<Integer> userIds);

	/**
	 * 更新用户信息(不更新头像)
	 * @param userBase
	 * @return
	 */
	int updateUserBaseNoAvatar(UserBase userBase);

	/**
	 * 激活大街帐号，设置手机号和密码
	 * @param userId
	 * @param mobile
	 * @param verifyCode
	 * @param password
	 * @param source 来源 @see{com.dajie.wika.constants.SourceTypeConstant}
	 * @return
	 */
	ActiveDJReturn activeDJAccount(int userId, String mobile, String verifyCode,
			String password,int source);

	/**
	 * 是否是一个完成了必须信息的用户
	 * @param userId
	 * @param source 调用的来源,如果是wap，会补默认头像
	 * @return
	 */
	boolean isCompleteRequriedInfo(int userId,int source);

	/**
	 * 是否完善了必要信息
	 * @param userId
	 * @return
	 */
	boolean isCompleteRequriedInfo(int userId);
	
	/**
	 * mobile是否被用过
	 * @param mobile
	 * @return
	 */
	boolean isMobileUsed(String mobile);
}
