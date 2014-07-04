package com.dajie.wika.wap.controllers;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dajie.wika.constants.RelationType;
import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.UserOccupation;
import com.dajie.wika.model.UserQRSet;
import com.dajie.wika.model.UserQRTemplate;
import com.dajie.wika.model.UserWikaTemplate;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.CorpSearchService;
import com.dajie.wika.service.OccupationService;
import com.dajie.wika.service.QRcodeService;
import com.dajie.wika.service.RelationService;
import com.dajie.wika.service.TemplateService;
import com.dajie.wika.service.UserCountService;
import com.dajie.wika.service.stub.ProfileServiceStub;
import com.dajie.wika.wap.model.CorpAjaxData;
import com.dajie.wika.wap.model.CorpOption;
import com.dajie.wika.wap.model.PicUrls;
import com.dajie.wika.wap.model.QRcodeWrapper;
import com.dajie.wika.wap.util.CookieUtil;
import com.dajie.wika.wap.util.HttpRequestUtil;
import com.dajie.wika.wap.util.StringUtil;
import com.dajie.wika.wap.util.WikaDataUtil;
import com.dajie.wika.wap.util.WikaJsonUtil;


@Controller
@RequestMapping("user")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private AccountService accountService;
	
	@Autowired
	private UserCountService userCountService;
	
	@Autowired
	private TemplateService templateService;

	@Autowired
	private OccupationService occupationService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private ProfileServiceStub profileServiceStub;
	
	@Autowired
	private CorpSearchService corpSearchService;
	
	@Autowired
	private QRcodeService qRcodeService;
	
	@RequestMapping("myvcard")
	public ModelAndView myvcard(HttpServletRequest request,HttpServletResponse response,@RequestParam(value="userId",required=false)Integer userId,@RequestParam(value="w",required=false)String w){
		//System.out.println(CookieUtil.getUserId(request));
		Integer curUserId=CookieUtil.getUserId(request);
		if(curUserId!=null){//登陆
			if(curUserId.equals(userId)||userId==null){//已登陆看自己
				//检查是否有traceId，如果有，说明是从其他人引导页注册登陆的，如果没有，表示自然注册登陆
				Integer traceId=CookieUtil.getTraceId(request);
				if(traceId!=null&&!traceId.equals(curUserId)){
					logger.info("login or register succ,jump to traceId:"+traceId);
					CookieUtil.removeTraceId(response);
					ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/people-i-know?id="+traceId));
					return mv;
				}
				String view="myvcard-nowx";
				if(HttpRequestUtil.isFromMicroMessenger(request)||w!=null){//w是后门，用来测试微信UA
					view="myvcard";
				}
				ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, view));
				renderUserBase(mv,curUserId,curUserId);
				mv.addObject("curUserId", curUserId);
				mv.addObject("myvcardflag",1);//作个标记，用于显示底部样式
				return mv;
			}else{//已登陆看他人页面
				ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/people-i-know?id="+userId));
				return mv;
			}
		}else{//未登陆
			if(userId!=null){
				String view="othersvcard-nowx";
				if(HttpRequestUtil.isFromMicroMessenger(request)||w!=null){
					view="othersvcard";
				}
				//写入cookie作为标记。登陆或注册后可以跳回来
				CookieUtil.setTraceId(response, userId);
				ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, view));
				renderUserBase(mv,userId,0);
				return mv;
			}else{
				ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/login-only"));
				return mv;
			}
		}
		
	}
	
	private void renderUserBase(ModelAndView mv,int userId,int visitedId){
		try{
			UserBase userbase=profileServiceStub.getProfile(userId, visitedId).getUserBase();
			UserOccupation occupation=occupationService.getOccupation(userId);
			//UserCountBase usercount=userCountService.getUserCountBase(userId);
			//int userValue=userCountService.getUserValueDetail(userId).getTotalValue();
			mv.addObject("userbase",userbase);
			//mv.addObject("followerCount",usercount.getFollowerCount());
			mv.addObject("occupation",occupation);
			//mv.addObject("uservalue",userValue);
			mv.addObject("avatarPicUrls",new PicUrls(userbase.getAvatar()));
			mv.addObject("qrPicUrls",new PicUrls(userbase.getFaceQRCode()));
			mv.addObject("newFollowerCount",relationService.getNewFollowerCount(visitedId));
			mv.addObject("userId",visitedId);
		}
		catch(Exception e){
			logger.warn("renderuserbase ERROR",e);
		}
	}
	
	@RequestMapping("people-i-know")
	public ModelAndView peopleIKnow(HttpServletRequest request,@RequestParam("id")Integer id,@RequestParam(value="w",required=false)String w){
		Integer curId=CookieUtil.getUserId(request);
		String view="";
		int relationType=relationService.getRelation(id, curId);
		if(relationType==RelationType.RELATION_FRIEND||relationType==RelationType.RELATION_FOLLOWING){
			view="people-i-know";
		}else{
			view="people-i-know-2";
		}
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, view));
		renderUserBase(mv,id,(curId==null?0:curId));
		mv.addObject("userId", curId);
		if(HttpRequestUtil.isFromMicroMessenger(request)||w!=null){
			mv.addObject("wx",1);//标识是否是微信UA
		}
		
		return mv;
	}
	
	
	@RequestMapping("avatar-large")
	public ModelAndView avatarLarge(HttpServletRequest request,Integer userId,@RequestParam(required=false)Integer flag){
		List<UserQRTemplate> qrtlist=templateService.getQRTemplates(userId);
		//如果没有头像，跳转到关注引导页
		UserBase base=accountService.getUserBaseById(userId);
		if(base.getAvatar()==null){
			String url="http://mp.weixin.qq.com/s?__biz=MzA4OTE2NDYyNw==&mid=10000162&idx=1&sn=0452cc1b20c582a20ca3baf6506db474#rd";
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:"+url));
		}else{
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "avatar-large"));
			Integer curId=CookieUtil.getUserId(request);
			renderUserBase(mv,userId,(curId==null?0:curId));
			mv.addObject("qrtlist",qrtlist);
			mv.addObject("flag", flag);//此flag标识进入这页后，应该显示头像大图还是二维码大图。点击别人的二维码，要显示二维码大图。
			return mv;
		}	
	}
	
	@RequestMapping("phone-number")
	public ModelAndView phoneNumber(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "phone-number"));
		return mv;
	}
	
	@RequestMapping("qr-large")
	public ModelAndView qrLarge(HttpServletRequest request,@RequestParam(required=false)Integer userId){
		Integer curId=CookieUtil.getUserId(request);
		if(userId==null){//访问自己页面
			if(curId==null){//未登陆，跳到注册页
				ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/account/phone-number"));
				return mv;
			}
			ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "qr-large"));
			//拿出用户qr模板状态
			List<UserQRTemplate> userQRTemplates=templateService.getQRTemplates(curId);
			//取该用户生成的二维码头像
			List<UserQRSet> qrcodes=qRcodeService.getAllQRcode(curId);
			renderUserBase(mv,curId,curId);
			mv.addObject("qrcodeswrapper",renderQRcodeWrappers(userQRTemplates,qrcodes));
			return mv;
		}else{//点击别人页面
			return new ModelAndView("redirect:/user/avatar-large?userId="+userId+"&flag=1");
		}	
	}
	
	private List<QRcodeWrapper> renderQRcodeWrappers(List<UserQRTemplate> userQRTemplates,List<UserQRSet> qrcodes){
		
		List<QRcodeWrapper> qRcodeWrappers=new ArrayList<QRcodeWrapper>();
		for(UserQRTemplate userQRTemplate:userQRTemplates){
			QRcodeWrapper qRcodeWrapper=new QRcodeWrapper();
			try {
				BeanUtils.copyProperties(qRcodeWrapper, userQRTemplate);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//有真实的二维码
			if(qrcodes!=null&&qrcodes.size()!=0){				
				for(UserQRSet qrcode:qrcodes){
					if(qrcode.getQrId()==qRcodeWrapper.getQTId()){
						qRcodeWrapper.setQrUrl(new PicUrls(qrcode.getQrUrl()).getL());
						break;
					}
				}
			}
			//无对应的二维码，用模板url补上
			if(qRcodeWrapper.getQrUrl()==null||qRcodeWrapper.getQrUrl().equals("")){
				qRcodeWrapper.setQrUrl(qRcodeWrapper.getQTUrl());
			}
			//写这段的时候，只有我和上帝知道为什么。
			//现在只有上帝知道为什么了。
			qRcodeWrappers.add(qRcodeWrapper);
		}
		return qRcodeWrappers;
	}
	
	@RequestMapping("login-unlock")
	public ModelAndView loginUnlock(HttpServletRequest request){
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "login-unlock"));
		int userId=CookieUtil.getUserId(request);
		List<UserWikaTemplate> wktlist=templateService.getWikaTemplates(userId);
		renderUserBase(mv,userId,userId);
		mv.addObject("wktlist",wktlist);
		return mv;
	}
	
	
	@RequestMapping("edit")
	public ModelAndView edit(HttpServletRequest request){
		int userId=CookieUtil.getUserId(request);
		UserBase userBase=accountService.getUserBaseById(userId);
		UserOccupation occupation=occupationService.getOccupation(userId);
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "edit"));
		mv.addObject("userbase",userBase);
		mv.addObject("occupation",occupation);
		mv.addObject("industries",WikaDataUtil.getIndustry().getData());
		mv.addObject("jobtype",WikaDataUtil.getJobType().getData());
		return mv;
	}
	
	@RequestMapping("update-service")
	public ModelAndView updateService(HttpServletRequest request,String name,@RequestParam(required=false)String email,
		Integer sex,@RequestParam(required=false)String location,@RequestParam(required=false)String introduce,String corp,Integer industry,String position,
		Integer jobType,@RequestParam(required=false)String department) {
		int userId=CookieUtil.getUserId(request);
		//增加了特殊字符过滤
		int result=accountService.updateUserInfo(userId, null, name, email, sex, location, introduce, 0, corp, industry, position, jobType, department==""?null:department);
		if(result==0){
			return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/myvcard?userId="+userId));
		}else{
			ModelAndView mv= new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/edit"));
			mv.addObject("errmsg",result);
			return mv;
		}
	}
	
	@RequestMapping("corp-search-ajax")
	@ResponseBody
	public String corpSearchSerivceAjax(String keyword){
		CorpAjaxData corpAjaxData=new CorpAjaxData();		
		List<String> corps=corpSearchService.getCorpName(keyword);
		List<CorpOption> dataList=new ArrayList<CorpOption>();
		for(String corp:corps){			
			dataList.add(new CorpOption(corp,corp));
		}
		corpAjaxData.setData(dataList);
		return WikaJsonUtil.toJson(corpAjaxData);
	}
	

	@RequestMapping("unlock-qr")
	public ModelAndView unlockQRtemplate(HttpServletRequest request,Integer id){
		Integer userId=CookieUtil.getUserId(request);
		templateService.unlockQRTemplate(userId, id);
		return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/myvcard?djshare=1&userId="+userId));
	}
	

	@RequestMapping("use-qr")
	public ModelAndView useQRtemplate(HttpServletRequest request,Integer id){
		Integer userId=CookieUtil.getUserId(request);
		//获取qrurl
		String qrUrl=qRcodeService.getQRcodeUrl(userId, id);
		int result=accountService.updateUserQRCode(userId, id, qrUrl);
		return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/qr-large"));
	}
	
	@RequestMapping("unlock-wika")
	public ModelAndView unlockWikatemplate(HttpServletRequest request,Integer id){
		Integer userId=CookieUtil.getUserId(request);
		templateService.unlockWikaTemplate(userId, id);
		return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/myvcard?djshare=1&userId="+userId));
	}
	

	@RequestMapping("use-wika")
	public ModelAndView useWikatemplate(HttpServletRequest request,Integer id){
		Integer userId=CookieUtil.getUserId(request);
		//获取qrurl
		int result=accountService.updateWikaTemplate(userId, id);
		return new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:/user/login-unlock"));
	}
}
