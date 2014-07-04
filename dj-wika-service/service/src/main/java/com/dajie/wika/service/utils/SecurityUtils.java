package com.dajie.wika.service.utils;

import org.apache.log4j.Logger;

import com.dajie.infra.util.CoderUtil;

public class SecurityUtils {

	private static final Logger logger = Logger.getLogger(SecurityUtils.class);
	public static final String CHARACTER_MOUSE = "@";

	public static String encodePhone(String phone) {
		if (StringUtil.isNotEmpty(phone)) {
			return CoderUtil.encode(phone);
		}
		return null;
	}

	public static String decodePhone(String phone) {
		if (StringUtil.isNotEmpty(phone)) {
			return CoderUtil.decode(phone);
		}
		return null;
	}
	
	// 返回值 email = 加密后前缀 + @ +后缀
	// 参数 preEmail(加密后前缀) ，sufEmail（后缀）
	public static String encryptEmail(String email) {
		if (StringUtil.isNotEmpty(email) && email.contains(CHARACTER_MOUSE)) {
			String[] subStr = email.split(CHARACTER_MOUSE);
			if (subStr.length == 2) {
				String encode = encryptPre(subStr[0]);
				if (StringUtil.isNotEmpty(encode)) {
					return encode + CHARACTER_MOUSE + subStr[1];
				} else {
					logger.error("encryptEmail email failed,email="+email);
				}
			} else {
				logger.error("encryptEmail email failed,email="+email);
			}
		}
		logger.error("encryptEmail email failed,email="+email);
		return null;
	}
	
	/**
	 * 加密
	 * 
	 * @param preEmail
	 * @return 返回email_encry
	 */
	private static String encryptPre(String preEmail) {
		if (StringUtil.isNotEmpty(preEmail)) {
			return CoderUtil.encode(preEmail);
		}
		return null;
	}


	// 参数 email = preEmail(加密后前缀) + sufEmail（后缀）
	// 返回值 = 解密后前缀 + @ + 后缀
	public static String decryptEmail(String email) {
		if (StringUtil.isNotEmpty(email) && email.contains(CHARACTER_MOUSE)) {
			String[] subStr = email.split(CHARACTER_MOUSE);
			if (subStr.length == 2) {
				return decryptEmail(subStr[0], subStr[1]);
			}
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param preEmail
	 * @param sufEmail
	 * @return 解密后前缀 + @ + 后缀
	 */
	private static String decryptEmail(String preEmail, String sufEmail) {
		if (StringUtil.isNotEmpty(preEmail) && StringUtil.isNotEmpty(sufEmail)) {
			String decode = CoderUtil.decode(preEmail);
			if (decode == null) {
				decode = CoderUtil.decode("A" + preEmail);
			}
			if (StringUtil.isNotEmpty(decode)) {
				return decode.toLowerCase() + CHARACTER_MOUSE
						+ sufEmail.toLowerCase();
			}
		} else {
		}
		return null;
	}
	
	public static String handlePassword(String password,String salt){
		 return MD5Util.MD5Encrypt(MD5Util.MD5Encrypt(password) + salt);
	}
	
}
