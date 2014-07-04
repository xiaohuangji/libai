package com.dajie.wika.service.stub.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.wika.constants.PrivacyType;
import com.dajie.wika.constants.RelationType;
import com.dajie.wika.enums.UserCountUpdateEventEnum;
import com.dajie.wika.enums.UserValueUpdateEventEnum;
import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.UserPrivacySettings;
import com.dajie.wika.model.UserValue;
import com.dajie.wika.model.UserValueDetail;
import com.dajie.wika.model.wrapper.UserCountLiteWrapper;
import com.dajie.wika.model.wrapper.UserProfile;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.OccupationService;
import com.dajie.wika.service.RelationService;
import com.dajie.wika.service.ShortUrlService;
import com.dajie.wika.service.UserCountService;
import com.dajie.wika.service.UserSettingsService;
import com.dajie.wika.service.stub.ProfileServiceStub;

@Service("profileServiceStub")
public class ProfileServiceStupImpl implements ProfileServiceStub {
	
	private static final Logger logger=LoggerFactory.getLogger(ProfileServiceStupImpl.class);

	private static final int USER_VALUE_TREND_LIMIT = 12;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserCountService userCountService;

	@Autowired
	private OccupationService occupationService;

	@Autowired
	private RelationService relationService;
	
	@Autowired
	private ShortUrlService shortUrlService;
	
	@Autowired
	private UserSettingsService userSettingsService;
	
	private static final String ONLYFRIEND="[互相添加后可见]";
	
	@Override
	public UserProfile getProfile(int userId, int visitedId) {
		//被访问增加身价
		//被访问增加访问量
		if(userId!=visitedId){//访问自己时身价不变
			userCountService.updateUserValue(userId, UserValueUpdateEventEnum.VISITED);
			userCountService.updateUserCountBase(userId, UserCountUpdateEventEnum.VISITEDINCR);
		}
		
		UserProfile userProfile = new UserProfile(userId);
	
		userProfile.setUserOccupation(occupationService.getOccupation(userId));
		userProfile.setUserCountLiteWrapper(getUserCountLiteWrapper(userId));
		//获取访问者和被访问者关系
		int relationType=relationService.getRelation(userId,visitedId);
		userProfile.setRelationType(relationType);
		//隐私控制
		UserBase userBase = accountService.getUserBaseById(userId);
		/**
		 * 无敌三层if-else。只能怪辉哥relation.self定义成了-1
		 */
		if(relationType!=RelationType.RELATION_SELF){//如果是获取自身信息，不进行隐私控制
			UserPrivacySettings  privacy=userSettingsService.getUserPrivacySettings(userId);
			if(privacy!=null){
				//email判断
				if(privacy.getEmailPrivacy()==PrivacyType.FRIEND){
					if(relationType<RelationType.RELATION_FRIEND){
						userBase.setEmail(ONLYFRIEND);
					}
				}else if(privacy.getEmailPrivacy()==PrivacyType.SELF){
					userBase.setEmail("");
				}
				//mobile判断
				if(privacy.getMobilePrivacy()==PrivacyType.FRIEND){
					if(relationType<RelationType.RELATION_FRIEND){
						userBase.setMobile(ONLYFRIEND);
					}	
				}else if(privacy.getMobilePrivacy()==PrivacyType.SELF){
						userBase.setMobile("");
				}
			}else{
				logger.warn("getprivary null:"+userId);
			}
		}
		userProfile.setUserBase(userBase);
		userProfile.setShortUrl(shortUrlService.genWikaProfileShortUrl(userId));
		return userProfile;
	}

	private UserCountLiteWrapper getUserCountLiteWrapper(int userId) {
		UserCountLiteWrapper userCountLiteWrapper = new UserCountLiteWrapper();
		UserValueDetail detail=userCountService.getUserValueDetail(userId);
		userCountLiteWrapper.setUserValue(detail==null?0:detail.getTotalValue());
		List<UserValue> userValues=userCountService.getUserValueTrend(userId, 0, USER_VALUE_TREND_LIMIT);
/*		int len=userValues.size();
		if(userValues!=null&&len<USER_VALUE_TREND_LIMIT){
			UserValue userValue=userValues.get(len-1);
			while(len++<USER_VALUE_TREND_LIMIT){
				userValues.add(userValue);
			}
		}*/
		userCountLiteWrapper.setUserValues(userValues);
		return userCountLiteWrapper;
	}
}
