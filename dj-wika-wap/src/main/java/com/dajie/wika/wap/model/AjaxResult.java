package com.dajie.wika.wap.model;

public class AjaxResult {

	private int status;
	
	private String msg;
	
	public AjaxResult(int status){
		this.status=status;
	}
	
	public AjaxResult(int status,String msg){
		this.status=status;
		this.msg=msg;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
