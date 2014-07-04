package com.dajie.wika.wap.model;

import java.util.List;

public class JobType {

	private String name;
	
	private List<Option> data;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Option> getData() {
		return data;
	}

	public void setData(List<Option> data) {
		this.data = data;
	}
		
}
