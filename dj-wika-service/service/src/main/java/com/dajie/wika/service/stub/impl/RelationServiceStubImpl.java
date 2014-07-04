package com.dajie.wika.service.stub.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.common.dubbo.tolerance.Idempotent;
import com.dajie.wika.constants.returncode.ResultCodeConstant;
import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.UserOccupation;
import com.dajie.wika.model.wrapper.RelationActionReturn;
import com.dajie.wika.model.wrapper.RelationUserView;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.OccupationService;
import com.dajie.wika.service.RelationService;
import com.dajie.wika.service.stub.RelationServiceStub;

@Service("relationServiceStub")
public class RelationServiceStubImpl implements RelationServiceStub {

	@Autowired
	private RelationService relationService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private OccupationService occupationService;

	@Override
	@Idempotent(value = false)
	public RelationActionReturn follow(int userId, int followId) {
		int returnCode = relationService.follow(userId, followId);
		RelationActionReturn relationActionReturn = new RelationActionReturn();
		relationActionReturn.setReturnCode(returnCode);
		if (returnCode == ResultCodeConstant.OP_SUCC) {
			relationActionReturn.setRelationUserView(convert2RelationUserViews(
					userId, followId));
		}
		return relationActionReturn;
	}

	@Override
	@Idempotent(value = false)
	public RelationActionReturn unfollow(int userId, int unfollowId) {
		int returnCode = relationService.unfollow(userId, unfollowId);
		RelationActionReturn relationActionReturn = new RelationActionReturn();
		relationActionReturn.setReturnCode(returnCode);
		if (returnCode == ResultCodeConstant.OP_SUCC) {
			relationActionReturn.setRelationUserView(convert2RelationUserViews(
					userId, unfollowId));
		}
		return relationActionReturn;
	}

	private RelationUserView convert2RelationUserViews(int userId,
			int relationId) {

		RelationUserView userView = new RelationUserView();

		UserBase userBase = accountService.getUserBaseById(relationId);
		if (userBase != null) {
			UserOccupation occupation = occupationService
					.getOccupation(relationId);
			userView.setName(userBase.getName());
			userView.setAvatar(userBase.getAvatar());
			userView.setCorp(occupation.getCorp() + "");
			userView.setPosition(occupation.getPosition() + "");
			userView.setUserId(relationId);
			int relationType = relationService.getRelation(relationId, userId);
			userView.setRelationType(relationType);
		}
		return userView;
	}

}
