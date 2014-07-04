package com.dajie.wika.wap.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dajie.wika.model.wrapper.RelationUserView;
import com.dajie.wika.model.wrapper.RelationViewList;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.RelationService;
import com.dajie.wika.service.UserCountService;
import com.dajie.wika.wap.model.AjaxResult;
import com.dajie.wika.wap.model.PicUrls;
import com.dajie.wika.wap.util.CookieUtil;
import com.dajie.wika.wap.util.HttpRequestUtil;
import com.dajie.wika.wap.util.WikaJsonUtil;


@Controller
@RequestMapping("relation")
public class RelationController {
	
	private static final Logger logger = LoggerFactory.getLogger(RelationController.class);
	
	@Autowired
	private UserCountService userCountService;
	
	@Autowired
	private RelationService relationService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("i-know")
	public ModelAndView iKnow(HttpServletRequest request,@RequestParam(required=false)Integer following){
	    int userId=CookieUtil.getUserId(request);
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "i-know"));
		RelationViewList viewList=relationService.getFollowings(userId);
		mv.addObject("newFollowerCount",relationService.getNewFollowerCount(userId));
		mv.addObject("userId",userId);
		if(following==null||following==1){//如果是following页面
			
			if(viewList!=null){
				List<RelationUserView> list=viewList.getRelationUserViews();
				renderAvatar(list);
				mv.addObject("userlist", list);
				mv.addObject("followercount", viewList.getFollowerCount());
			}
		}
		else{
			//获取认识我的人
			viewList=relationService.getFollowers(userId);
			relationService.cleanNewFollower(userId);
			if(viewList!=null){
				List<RelationUserView> list=viewList.getRelationUserViews();
				renderAvatar(list);
				mv.addObject("userlist", list);
			}	
		}
		mv.addObject("following",following);//标识是否是following页面
		mv.addObject("followflag",1);//用于显示底部高亮
		mv.addObject("userbase",accountService.getUserBaseById(userId));
		return mv;
	}
	
	@RequestMapping("follow-service")
	public ModelAndView follow(HttpServletRequest request,@RequestParam("id")Integer followId){
		Integer userId=CookieUtil.getUserId(request);
		relationService.follow(userId, followId);
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:"+HttpRequestUtil.getReferer(request)));
		return mv;
	}
	
	@RequestMapping("follow-service-ajax")
	@ResponseBody
	public String followAjax(HttpServletRequest request,@RequestParam("id")Integer followId){
		Integer userId=CookieUtil.getUserId(request);
		int result=relationService.follow(userId, followId);
		//wap上面不弹，全部成功
		AjaxResult ajaxResult=new AjaxResult(0);
		return WikaJsonUtil.toJson(ajaxResult);
	}
	
	@RequestMapping("unfollow-service")
	public ModelAndView unfollow(HttpServletRequest request,@RequestParam("id")Integer followId){
		Integer userId=CookieUtil.getUserId(request);
		relationService.unfollow(userId, followId);
		ModelAndView mv=new ModelAndView(HttpRequestUtil.addStatInfo(request, "redirect:"+HttpRequestUtil.getReferer(request)));
		return mv;
	}
	
	private void renderAvatar(List<RelationUserView> list){
		if (list==null) return ;
		for(RelationUserView user:list){
			user.setAvatar(new PicUrls(user.getAvatar()).getB());
		}
	}
	
}

