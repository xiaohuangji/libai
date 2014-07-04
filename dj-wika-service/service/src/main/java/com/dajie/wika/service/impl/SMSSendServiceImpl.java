package com.dajie.wika.service.impl;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.sms.model.SmsRequest;
import com.dajie.sms.model.SmsResponse;
import com.dajie.sms.service.SmsService;
import com.dajie.wika.service.SMSSendService;

@Service("smsSendService")
public class SMSSendServiceImpl implements SMSSendService {
	private Logger logger = LoggerFactory.getLogger(SMSSendServiceImpl.class);

	@Autowired
	private SmsService smsService;

	/**
	 * 发送短信，大于0为正常发送
	 * 
	 * @param mobile
	 * @param msg
	 * @return
	 */
	public int smsSend(String mobile, String msg) {
		SmsRequest request = new SmsRequest();
		request.setMobile(mobile);
		request.setMsg(msg);
		// TODO 发送邮件确定短信通道
		request.setType("c");
		SmsResponse response = smsService.mt(request);
		// 短信下发的返回值处理
		int returnCode = NumberUtils.toInt(response.getResult());
		if (returnCode > 0) {
			logger.info("send Message success:" + "|"
					+ StringUtils.defaultString(mobile) + "|"
					+ StringUtils.defaultString(msg));
		} else {
			logger.info("send Message success:" + "|"
					+ StringUtils.defaultString(mobile) + "|"
					+ StringUtils.defaultString(msg));
		}
		return returnCode;
	}

	public SmsService getSmsService() {
		return smsService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

}
