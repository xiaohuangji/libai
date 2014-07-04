package com.dajie.wika.wap.model;

import java.util.List;


public class Industry {

	private String name;
	
	private List<OptionGroup> data;
		
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OptionGroup> getData() {
		return data;
	}

	public void setData(List<OptionGroup> data) {
		this.data = data;
	}
	
}

