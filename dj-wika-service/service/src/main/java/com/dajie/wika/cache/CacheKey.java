/**
 * $Id$
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */

package com.dajie.wika.cache;

public class CacheKey {
	public static final String MOBILE_VERIFY_CODE = "mobileCode@WIKA_";

	public static final String MOBILE_VERIFY_CODE_SEND_COUNT = "mobileCodeCount@WIKA_";

/*	public static final String USER_BASE_INFO = "uInfo@DAJIE_";

	public static final String RELATION_FOLLOWER = "relation_follower@DAJIE_";

	public static final String RELATION_FOLLOWING = "relation_following@DAJIE_";

	public static final String RELATION_FRIEND = "relation_friend@DAJIE_";*/

	/**
	 * 用户基本计数，好友数等
	 */
	public static final String USER_COUNT_BASE = "uCountBaseCount@WIKA_";

	/**
	 * 用户统计计数相关等
	 */
	public static final String USER_COUNT_YESTERDAY = "uCountYesterday@WIKA_";

	/**
	 * idseq
	 */
	public static final String IDSEQUENCE = "idseq@WIKA_";

	/**
	 * 未读消息数
	 */
	public static final String USER_UNREAD__MSG_COUNT = "uUnreadMsgCount@WIKA_";
	
	/**
	 * 通过redis弄一个简易的分布式锁
	 */
	public static final String USER_VALUE_CRONTAB_LOCK="userValuecrontab@WIKA_";
	
	
	public static String getUserValueCrontabKey(){
		return USER_VALUE_CRONTAB_LOCK;
	}

	// 获取手机码key
	public static String getPhoneCodeKey(String phone) {
		return MOBILE_VERIFY_CODE + phone;
	}

	// 手机验证码发送的次数
	public static String getPhoneCodeCountKey(String phone) {
		return MOBILE_VERIFY_CODE_SEND_COUNT + phone;
	}

	public static String getUserCountBaseKey(int userId) {
		return USER_COUNT_BASE + userId;
	}

	public static String getUserCountYesterdaysKey(int userId) {
		return USER_COUNT_YESTERDAY + userId;
	}

	public static String getIdseqkey(int type) {
		return IDSEQUENCE + type;
	}

	public static String getUnreadMsgCountKey(int userId) {
		return USER_UNREAD__MSG_COUNT + userId;
	}

}
