package com.dajie.wika.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.common.dubbo.tolerance.Idempotent;
import com.dajie.wika.constants.returncode.ResultCodeConstant;
import com.dajie.wika.dao.FeedbackDAO;
import com.dajie.wika.model.Feedback;
import com.dajie.wika.service.SystemService;

@Service("systemService")
public class SystemServiceImpl implements SystemService {

	private Logger logger=LoggerFactory.getLogger(SystemServiceImpl.class);
	
	@Autowired
	private FeedbackDAO feedbackDAO;

	@Override
	@Idempotent(value = false)
	public int feedback(int userId, String content,String contact) {
		// TODO Auto-generated method stub
		try{
			Feedback feedback = new Feedback();
			feedback.setUserId(userId);
			feedback.setContent(content);
			feedback.setContact(contact);
			int result = feedbackDAO.insertFeedback(feedback);
			return result == 1 ? ResultCodeConstant.OP_SUCC
					: ResultCodeConstant.OP_FAIL;
		}
		catch(Exception e){
			logger.warn("feedback ERROR",e);
			return ResultCodeConstant.OP_FAIL;
		}
	}

	public void setFeedbackDAO(FeedbackDAO feedbackDAO) {
		this.feedbackDAO = feedbackDAO;
	}

}
