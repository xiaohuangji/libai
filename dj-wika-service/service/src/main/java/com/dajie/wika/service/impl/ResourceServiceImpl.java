package com.dajie.wika.service.impl;

import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.dajie.common.dubbo.tolerance.Idempotent;
import com.dajie.common.file.enums.FileSavedType;
import com.dajie.common.file.model.UploadReturnModel;
import com.dajie.common.file.service.FileService;
import com.dajie.common.file.service.FileUploadService;
import com.dajie.wika.service.ResourceService;

@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

	private Logger logger = LoggerFactory.getLogger(ResourceServiceImpl.class);

	private static final int FILE_TYPE_AVATAR = 1;

	private static final int FILE_TYPE_WIKA_AVATAR = 2;

	private static final int FILE_UPLOAD_SUCC_CODE = 2;

	@Override
	@Idempotent(value = false)
	public String uploadFile(byte[] data, String filename, int type) {
		// TODO Auto-generated method stub
		UploadReturnModel result;
		if (type == FILE_TYPE_AVATAR) {
			result = FileUploadService.uploadFromBytes(data, filename,
					FileSavedType.wika_avatar);
		} else if (type == FILE_TYPE_WIKA_AVATAR) {
			result = FileUploadService.uploadFromBytes(data, filename,
					FileSavedType.wika_avatar_qr);
		} else {
			return null;
		}
		if (result.getStatus() == FILE_UPLOAD_SUCC_CODE) {
			return result.getLocalUrl();
		} else {
			return null;
		}

	}

	@Override
	@Idempotent(value = false)
	public String uploadHttpFile(String httpFileSurfix) {
		// TODO Auto-generated method stub
		if (httpFileSurfix.indexOf(".jpg") != -1
				|| httpFileSurfix.indexOf(".jpeg") != -1
				|| httpFileSurfix.indexOf(".png") != -1) {
			try {
				String url = FileService.url(httpFileSurfix, FileService.LARGE);
				logger.info("upload http file url:" + url);
				InputStream inputStream = new URL(url).openStream();
				byte[] data = IOUtils.toByteArray(inputStream);
				if (data == null) {
					logger.info("get data from url : " + url + " is null");
					return null;
				}
				return uploadFile(data, httpFileSurfix, FILE_TYPE_AVATAR);
			} catch (Exception e) {
				logger.error("upload file error",e);
				return null;
			}
		} else {
			return null;
		}
	}

	public static void main(String[] args) {
		ResourceServiceImpl service=new ResourceServiceImpl();
		String url="n/avatar/T1DuZTByxT1RXrhCrK_.jpg";
		System.out.println(service.uploadHttpFile(url));
	}
}
