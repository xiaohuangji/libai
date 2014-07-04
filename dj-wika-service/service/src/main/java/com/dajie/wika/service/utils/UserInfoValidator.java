package com.dajie.wika.service.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author li.hui 用户基本信息校验
 */
public class UserInfoValidator {
	public static void main(String[] args) {
		String in = "";
		System.out.println(departmentValidate(in));
	}

	// 邮箱
	private static final String EMAIL_PATTERN = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";

	// 用户名
	private static final String NAME_PATTERN = "^([\u4e00-\u9fa5]|[a-zA-Z\\s]){2,40}$";

	// 手机号
	private static final String MOBILE_PATTERN = "^[1][3-8][0-9]{9}$";

	// 密码(不含空格的可显示8位字符)
	private static final String PASSWORD_PATTERN = "^[\\x21-\\x7e]{4,20}$";

	// 微卡号
	private static final String WIKA_ID_PATTERN = "^[a-zA-z][a-zA-z0-9_.]{3,19}$";

	private static final int INTRODUCE_LEN = 30;

	public static boolean emailValidate(String email) {
		// 邮箱可以为空
		if (email == null || "".equals(email)) {
			return true;
		} else {
			if (email.length() > 50) {
				return false;
			}
			return validate(email, EMAIL_PATTERN);
		}

	}

	public static boolean nameValidate(String name) {
		return validate(name, NAME_PATTERN);
	}

	public static boolean mobileValidate(String mobile) {
		return validate(mobile, MOBILE_PATTERN);
	}

	public static boolean passwordValidate(String password) {
		return validate(password, PASSWORD_PATTERN);
	}

	public static boolean wikaIdValadate(String wikaId) {
		if (wikaId == null || "".equals(wikaId)) {
			return true;
		}
		return validate(wikaId, WIKA_ID_PATTERN);
	}

	public static boolean introduceValidate(String introduce) {
		if (introduce != null && introduce.length() > INTRODUCE_LEN) {
			return false;
		}
		return true;
	}

	public static boolean corpValidate(String corp) {
		if (null != corp) {
			int len = corp.length();
			if (len >= 2 && len <= 40) {
				return true;
			}
		}
		return false;
	}

	public static boolean positionValidate(String position) {
		if (null != position) {
			int len = position.length();
			if (len >= 2 && len <= 40) {
				return true;
			}
		}
		return false;
	}

	public static boolean departmentValidate(String department) {
		if (null != department && department.length() > 40) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean validate(String input, String regex) {
		if (input == null || "".equals(input)) {
			return false;
		}
		boolean tag = true;
		Pattern pattern = Pattern.compile(regex);
		Matcher mat = pattern.matcher(input);
		if (!mat.find()) {
			tag = false;
		}
		return tag;
	}

}
