package com.dajie.wika.wap.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xing.feng
 * 简单地加密。
 *
 */
public class CookieUtil {

	private static final Logger logger=LoggerFactory.getLogger(CookieUtil.class);
	
	private static final String UserId="token";
	
	private static final String TraceUserId="traceUserId";
	
	private static final  int cookieMaxAge=86400*7;
	
	private static Integer getIdFromCookie(HttpServletRequest request,String type){
		try{
			Cookie[] cookies=request.getCookies();
			if(cookies==null)
				return null;
			for(Cookie cookie:cookies){
				if(cookie.getName().equals(type)){
					String token=cookie.getValue();
					//解密
					return Integer.valueOf(EncrUtil.decrypt(token));
				}
			}
		}catch(Exception e){
			logger.error("getIdFromCookie ERROR",e);
		}
		return null;
		

	}
	
	public static void setIdToCookie(HttpServletResponse response,String type,int valueId){
		try{
			//加密
			String token=EncrUtil.encrypt(String.valueOf(valueId));
			Cookie cookie = new Cookie(type,token);
			cookie.setPath("/");
			cookie.setMaxAge(cookieMaxAge);
			response.addCookie(cookie);
		}
		catch(Exception e){
			logger.error("setIdToCookie ERROR",e);
		}
	}
	
	public static void removeIdFromCookie(HttpServletResponse response,String type){
		Cookie cookie = new Cookie(type,null);
		cookie.setPath("/");
		cookie.setMaxAge(0); 
		response.addCookie(cookie); 
	}
	
	/**
	 * 登陆相关用户id
	 * 
	 * @param request
	 * @return
	 */
	public static Integer getUserId(HttpServletRequest request){
		return getIdFromCookie(request,UserId);
	}
	

	public static void removeUserId(HttpServletResponse response){
		removeIdFromCookie(response,UserId);
	}
	
	public static void setUserId(HttpServletResponse response,int userId){
		setIdToCookie(response, UserId, userId);
	}
	
	/**
	 * 获取traceId
	 * @param response
	 */
	public static void removeTraceId(HttpServletResponse response){
		removeIdFromCookie(response,TraceUserId);
	}
	
	public static void setTraceId(HttpServletResponse response,int userId){
		setIdToCookie(response, TraceUserId, userId);
	}
	
	public static Integer getTraceId(HttpServletRequest request){
		return getIdFromCookie(request,TraceUserId);
	}
	

	
}
