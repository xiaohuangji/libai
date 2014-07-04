package com.dajie.wika.service.utils;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dajie.wika.constants.MessageType;
import com.dajie.wika.model.Message;
import com.dajie.wika.service.QRcodeService;
import com.dajie.wika.service.ShortUrlService;
import com.dajie.wika.service.impl.MessageServiceImpl;

public class AppMain {
	
	
	//ConfigurationManager.getInstance();
	static String[] springPath = new String[] {
			"classpath*:*.xml",
			"classpath*:app_config/*.xml", "classpath*:dubbo_config/*.xml",
			"classpath*:spring/applicationContext-*.xml" };
	static final ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			springPath);
	
	public static void main(String[] args) {		
		
		testQrCode();
		
	}
	
	public static void testQrCode(){
		QRcodeService service1 = (QRcodeService) applicationContext
				.getBean("qRcodeService");
		System.out.println("==============="+service1.getAllQRcode(29));
	}
	
	public static void tinyTest(){
		ShortUrlService service = (ShortUrlService) applicationContext
				.getBean("shortUrlService");
		System.out.println("==============="+service.resolveUrl("4rtit2"));
	}
	
	public static void testMessage(){
		MessageServiceImpl service1 = (MessageServiceImpl) applicationContext
				.getBean("messageService");
		System.out.println("==============="+service1.getMessage(108, 0, 10).size());
	}
}
