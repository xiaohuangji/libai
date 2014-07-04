package com.dajie.wika.service;


public interface ResourceService {
	
	/**
	 * 上传资源
	 * @param data
	 * @param suffix
	 * @type 1 表示头像 2 表示二维码头像
	 * @return
	 */
	public String uploadFile(byte data[],String filename,int type);
	
	/**
	 * 将大街的头像资源存储到一下
	 * @param httpFile
	 * @return
	 */
	public String uploadHttpFile(String httpFileSurfix);
}
