package com.dajie.wika.model;

import java.io.Serializable;

public class UserCountYesterday implements Serializable{

	private int yesterdayFollowerCount;
	
	private int yesterdayFollowingCount;

	public int getYesterdayFollowerCount() {
		return yesterdayFollowerCount;
	}

	public void setYesterdayFollowerCount(int yesterdayFollowerCount) {
		this.yesterdayFollowerCount = yesterdayFollowerCount;
	}

	public int getYesterdayFollowingCount() {
		return yesterdayFollowingCount;
	}

	public void setYesterdayFollowingCount(int yesterdayFollowingCount) {
		this.yesterdayFollowingCount = yesterdayFollowingCount;
	}
	
	
}
