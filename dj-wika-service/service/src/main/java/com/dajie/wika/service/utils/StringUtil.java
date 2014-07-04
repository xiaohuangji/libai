package com.dajie.wika.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

public class StringUtil {

	private static final Pattern PHONE_NUMBER_PATTERN = Pattern
			.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(14[5,7]))\\d{8}$");

	public static String trim(String input) {
		if (input == null || input.equals("")) {
			return input;
		}
		String[] inputStrings = input.split(" ");
		List<String> inputs = new ArrayList<String>();
		for (String s : inputStrings) {
			if (!s.equals(" ") && !s.equals("")) {
				inputs.add(s);
			}
		}
		StringBuffer sb = new StringBuffer();
		if (inputs != null && inputs.size() > 0) {
			int len = inputs.size();
			for (int i = 0; i < len; i++) {
				sb.append(inputs.get(i));
				if (i < len - 1) {
					sb.append(" ");
				}
			}
		}
		return sb.toString();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public static boolean isNotEmpty(String str) {
		return !StringUtils.isEmpty(str);
	}

	public static boolean isEmail(String email) {
		if (isEmpty(email)) {
			return false;
		}
		Pattern p = Pattern
				.compile("^[_a-z0-9\\.\\-]+@([\\._a-z0-9\\-]+\\.)+[a-z0-9]{2,4}$");
		Matcher m = p.matcher(email.toLowerCase());
		return m.matches();
	}

	/**
	 * 判断是否符合手机号的条件
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhoneNumber(String phone) {
		if (isEmpty(phone)) {
			return false;
		}
		Matcher m = PHONE_NUMBER_PATTERN.matcher(phone);
		return m.matches();
	}

}
