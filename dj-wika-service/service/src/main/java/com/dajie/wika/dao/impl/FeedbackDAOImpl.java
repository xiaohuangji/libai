package com.dajie.wika.dao.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.FeedbackDAO;
import com.dajie.wika.model.Feedback;

@Component("feedbackDAO")
public class FeedbackDAOImpl extends Dao implements FeedbackDAO{

	private static final String SQL_PREFIX = "com.dajie.wika.dao.FeedbackDAO.";
	
	public FeedbackDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

	@Override
	public int insertFeedback(Feedback feedback) {
	   if(null == feedback) {
	       return 0;
	   }
	   return insert(SQL_PREFIX + "insert", feedback);
	}

	@PostConstruct
	public void init() {
	    this.initResource();
	}

}
