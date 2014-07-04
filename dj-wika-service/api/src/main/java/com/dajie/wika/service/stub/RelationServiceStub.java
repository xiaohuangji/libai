package com.dajie.wika.service.stub;

import com.dajie.wika.model.wrapper.RelationActionReturn;

public interface RelationServiceStub {
	public RelationActionReturn follow(int userId, int followId);

	public RelationActionReturn unfollow(int userId, int unfollowId);
}
