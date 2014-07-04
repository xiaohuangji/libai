package com.dajie.wika.model;


public class ImageSize {

	private int high;
	
	private int width;

	public ImageSize(int size){
		this.high=size;
		this.width=size;
	}
	
	public ImageSize(int high,int width){
		this.high=high;
		this.width=width;
	}
	public int getHigh() {
		return high;
	}

	public void setHigh(int high) {
		this.high = high;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
