package com.dajie.wika.wap.model;

import java.util.HashMap;
import java.util.Map;

import com.dajie.wika.constants.returncode.AccountReturnCodeConstant;

public class ErrorMsg {

	//登录错误文案
	private static final Map ErrorMsg=new HashMap<Integer, String>(){{
		put(AccountReturnCodeConstant.ACCOUNT_FREEZEN,"帐号被冻结，请联系客服");
		put(AccountReturnCodeConstant.PASSWORD_ERROR, "密码不正确，请确认后重试");
		put(AccountReturnCodeConstant.ACCOUNT_NOT_EXIST, "帐号未注册，请注册后再登录");
		put(AccountReturnCodeConstant.MOBILE_HAS_USED,"手机号已存在，请换手机号重试或者直接用这个手机号登录");
		put(AccountReturnCodeConstant.VERIFY_CODE_ERROR, "验证码输入错误，请重试");
		put(AccountReturnCodeConstant.OLD_PWD_ERROR, "旧密码不正确，请确认后重试");
		put(AccountReturnCodeConstant.RETRY_TIME_LIMIT, "每天向同一号码至多发送10次验证码，已经超过次数了");
		put(AccountReturnCodeConstant.DJ_ACCOUNT_LOGIN_FAIL, "此大街网帐号状态异常");
		put(AccountReturnCodeConstant.DJ_ACCOUNT_PASSWORD_ERROR, "大街密码错误密码不正确，请确认后重试");
		put(AccountReturnCodeConstant.DJ_ACCOUNT_NO_EXIST, "此大街网帐号不存在");
		put(AccountReturnCodeConstant.DJ_ACCOUNT_DISABLE,"此大街网帐号状态异常");
	}};
	

	public static String getErrorMsg(int errorCode){
		Object o=ErrorMsg.get(errorCode);
		if(o==null){
			return "未知的错误信息";
		}else{
			return String.valueOf(o);
		}
	}
	
	public static String getVerifyCodeErrorMsg(int errorCode){
		Object o=ErrorMsg.get(errorCode);
		if(o==null){
			return "验证码发送失败，请重新发送";
		}else{
			return String.valueOf(o);
		}
	}
	
	public static String getPasswordErrorMsg(int errorCode){
		Object o=ErrorMsg.get(errorCode);
		if(o==null){
			return "更改密码失败，请重试";
		}else{
			return String.valueOf(o);
		}
	}
	
	public static String getDajieLoginMsg(int errorCode){
		Object o=ErrorMsg.get(errorCode);
		if(o==null){
			return "此大街网帐号存在异常，请登录大街网站核实或注册新的微卡帐号";
		}else{
			return String.valueOf(o);
		}
	}
	

	
	public static String getLoginMsg(int errorCode){
		Object o=ErrorMsg.get(errorCode);
		if(o==null){
			return "登录错误";
		}else{
			return String.valueOf(o);
		}
	}
	
	public static String getRegisterMsg(int errorCode){
		Object o=ErrorMsg.get(errorCode);
		if(o==null){
			return "注册发生错误";
		}else{
			return String.valueOf(o);
		}
	}
	
	public static String getBindMsg(int errorCode){
		Object o=ErrorMsg.get(errorCode);
		if(o==null){
			return "绑定手机号错误";
		}else{
			return String.valueOf(o);
		}
	}
	
	
	
}
