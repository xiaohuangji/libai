package com.dajie.wika.model.wrapper;

import java.io.Serializable;
import java.util.List;

import com.dajie.wika.model.UserCountBase;
import com.dajie.wika.model.UserCountYesterday;
import com.dajie.wika.model.UserValue;
import com.dajie.wika.model.UserValueDetail;

public class UserCountWrapper implements Serializable{
	
	private int userId;

	private UserCountBase userCountBase;
	
	private UserCountYesterday userCountYesterday;
	
	private UserValueDetail userValueDetail;
	
	private List<UserValue> userValues;
	
	private float rank;
	
	private int userValue;
	
	public UserCountWrapper(int userId){
		this.userId=userId;
	}

	public UserCountBase getUserCountBase() {
		return userCountBase;
	}

	public void setUserCountBase(UserCountBase userCountBase) {
		this.userCountBase = userCountBase;
	}


	public UserCountYesterday getUserCountYesterday() {
		return userCountYesterday;
	}

	public void setUserCountYesterday(UserCountYesterday userCountYesterday) {
		this.userCountYesterday = userCountYesterday;
	}

	public UserValueDetail getUserValueDetail() {
		return userValueDetail;
	}

	public void setUserValueDetail(UserValueDetail userValueDetail) {
		this.userValueDetail = userValueDetail;
	}

	public List<UserValue> getUserValues() {
		return userValues;
	}

	public void setUserValues(List<UserValue> userValues) {
		this.userValues = userValues;
	}

	public float getRank() {
		return rank;
	}

	public void setRank(float rank) {
		this.rank = rank;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getUserValue() {
		return userValue;
	}

	public void setUserValue(int userValue) {
		this.userValue = userValue;
	}
}
