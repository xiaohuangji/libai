package com.dajie.wika.wap.interceptors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.dajie.wika.wap.util.CookieUtil;

public class CommonInterceptor extends HandlerInterceptorAdapter {

	private static String loginUrl="/account/phone-number";
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
			Cookie[] cookies=request.getCookies();
			if(cookies==null){
				response.sendRedirect(loginUrl);
				return false;
			}
			Integer userId=CookieUtil.getUserId(request);
			
			if(userId==null){
				response.sendRedirect(loginUrl);
				return false;
			}
			return true;
		}
}
