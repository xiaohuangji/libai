package com.dajie.wika.wap.controllers;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dajie.wika.constants.SourceTypeConstant;
import com.dajie.wika.constants.UserActivationConstant;
import com.dajie.wika.constants.returncode.AccountReturnCodeConstant;
import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.UserCountBase;
import com.dajie.wika.model.UserOccupation;
import com.dajie.wika.model.wrapper.ActiveDJReturn;
import com.dajie.wika.model.wrapper.LoginReturn;
import com.dajie.wika.model.wrapper.RegisterReturn;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.OccupationService;
import com.dajie.wika.service.UserCountService;
import com.dajie.wika.wap.model.AjaxResult;
import com.dajie.wika.wap.model.ErrorMsg;
import com.dajie.wika.wap.model.PicUrls;
import com.dajie.wika.wap.util.CookieUtil;
import com.dajie.wika.wap.util.HttpRequestUtil;
import com.dajie.wika.wap.util.WikaJsonUtil;

@Controller
@RequestMapping("account")
public class AccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserCountService userCountService;
	
	@Autowired
	private OccupationService occupationService;
	
	@RequestMapping("jump")
	public ModelAndView jump(HttpServletRequest request,Integer userId){
		//根据uid取出用户信息，根据状态进行跳转
		UserBase userbase=accountService.getUserBaseById(userId);
		int activity=userbase.getActivation();
		if(activity==UserActivationConstant.UN_ACTIVE){//未激活，进入领取页
			if(HttpRequestUtil.isFromMicroMessenger(request)){//从微信进入
				return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/login?userId="+userId));
			}
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/no-login-receive?userId="+userId));
		}else{// 已激活，进入myvcard页
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/myvcard?userId="+userId));
		}
		
	}
	
	private void renderUserBase(ModelAndView mv,int userId){
		try{
			UserBase userbase=accountService.getUserBaseById(userId);
			UserOccupation occupation=occupationService.getOccupation(userId);
			mv.addObject("userbase",userbase);
			mv.addObject("occupation",occupation);
			mv.addObject("avatarPicUrls",new PicUrls(userbase.getAvatar()));
			mv.addObject("qrPicUrls",new PicUrls(userbase.getFaceQRCode()));
		}
		catch(Exception e){
			logger.warn("renderuserbase ERROR",e);
		}
	}
	
	/**
	 * 种子用户领卡，非微信里面
	 * @param userId
	 * @return
	 */
	@RequestMapping("no-login-receive")
	public ModelAndView noLoginReceive(HttpServletRequest request, Integer userId){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "no-login-receive"));
		renderUserBase(mv,userId);
		renderErrMsg(request,mv);
		return mv;
	}
	
	@RequestMapping("receive")
	public ModelAndView receive(HttpServletRequest request, Integer userId){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "receive"));
		UserBase userbase=accountService.getUserBaseById(userId);
		UserCountBase userCountBase=userCountService.getUserCountBase(userId);
		mv.addObject("userbase",userbase);
		mv.addObject("usercountbase",userCountBase);
		mv.addObject("userId",userId);
		return mv;
	}
	
	/**
	 * 种子用户领取wika，在微信里
	 * @param userId
	 * @return
	 */
	@RequestMapping("login")
	public ModelAndView login(HttpServletRequest request, Integer userId){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "login"));
		renderUserBase(mv,userId);
		renderErrMsg(request,mv);
		return mv;
	}
	
	
	/**
	 * 纯登录页
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("login-only")
	public ModelAndView loginOnly(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "login-only"));
		renderErrMsg(request,mv);
		return mv;
	}
	
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
	/**
	 * 大街登录页
	 * @return
	 */
	@RequestMapping("login-dajie")
	public ModelAndView loginDajie(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "login-dajie"));
		renderErrMsg(request,mv);
		return mv;
	}
	
	@RequestMapping("phone-number-pic")
	public ModelAndView phoneNumberPic(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "phone-number-pic"));
		return mv;
	}
	
	@RequestMapping("phone-number")
	public ModelAndView phoneNumber(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "phone-number"));
		renderErrMsg(request,mv);
		return mv;
	}
	
	
	@RequestMapping("login-service")
	public ModelAndView loginService(HttpServletRequest request, String tel,String password,HttpServletResponse response){
		LoginReturn loginResult=accountService.login(tel,password,SourceTypeConstant.WAP_SOURCE);//1表示来自wap的登录
		int returnCode=loginResult.getReturnCode();
		if(returnCode==0){
			//保存cookie
			int userId=loginResult.getUserId();
			CookieUtil.setUserId(response, userId);
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/myvcard?userId="+userId));
		}else if(returnCode==AccountReturnCodeConstant.BASE_INFO_UNFINISED){//信息不完整
			int userId=loginResult.getUserId();
			CookieUtil.setUserId(response, userId);
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/edit"));
		}else{//其他错误
			logger.info("login error:",returnCode);
			ModelMap mmap = new ModelMap();
			mmap.addAttribute("tel", tel);
			mmap.addAttribute("errmsg", ErrorMsg.getLoginMsg(returnCode));
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/login-only"),mmap);
			return mv;
		}
	}

	@RequestMapping("login-dajie-service")
	public ModelAndView loginDajieService(HttpServletRequest request,HttpServletResponse response,String email,String password) throws UnsupportedEncodingException{
		LoginReturn loginResult=accountService.loginWithDJ(email, password, SourceTypeConstant.WAP_SOURCE);
		//登录成功后，进入绑定页面
		int result=loginResult.getReturnCode();
		if(result==0){//成功，并且信息全,跳转至主页
			//写入cookie
			CookieUtil.setUserId(response, loginResult.getUserId());
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/myvcard?userId="+loginResult.getUserId()));
		}else if(result==1405){//登陆成功，未绑定手机号--第一次登录
			CookieUtil.setUserId(response, loginResult.getUserId());
			String mobile="";
			if(loginResult.getLoginUserView()!=null){
				mobile=loginResult.getLoginUserView().getMobile();
			}
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/bind-mobile-dajie?tel="+mobile));
			return mv;
		}else if(result==1404){//登陆成功,未完善信息
			CookieUtil.setUserId(response, loginResult.getUserId());
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/edit"));
			return mv;
		}
		else{
			logger.error("dajie login error:"+result);
			ModelMap mmap = new ModelMap();
			mmap.addAttribute("email", email);
			mmap.addAttribute("errmsg", ErrorMsg.getDajieLoginMsg(result));
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:"+HttpRequestUtil.getReferer(request)),mmap);
			return mv;
		}
	}
	
	@RequestMapping("active-dajie-service")
	public ModelAndView activeDJAccountService(HttpServletRequest request,String tel,String code,String password){
		int userId=CookieUtil.getUserId(request);
		ActiveDJReturn result=accountService.activeDJAccount(userId, tel, code, password,SourceTypeConstant.WAP_SOURCE);
		if(result.getReturnCode()==0){//绑定成功，信息全
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/myvcard?userId="+userId));
		}else if(result.getReturnCode()==AccountReturnCodeConstant.BASE_INFO_UNFINISED){//绑定成功，信息不全
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/edit"));
		}else{//其他问题
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:"+HttpRequestUtil.getReferer(request)));
			mv.addObject("errmsg",ErrorMsg.getBindMsg(result.getReturnCode()));
			return mv;
		}	
	}
	
	
	@RequestMapping("bind-mobile-service")
	public ModelAndView bindMobileService(HttpServletRequest request,String tel,String code,String password){
		int userId=CookieUtil.getUserId(request);
		int result=accountService.bindMobile(userId, tel, code, password);
		if(result==0){
			//String referer=HttpRequestUtil.getReferer(request);//跳回edit
			/*if(referer.indexOf("/user/edit")!=-1)//
			{
				return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/edit"));
			}*/
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/edit"));	
		}else{
			logger.info("bind mobile error",userId);
			//返回登陆页，并提示错误
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/rebind-mobile"));
			mv.addObject("tel",tel);
			mv.addObject("errmsg",ErrorMsg.getBindMsg(result));
			return mv;
		}
	}
	
	@RequestMapping("get-code-ajax")
	@ResponseBody
	public String getVerifyCode(String tel){
		int result=accountService.getVerifyCode(tel);
		AjaxResult ajaxResult=new AjaxResult(result);
		if(result!=0){
			ajaxResult.setMsg(ErrorMsg.getVerifyCodeErrorMsg(result));
		}
		return WikaJsonUtil.toJson(ajaxResult);
	}
	
	@RequestMapping("rebind-mobile")
	public ModelAndView rebindMobile(HttpServletRequest request,@RequestParam(required=false)String tel){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "rebind-mobile"));
		renderErrMsg(request,mv);
		if(tel!=null){
			mv.addObject("tel",tel);
		}
		return mv;
	}
	
	@RequestMapping("bind-mobile-dajie")
	public ModelAndView bindMobileSeed(HttpServletRequest request,@RequestParam(required=false)String tel){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "bind-mobile-dajie"));
		if(tel!=null){
			mv.addObject("tel",tel);
		}
		return mv;
	}
	
	@RequestMapping("register-service")
	public ModelAndView registerService(HttpServletRequest request, String tel,String code,String password,HttpServletResponse response){
		RegisterReturn result=accountService.register(tel, code, password,SourceTypeConstant.WAP_SOURCE);
		if(result.getReturnCode()==0){
			//注册成功，写cookie
			int userId=result.getUserId();
			CookieUtil.setUserId(response, userId);
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/edit"));
		}else {
			//失败，返回错误码
			logger.error("register error:",result.getReturnCode());
			ModelMap mmap = new ModelMap();
			mmap.addAttribute("tel", tel);
			mmap.addAttribute("errmsg", ErrorMsg.getRegisterMsg(result.getReturnCode()));
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/phone-number"),mmap);
			return mv;
		}
	}
	
	@RequestMapping("logout-service")
	public ModelAndView logoutService(HttpServletRequest request, HttpServletResponse response){
		//清除cookie
		CookieUtil.removeUserId(response);
		CookieUtil.removeTraceId(response);
		//返回登陆页面
		return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/login-only"));
	}

}
