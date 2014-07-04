package com.dajie.wika.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.common.dubbo.tolerance.Idempotent;
import com.dajie.wika.cache.JedisCacheUtil;
import com.dajie.wika.constants.MessageType;
import com.dajie.wika.constants.RelationType;
import com.dajie.wika.constants.SourceTypeConstant;
import com.dajie.wika.constants.returncode.RelationReturnCode;
import com.dajie.wika.constants.returncode.ResultCodeConstant;
import com.dajie.wika.dao.RelationFollowerDAO;
import com.dajie.wika.dao.RelationFollowingDAO;
import com.dajie.wika.dao.RelationFriendDAO;
import com.dajie.wika.enums.UserCountUpdateEventEnum;
import com.dajie.wika.enums.UserValueUpdateEventEnum;
import com.dajie.wika.model.Message;
import com.dajie.wika.model.UserBase;
import com.dajie.wika.model.UserCountBase;
import com.dajie.wika.model.UserOccupation;
import com.dajie.wika.model.message.MsgPayload2;
import com.dajie.wika.model.message.MsgPayload3;
import com.dajie.wika.model.wrapper.RelationUserView;
import com.dajie.wika.model.wrapper.RelationViewList;
import com.dajie.wika.service.AccountService;
import com.dajie.wika.service.MessageService;
import com.dajie.wika.service.OccupationService;
import com.dajie.wika.service.RelationService;
import com.dajie.wika.service.UserCountService;
import com.dajie.wika.service.utils.WikaGson;

@Service("relationService")
public class RelationServiceImpl implements RelationService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RelationServiceImpl.class);

	@Autowired
	private RelationFollowerDAO relationFollowerDAO;

	@Autowired
	private RelationFollowingDAO relationFollowingDAO;

	@Autowired
	private RelationFriendDAO relationFriendDAO;

	@Autowired
	private AccountService accountService;

	@Autowired
	private OccupationService occupationService;

	@Autowired
	private UserCountService userCountService;

	@Autowired
	private JedisCacheUtil jedisCacheUtil;

	@Autowired
	private MessageService messageService;

	@Override
	@Idempotent(false)
	public int follow(int userId, int followId) {
		LOGGER.info("user follow start: " + userId + " follow " + followId);
		if (userId == followId) {
			return RelationReturnCode.FOLLOW_SELF_FORBIDDEN;
		}
		// TODO 查询是否已经关注过
		boolean isFollowing = isFollowing(userId, followId);
		// insert following,follower
		if (!isFollowing) {
			try {
				boolean isFollowUserValid = isFollowUserValid(followId);
				if (isFollowUserValid) {
					int followRelationAdd = relationFollowingDAO.insert(userId,
							followId);
					followRelationAdd = followRelationAdd
							+ relationFollowerDAO.insert(followId, userId);

					// 插入了两条记录
					if (followRelationAdd == 2) {
						// 更新following计数
						userCountService.updateUserCountBase(userId,
								UserCountUpdateEventEnum.FOLLOWINGINCR);
						userCountService.updateUserCountBase(followId,
								UserCountUpdateEventEnum.FOLLOWERINCR);
					}
					// 查询是否自已被他关注
					boolean isFollower = relationFollowingDAO.isFollowing(
							followId, userId) > 0;
					// insert into friend
					if (isFollower) {
						int friendRelationAdd = relationFriendDAO.insert(
								userId, followId);
						friendRelationAdd += relationFriendDAO.insert(followId,
								userId);

						if (friendRelationAdd == 2) {
							userCountService.updateUserCountBase(userId,
									UserCountUpdateEventEnum.FRIENDINCR);
							userCountService.updateUserCountBase(followId,
									UserCountUpdateEventEnum.FRIENDINCR);
						}
					}
					//发送消息
					Message message = new Message(userId, followId,
							MessageType.COLLECTED_1);
					messageService.SendMessage(message);
					//更新身价
					userCountService.updateUserValue(userId, UserValueUpdateEventEnum.FOLLOWING);
					
					return ResultCodeConstant.OP_SUCC;
				} else {
					return RelationReturnCode.FOLLOW_USER_UNVALID;
				}
			} catch (Exception e) {
				LOGGER.error("relation.follow:" + userId + " follow "
						+ followId + " error.", e);
				return ResultCodeConstant.OP_FAIL;
			}
		} else
			return RelationReturnCode.HAS_FOLLOWED;

	}

	@Override
	@Idempotent(false)
	public int unfollow(int userId, int unfollowId) {
		LOGGER.info(userId + " unfollow " + unfollowId + " start");
		try {
			// delete from following,folower
			int followRelationDel = relationFollowerDAO.delete(unfollowId,
					userId);
			followRelationDel += relationFollowingDAO
					.delete(userId, unfollowId);
			LOGGER.info("delete relation following and follower item:"
					+ followRelationDel);
			if (followRelationDel == 2) {
				// 更新following计数
				userCountService.updateUserCountBase(userId,
						UserCountUpdateEventEnum.FOLLOWINGDECR);
				userCountService.updateUserCountBase(unfollowId,
						UserCountUpdateEventEnum.FOLLOWERDECR);
			}
			// delete from friends
			int friendRelationDel = relationFriendDAO
					.delete(userId, unfollowId);
			friendRelationDel += relationFriendDAO.delete(unfollowId, userId);
			LOGGER.info("delete relation friend item:" + friendRelationDel);
			if (friendRelationDel == 2) {
				userCountService.updateUserCountBase(userId,
						UserCountUpdateEventEnum.FRIENDDECR);
				userCountService.updateUserCountBase(unfollowId,
						UserCountUpdateEventEnum.FRIENDDECR);
			}			
			//更新身价
			userCountService.updateUserValue(userId, UserValueUpdateEventEnum.UNFOLLOWING);
			if (followRelationDel == 2) {
				LOGGER.info(userId + " unfollow " + unfollowId + " suc.");
				return ResultCodeConstant.OP_SUCC;
			} else {
				LOGGER.info(userId + " unfollow " + unfollowId
						+ " fail. followRelationDel = " + followRelationDel);
				return ResultCodeConstant.OP_FAIL;
			}

		} catch (Exception e) {
			LOGGER.error("relation.unfollow: " + userId + " unfollow "
					+ unfollowId + " error.", e);
			return ResultCodeConstant.OP_FAIL;
		}

	}

	@Override
	public RelationViewList getFollowers(int userId) {
		// select userBase batch
		List<Integer> followerIds = relationFollowerDAO.getFollowers(userId);
		List<RelationUserView> followerUsers = convert2RelationUserViews(
				userId, followerIds, RelationType.RELATION_FOLLOWER);
		RelationViewList viewList = new RelationViewList();
		viewList.setRelationUserViews(followerUsers);
		UserCountBase userCountBase = userCountService.getUserCountBase(userId);
		if (null != userCountBase) {
			viewList.setFollowerCount(userCountBase.getFollowerCount());
			viewList.setFollowingCount(userCountBase.getFollowingCount());
			viewList.setFriendCount(userCountBase.getFriendCount());
		}
		return viewList;
	}

	@Override
	public RelationViewList getFollowings(int userId) {
		List<Integer> followingIds = relationFollowingDAO.getFollowings(userId);
		List<RelationUserView> followingUsers = convert2RelationUserViews(
				userId, followingIds, RelationType.RELATION_FOLLOWING);
		RelationViewList viewList = new RelationViewList();
		viewList.setRelationUserViews(followingUsers);
		UserCountBase userCountBase = userCountService.getUserCountBase(userId);
		if (null != userCountBase) {
			viewList.setFollowerCount(userCountBase.getFollowerCount());
			viewList.setFollowingCount(userCountBase.getFollowingCount());
			viewList.setFriendCount(userCountBase.getFriendCount());
		}
		return viewList;
	}

	@Override
	public int getNewFollowerCount(int userId) {
		int newFollowingCnt = relationFollowerDAO.getNewFollowerCount(userId);
		return newFollowingCnt;
	}

	@Override
	public void cleanNewFollower(int userId) {
		int cleanedCount = relationFollowerDAO.cleanNewFollower(userId);
		LOGGER.info("clean new follower : " + cleanedCount);
	}

	@Override
	public RelationViewList getFriends(int userId) {
		List<Integer> friendIds = relationFriendDAO.getFriends(userId);
		List<RelationUserView> friendUsers = new ArrayList<RelationUserView>();
		for (int friendId : friendIds) {
			RelationUserView userView = new RelationUserView();
			UserBase userBase = accountService.getUserBaseById(friendId);
			UserOccupation occupation = occupationService.getOccupation(friendId);
			if (userBase != null && occupation != null) {
				userView.setName(userBase.getName());
				userView.setCorp(occupation.getCorp() + "");
				userView.setPosition(occupation.getPosition() + "");
				userView.setUserId(friendId);
				userView.setRelationType(RelationType.RELATION_FRIEND);
				friendUsers.add(userView);
			}
		}

		RelationViewList viewList = new RelationViewList();
		viewList.setRelationUserViews(friendUsers);
		UserCountBase userCountBase = userCountService.getUserCountBase(userId);
		if (null != userCountBase) {
			viewList.setFollowerCount(userCountBase.getFollowerCount());
			viewList.setFollowingCount(userCountBase.getFollowingCount());
			viewList.setFriendCount(userCountBase.getFriendCount());
		}
		return viewList;
	}

	@Override
	public int getRelation(int userId, int visitedId) {
		int relationType = RelationType.NO_RELAITON;
		if (userId == visitedId) {
			relationType = RelationType.RELATION_SELF;
		} else {
			boolean isFollowing = isFollowing(visitedId, userId);
			boolean isFollower = isFollower(visitedId, userId);
			relationType = (isFollowing ? RelationType.RELATION_FOLLOWING : 0)
					+ (isFollower ? RelationType.RELATION_FOLLOWER : 0);
		}
		return relationType;

	}

	@Override
	public boolean isFollowing(int userId, int followId) {
		return relationFollowingDAO.isFollowing(userId, followId) > 0;
	}

	@Override
	public boolean isFollower(int userId, int followerId) {
		return relationFollowerDAO.isFollower(userId, followerId) > 0;
	}

	@Override
	public List<Integer> getFollowingIds(int userId) {
		List<Integer> followingIds = relationFollowingDAO.getFollowings(userId);
		return followingIds;
	}

	@Override
	public List<Integer> getFollowerIds(int userId) {
		List<Integer> followingIds = relationFollowerDAO.getFollowers(userId);
		return followingIds;
	}

	@Override
	public List<Integer> getFriendIds(int userId) {
		List<Integer> friendIds = relationFriendDAO.getFriends(userId);
		return friendIds;
	}

	@Override
	@Idempotent(value = false)
	public int sayHi(int userId, int toId) {
		// send a message
		Message message = new Message(userId, toId, MessageType.SAYHI_5);
		messageService.SendMessage(message);
		return ResultCodeConstant.OP_SUCC;
	}

	@Override
	@Idempotent(value = false)
	public int introduce(int userId, int sourceId, List<Integer> destIds,
			String content) {
		// send a message
		for (Integer destId : destIds) {
			// 给destId发消息,收到wikia推荐
			Message message = new Message(sourceId, destId,
					MessageType.RECOMMEND_2);
			MsgPayload2 msgPayload2 = new MsgPayload2(userId, content);
			message.setPayload(WikaGson.toJson(msgPayload2));
			messageService.SendMessage(message);
			if (userId != sourceId) {// 如果userId==souceId，自己不收到消息
				// 给sourceId发消息
				message = new Message(destId, sourceId,
						MessageType.RECOMMENDED_3);
				MsgPayload3 msgPayload3 = new MsgPayload3(userId);
				message.setPayload(WikaGson.toJson(msgPayload3));
				messageService.SendMessage(message);
			}
		}
		return ResultCodeConstant.OP_SUCC;
	}

	private List<RelationUserView> convert2RelationUserViews(int userId,
			List<Integer> relationIds, int relationType) {

		List<RelationUserView> relationUsers = new ArrayList<RelationUserView>();

		if (relationIds != null && relationIds.size() > 0) {
			List<Integer> friendIds = getFriendIds(userId);
			for (int relationId : relationIds) {
				RelationUserView userView = new RelationUserView();
				UserBase userBase = accountService.getUserBaseById(relationId);
				UserOccupation occupation = occupationService
						.getOccupation(relationId);
				if (userBase != null && occupation != null) {
					userView.setName(userBase.getName());
					userView.setAvatar(userBase.getAvatar());
					userView.setCorp(occupation.getCorp() + "");
					userView.setPosition(occupation.getPosition() + "");
					userView.setUserId(relationId);
					int userRelationType = relationType;
					if (friendIds != null && friendIds.size() > 0) {
						userRelationType = friendIds.contains(relationId) ? RelationType.RELATION_FRIEND
								: relationType;
					}
					userView.setRelationType(userRelationType);
					relationUsers.add(userView);
				}
			}
		}
		return relationUsers;
	}

	private boolean isFollowUserValid(int userId) {
		return accountService.isCompleteRequriedInfo(userId,SourceTypeConstant.APP_SOURCE);
	}

	@Override
	public int shareWika(int userId) {
		// TODO Auto-generated method stub
		return userCountService.updateUserValue(userId, UserValueUpdateEventEnum.SHAREWIKA);
	}
}
