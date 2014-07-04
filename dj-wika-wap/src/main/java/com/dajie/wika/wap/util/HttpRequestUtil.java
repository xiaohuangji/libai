package com.dajie.wika.wap.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpRequestUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(HttpRequestUtil.class);

	/**
	 * 判断请求是否是从微信客户端进入
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isFromMicroMessenger(HttpServletRequest request) {
		String ua = request.getHeader("user-agent");
		return (ua != null && ua.indexOf("MicroMessenger") != -1);
	}
	
	
	/**
	 * 判断请求是否是从微信客户端进入
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isIphoneUA(HttpServletRequest request) {
		String ua = request.getHeader("user-agent");
		return (ua != null && ua.indexOf("iPhone") != -1);
	}

	/**
	 * 获取Referer
	 * 
	 * @param request
	 * @return
	 */
	public static String getReferer(HttpServletRequest request) {
		String referer = request.getHeader("referer");
		String host = request.getHeader("host");
		int i = referer.indexOf(host);
		String lastPage = referer.substring(i + host.length());
		return lastPage;
	}

	/**
	 * 获取requestview
	 * 
	 * @param request
	 * @return
	 */
	public static String getRefererView(HttpServletRequest request) {
		String referer = request.getHeader("referer");
		String host = request.getHeader("host");
		int i = referer.indexOf(host);
		String lastPage = referer.substring(i + host.length());
		String[] pathes = lastPage.split("/");
		return pathes[pathes.length - 1];
	}

	/**
	 * 根据请求里是否有统计参数，在url里加上对应的参数小尾巴
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	public static String addStat(HttpServletRequest request, String url,
			String stat) {
		if (StringUtils.isBlank(url))
			return url;

		if (url.indexOf('?') != -1
				&& url.indexOf(StringUtils.defaultString(stat) + "=") != -1)
			return url;

		@SuppressWarnings("unchecked")
		Enumeration<String> e = request.getParameterNames();
		while (e.hasMoreElements()) {
			String param = e.nextElement();
			String value = request.getParameter(param);

			if (StringUtils.defaultString(stat).equals(param)
					&& StringUtils.isNotBlank(value)) {
				if (logger.isDebugEnabled()) {
					logger.debug(String
							.format("[HttpRequestUtil]:{getRequestParamMap(HttpServletRequest):[param=%s]=>[value=%s]}",
									param, value));
				}
				if (url.indexOf('?') != -1)
					return url + "&" + StringUtils.defaultString(stat) + "="
							+ value;
				else
					return url + "?" + StringUtils.defaultString(stat) + "="
							+ value;
			}
		}
		return url;
	}

	/**
	 * 根据请求里是否有userId参数，在url里加上对应的wuid参数小尾巴
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	public static String addUid(HttpServletRequest request, String url) {
		if (StringUtils.isBlank(url))
			return url;

		if (url.indexOf('?') != -1 && url.indexOf("wuid=") != -1)
			return url;

		@SuppressWarnings("unchecked")
		Enumeration<String> e = request.getParameterNames();

		// 优先搜索wuid参数，并在url里添加
		while (e.hasMoreElements()) {
			String param = e.nextElement();
			String value = request.getParameter(param);

			if ("wuid".equals(param) && StringUtils.isNotBlank(value)) {
				if (logger.isDebugEnabled()) {
					logger.debug(String
							.format("[HttpRequestUtil]:{getRequestParamMap(HttpServletRequest):[param=%s]=>[value=%s]}",
									param, value));
				}
				if (url.indexOf('?') != -1)
					return url + "&wuid=" + value;
				else
					return url + "?wuid=" + value;
			}
		}

		// 一开始并没有wuid参数，最初的来源是userId
		while (e.hasMoreElements()) {
			String param = e.nextElement();
			String value = request.getParameter(param);

			if ("userId".equals(param) && StringUtils.isNotBlank(value)) {
				if (logger.isDebugEnabled()) {
					logger.debug(String
							.format("[HttpRequestUtil]:{getRequestParamMap(HttpServletRequest):[param=%s]=>[value=%s]}",
									param, value));
				}
				if (url.indexOf('?') != -1)
					return url + "&wuid=" + value;
				else
					return url + "?wuid=" + value;
			}
		}
		return url;
	}

	/**
	 * 把统计的信息加在url里
	 * 
	 * @param request
	 * @param url
	 * @return
	 */
	public static String addStatInfo(HttpServletRequest request, String url) {
		if (url.contains("redirect")) {
			String url1 = addStat(request, url, "idt");
			String url2 = addStat(request, url1, "cn");
			String url3 = addStat(request, url2, "cp");
			String url4 = addStat(request, url3, "etc");
			String url5 = addUid(request, url4);
			return url5;
		}
		return url;
	}
}
