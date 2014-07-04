package com.dajie.wika.model.wrapper;

import java.io.Serializable;
import java.util.List;

import com.dajie.wika.model.UserValue;

/**
 * 用户身价精简版包装类
 * @author xing.feng
 *
 */
public class UserCountLiteWrapper implements Serializable {
	
	private int userValue;
	
	private List<UserValue> userValues;

	public int getUserValue() {
		return userValue;
	}

	public void setUserValue(int userValue) {
		this.userValue = userValue;
	}

	public List<UserValue> getUserValues() {
		return userValues;
	}

	public void setUserValues(List<UserValue> userValues) {
		this.userValues = userValues;
	}

}
