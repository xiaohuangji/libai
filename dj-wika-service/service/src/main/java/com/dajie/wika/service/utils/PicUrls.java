package com.dajie.wika.service.utils;

public class PicUrls {

	private String c="";
	
	private String l="";
	
	private String b="";
	
	private String m="";
	
	public PicUrls(String url){
		if(url==null)
			return ;
		int index=url.lastIndexOf(".");
		String prefix=url.substring(0, index);
		String suffix=url.substring(index,url.length());
		this.c=prefix+"c"+suffix;
		this.b=prefix+"b"+suffix;;
		this.l=prefix+"l"+suffix;
		this.m=prefix+"m"+suffix;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getB() {
		return b;
	}

	public void setB(String b) {
		this.b = b;
	}

	public String getM() {
		return m;
	}

	public void setM(String m) {
		this.m = m;
	}
	
	
}
