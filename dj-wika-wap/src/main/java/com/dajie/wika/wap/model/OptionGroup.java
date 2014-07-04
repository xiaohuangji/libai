package com.dajie.wika.wap.model;

import java.util.List;


public class OptionGroup{
	
	private int id;
	
	private String name;
	
	private List<Option> sub;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Option> getSub() {
		return sub;
	}

	public void setSub(List<Option> sub) {
		this.sub = sub;
	}
	
	
	
}