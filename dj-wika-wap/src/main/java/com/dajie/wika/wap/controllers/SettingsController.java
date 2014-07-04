package com.dajie.wika.wap.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dajie.wika.constants.returncode.AccountReturnCodeConstant;
import com.dajie.wika.model.UserPrivacySettings;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.UserSettingsService;
import com.dajie.wika.wap.model.ErrorMsg;
import com.dajie.wika.wap.util.CookieUtil;
import com.dajie.wika.wap.util.HttpRequestUtil;

@Controller
@RequestMapping("setting")
public class SettingsController {

	private static final Logger logger = LoggerFactory.getLogger(SettingsController.class);
	
	@Autowired
	private UserSettingsService userSettingsService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("setting")
	public ModelAndView setting(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "setting"));
		return mv;
	}
	
	@RequestMapping("privacy-settings")
	public ModelAndView privacySettings(HttpServletRequest request){
		int userId=CookieUtil.getUserId(request);
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "privacy-settings"));
		UserPrivacySettings userPrivacySettings=userSettingsService.getUserPrivacySettings(userId);
		mv.addObject("privacysettings", userPrivacySettings);
		return mv;
	}
	
	@RequestMapping("privacy-settings-service")
	public ModelAndView privacySettingsService(HttpServletRequest request,Integer tel,Integer mail){
		int userId=CookieUtil.getUserId(request);
		UserPrivacySettings userPrivacySettings=new UserPrivacySettings();
		userPrivacySettings.setUserId(userId);
		userPrivacySettings.setMobilePrivacy(tel==null?0:tel);
		userPrivacySettings.setEmailPrivacy(mail==null?0:mail);
		userSettingsService.setPrivacySettings(userPrivacySettings);
		return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/setting/setting"));
	}
	
	
	@RequestMapping("setting-password")
	public ModelAndView settingPassword(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "setting-password"));
		renderErrMsg(request, mv);
		mv.addObject("tel", accountService.getUserBaseById(CookieUtil.getUserId(request)).getMobile());
		return mv;
	}
	
	@RequestMapping("setting-phone")
	public ModelAndView settingPhone(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "setting-phone"));
		return mv;
	}
	
	@RequestMapping("setting-find-password")
	public ModelAndView settingFindPassword(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "setting-find-password"));
		renderErrMsg(request,mv);
		return mv;
	}
	
	@RequestMapping("setting-reset-password")
	public ModelAndView settingResetPassword(HttpServletRequest request, String tel,String code){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "setting-reset-password"));
		mv.addObject("tel",tel);
		mv.addObject("code",code);
		return mv;
	}
	
	@RequestMapping("setting-reset-password-service")
	public ModelAndView settingResetPasswordService(HttpServletRequest request, String tel,String code,String newpassword,HttpServletResponse response){
		int result=accountService.changePasswordWithVerifyCode(tel, code, newpassword);
		CookieUtil.removeUserId(response);
		if(result==0){
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/login-only"));
		}else{
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/setting/setting-find-password"),"errmsg",ErrorMsg.getErrorMsg(result));
		}
	}
	
	//从其他controller拷了一份过来，恶心了
	private void renderErrMsg(HttpServletRequest request,ModelAndView mv){
		String errmsg=request.getParameter("errmsg");
		if(errmsg!=null){
			mv.addObject("errmsg",errmsg);
		}
		String tel=request.getParameter("tel");
		if(tel!=null){
			mv.addObject("tel",tel);
		}	
		String email=request.getParameter("email");
		if(email!=null){
			mv.addObject("email",email);
		}
	}
	
	@RequestMapping("validateVerifyCode-service")
	public ModelAndView settingFindPasswordService(HttpServletRequest request, String tel,String code){
		//先检查手机号是否存在，不存在直接返回账号不存在。
		if(!accountService.isMobileUsed(tel)){
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/setting/setting-find-password"),"errmsg",ErrorMsg.getErrorMsg(AccountReturnCodeConstant.ACCOUNT_NOT_EXIST));
			return mv;
		}
		boolean result=accountService.validateVerifyCode(tel, code);
		if(result){
			ModelMap mmap = new ModelMap();
			mmap.addAttribute("tel", tel);
			mmap.addAttribute("code",code);
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/setting/setting-reset-password"),mmap);
			return mv;
		}else{
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/setting/setting-find-password"),"errmsg",ErrorMsg.getErrorMsg(AccountReturnCodeConstant.VERIFY_CODE_ERROR));
			return mv;
		}
		
	}
		
	@RequestMapping("changepassword-service")
	public ModelAndView changePasswordService(HttpServletRequest request,String password,String newpassword,String mobile){
		int userId=CookieUtil.getUserId(request);
		int result=accountService.changePassword(userId, password, newpassword);
		if(result==0){
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/setting/setting"));
		}else {
			logger.error("change password error:"+result);
			ModelMap mmap = new ModelMap();
			mmap.addAttribute("tel", mobile);
			mmap.addAttribute("errmsg",ErrorMsg.getPasswordErrorMsg(result));
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/setting/setting-password"),mmap);
			return mv;
		}
	}
	
}
