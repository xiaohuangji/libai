package com.dajie.wika.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dajie.core.profile.model.CardInfo;
import com.dajie.core.profile.model.UserDetail;
import com.dajie.core.profile.service.CardInfoService;
import com.dajie.core.profile.service.ProfileService;
import com.dajie.infra.user.model.UserBase;
import com.dajie.infra.user.model.UserPhone;
import com.dajie.infra.user.service.UserBaseService;
import com.dajie.wika.constants.DJAccountConstant;
import com.dajie.wika.model.DJLoginReturn;
import com.dajie.wika.model.DJUserInfo;
import com.dajie.wika.service.utils.StringUtil;

@Component("djAccountService")
public class DJAccountService {

	private Logger logger = LoggerFactory.getLogger(DJAccountService.class);
	@Autowired
	private UserBaseService userService;

	@Autowired
	private ProfileService profileService;

	@Autowired
	private CardInfoService cardInfoService;

	public DJLoginReturn loginWithDJ(String account, String password) {
		DJLoginReturn djLoginReturn = new DJLoginReturn();
		try {
			UserBase userBase = userService.login(account, password);
			// 未正常返回
			if (userBase == null) {
				boolean isDJAccountExist = isDJAccountExist(account);
				if (isDJAccountExist) {
					djLoginReturn
							.setCode(DJAccountConstant.ACCOUNT_PASSWORD_ERROR);
				} else {
					djLoginReturn.setCode(DJAccountConstant.ACCOUNT_NOT_EXIST);
				}
				return djLoginReturn;
			} else {
				// 大街的正常用户
				if (!userBase.isDeleted() && !userBase.isDisabled()
						&& !userBase.isCanceled()) {
					djLoginReturn.setCode(DJAccountConstant.ACCOUNT_LOGIN_SUC);
					djLoginReturn.setDjId(userBase.getUid());
					return djLoginReturn;
				} else {
					logger.info("dajie user is unnormal");
					if (userBase.isDeleted() || userBase.isCanceled()) {
						logger.info("dajie account is delete..");
						djLoginReturn
								.setCode(DJAccountConstant.ACCOUNT_NOT_EXIST);
					} else if (userBase.isDisabled()) {
						logger.info("dajie account is disable");
						djLoginReturn
								.setCode(DJAccountConstant.ACCOUNT_DISABLE);
					}
					return djLoginReturn;
				}
			}
		} catch (Exception e) {
			logger.error("login with dajie error", e);
			djLoginReturn.setCode(DJAccountConstant.ACCOUNT_LOGIN_FAIL);
			return djLoginReturn;
		}
	}

	public DJUserInfo getDjUserInfoByDjId(int DjId) {
		UserBase userBase = userService.getUserByUid(DjId);
		if (!userBase.isDeleted() && !userBase.isDisabled()
				&& !userBase.isCanceled()) {
			DJUserInfo djUserInfo = new DJUserInfo();
			logger.info("dajie user login : userId=" + userBase.getUid());
			djUserInfo.setDjId(userBase.getUid());
			djUserInfo.setName(userBase.getName());
			List<String> emails = userBase.getEmails();
			if (emails != null && emails.size() > 0) {
				djUserInfo.setEmail(userBase.getEmails().get(0));
			}
			// TODO 其它信息
			if (userBase.isGuided()) {
				String avatar = userBase.getAvatar();
				djUserInfo.setAvatar(avatar);
				logger.info("get avatar url suffix : " + avatar + " ,djId = "
						+ userBase.getUid());
				UserDetail detail = profileService.getUserDetailByUid(userBase
						.getUid());
				if (detail != null) {
					djUserInfo.setGender(detail.getGender());
				}
				UserPhone userPhone = userService.getPhoneByUid(DjId);
				if (null != userPhone) {
					logger.info("get mobile from dajie = "
							+ userPhone.getPhoneNumber());
					djUserInfo.setMobile(userPhone.getPhoneNumber());
				} else {
					logger.info("get mobile from dajie =" + null);
				}
				CardInfo card = cardInfoService.getCardInfoByUid(userBase
						.getUid());
				if (card != null) {
					String position = card.getPosition();
					String corp = card.getCorpName();
					int industry = null != card.getIndustry() ? card
							.getIdentity() : 0;
					int jobType = null != card.getProfession() ? card
							.getProfession() : 0;
					String location = card.getLivecity();
					djUserInfo.setIndustry(industry);
					djUserInfo.setJobType(jobType);
					djUserInfo.setPosition(position);
					djUserInfo.setCorp(corp);
					djUserInfo.setLocation(location);
				}
			}
			return djUserInfo;
		}
		return null;
	}

	private boolean isDJAccountExist(String account) {
		UserBase userBase = null;
		if (StringUtil.isEmail(account)) {
			userBase = userService.getUserByEmail(account);
		}
		if (StringUtil.isPhoneNumber(account)) {
			userBase = userService.getUserByPhone(account);
		}
		if (userBase != null) {
			return true;
		}
		return false;
	}
}
