package com.dajie.wika.service;

import com.dajie.wika.model.ShortUrlInfo;

public interface ShortUrlService {

	public String genShortUrl(String sourceUrl);
	
	public ShortUrlInfo resolveShortUrl(String shortUrl);
	
	public String resolveUrl(String key);
	
	public String genWikaProfileShortUrl(int userId);
	
}
