package com.dajie.wika.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.infra.tinyurl.service.TinyUrlService;
import com.dajie.wika.model.ShortUrlInfo;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.ShortUrlService;


@Service("shortUrlService")
public class ShortUrlServiceImpl implements ShortUrlService{

	private static final Logger logger=LoggerFactory.getLogger(ShortUrlServiceImpl.class);
	
	private final static String SHOTRURL="http://dajie.me/";
		
	private final static String URL_PREFIX="http://www.weika001.com/account/jump?userId=";
	
	@Autowired
	private TinyUrlService tinyUrlService;
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public ShortUrlInfo resolveShortUrl(String tinyUrl) {
		// TODO Auto-generated method stub
		ShortUrlInfo info=new ShortUrlInfo();
		try{
			String tinyUrlKey=tinyUrl.substring(SHOTRURL.length());
			String sourceUrl=tinyUrlService.getUrlByKey(tinyUrlKey);
			String paramStr=sourceUrl.substring(sourceUrl.indexOf("?")+1);
			String[] params=paramStr.split("&");
			for(String paramstr:params){
				String[] kv= paramstr.split("=");
				if(kv[0].equals("userId")){
					info.setUserId(Integer.valueOf(kv[1]));
				}
				if(kv[0].equals("cn")){
					info.setCn(Integer.valueOf(kv[1]));
				}
				if(kv[0].equals("cp")){
					info.setCp(Integer.valueOf(kv[1]));
				}
				if(kv[0].equals("etc")){
					info.setEtc(Integer.valueOf(kv[1]));
				}
				if(kv[0].equals("idt")){
					info.setIdt(Integer.valueOf(kv[1]));
				}
				if(kv[0].equals("sts")){
					info.setSts(Integer.valueOf(kv[1]));
				}
			}
			info.setUserBase(accountService.getUserBaseById(info.getUserId()));
		}catch(Exception e){
			logger.error("resovle url error",e);
		}
		return info;
	}

	@Override
	public String genShortUrl(String sourceUrl) {
		// TODO Auto-generated method stub
		return SHOTRURL+tinyUrlService.shortenUrl(sourceUrl);
	}

	@Override
	public String resolveUrl(String key) {
		// TODO Auto-generated method stub
		return tinyUrlService.getUrlByKey(key);
	}

	@Override
	public String genWikaProfileShortUrl(int userId) {
		// TODO Auto-generated method stub
		return genShortUrl(URL_PREFIX+userId);
	}

}
