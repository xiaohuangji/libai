package com.dajie.wika.wap.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dajie.wika.wap.util.HttpRequestUtil;

@Controller
@RequestMapping("system")
public class SystemController {
	
	@RequestMapping("licence")
	public ModelAndView licence(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "licence"));
		return mv;
	}

	@RequestMapping("download")
	public ModelAndView download(HttpServletRequest request, @RequestParam(required=false)Integer sId){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "download"));
		//根据来源选择文案
		mv.addObject("downloadinfo",getDownloadInfo(sId));
		return mv;
	}
	
	@RequestMapping("getapp")
	public String download(HttpServletRequest request){
		if(HttpRequestUtil.isIphoneUA(request)){
			return "redirect:https://itunes.apple.com/us/app/wei-ka-neng-sao-tou-xiang/id805428016?ls=1&mt=8";
		}
		return "redirect:http://bcs.duapp.com/weikaapp/vcard_v1.0_dl.apk";
	}
	
	@RequestMapping("support")
	public String support(HttpServletRequest request){
		return "redirect:http://hi.baidu.com/iweika";
	}
	
	
	private String getDownloadInfo(Integer sId){
		if(sId==null||map.get(sId)==null)
			return String.valueOf(map.get(5));
		return String.valueOf(map.get(sId));
	}
	
	public static final Map map=new HashMap<Integer, String>(){
		{
			put(1, "使用微卡app，立刻保存联系方式到手机。");
			put(2, "使用微卡app，立即查看详细身价。");
			put(3, "使用微卡app，查看更多联系人。");
			put(4, "使用微卡app，查看更多新的朋友。");
			put(5, "立即下载微卡app。");
			put(6, "使用微卡app，立即更换头像。");
			put(7, "使用微卡app，马上解锁脸码。");
			put(8, "使用微卡app，马上解锁微卡模板。");
		}};
		
	     //所有的保存到通讯录按钮，点击后跳转。文案：使用微卡app，立刻保存联系方式到手机
	  //   点击查看身价，点击后跳转。文案：使用微卡app，立即查看详细身价。
	    // 联系人列表加载了40条下方显示的查看更多按钮，点击后跳转。文案：使用微卡app，查看更多联系人。
	   //  新的朋友列表加载了40条下方显示的查看更多按钮，点击后跳转。文案：使用微卡app，查看更多新的朋友。
	    // 页面下方说明文案里的下载链接，点击后跳转。文案：立即下载微卡app
	   //  头像大图页面上的下载按钮，点击后跳转。文案：使用微卡app，立即更换头像。
	   //  更换脸码页面上的下载应用解锁按钮，点击后跳转。文案：使用微卡app，马上解锁脸码。
	     //更换更换模板页面上的下载应用解锁按钮，点击后跳转。文案：使用微卡app，马上解锁微卡模板。
}
