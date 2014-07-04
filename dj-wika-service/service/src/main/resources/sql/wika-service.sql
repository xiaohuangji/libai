
-- feedback信息表 
CREATE TABLE IF NOT EXISTS `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `content` varchar(1024) DEFAULT NULL COMMENT '反馈内容',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- idsequence表
CREATE TABLE IF NOT EXISTS `idsequence` (
  `id` bigint(20) NOT NULL COMMENT 'sequenceId',
  `type` int(11) NOT NULL DEFAULT '0' COMMENT '类型',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 消息表
CREATE TABLE IF NOT EXISTS `message` (
  `id` bigint(20) NOT NULL COMMENT '自增id',
  `from_id` int(11) NOT NULL COMMENT '发送者id',
  `to_id` int(11) NOT NULL COMMENT '接收者id',
  `type` int(11) NOT NULL COMMENT '消息类型',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `payload` varchar(256) DEFAULT NULL COMMENT '载体',
  PRIMARY KEY (`id`),
  KEY `to_id` (`to_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 第三方登陆映射表
CREATE TABLE IF NOT EXISTS `platform_map` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `platform_type` tinyint(11) NOT NULL COMMENT '第三方帐号类型',
  `platform_uid` varchar(50) NOT NULL DEFAULT '' COMMENT '第三方帐号id',
  `access_token` varchar(150) DEFAULT NULL COMMENT '授权的的token',
  `secret_token` varchar(150) DEFAULT NULL COMMENT '授权的secret token',
  `token_type` tinyint(1) DEFAULT NULL COMMENT 'token的类型',
  `expire` bigint(20) DEFAULT NULL COMMENT '过期时间',
  `source` tinyint(1) DEFAULT NULL COMMENT '来源',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态',
  `access_time` datetime NOT NULL COMMENT '接入时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`platform_uid`,`platform_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 关注表
CREATE TABLE IF NOT EXISTS `relation_follower` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `follower_id` int(11) NOT NULL COMMENT 'follower user Id',
  `is_new` tinyint(4) DEFAULT '1' COMMENT '是否新的关注者',
  `create_time` bigint(20) DEFAULT NULL COMMENT '关注时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`user_id`,`follower_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 被关注表
CREATE TABLE IF NOT EXISTS `relation_following` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `following_id` int(11) NOT NULL COMMENT 'following user id',
  `create_time` bigint(20) DEFAULT NULL COMMENT '关注他的时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`user_id`,`following_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 好友表
CREATE TABLE IF NOT EXISTS `relation_friend` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `friend_id` int(11) NOT NULL COMMENT '好友的userid',
  `create_time` bigint(20) DEFAULT NULL COMMENT '成为朋友的时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`user_id`,`friend_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 模板表
CREATE TABLE IF NOT EXISTS `template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tid` tinyint(4) NOT NULL DEFAULT '0',
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1:wika 2:qr',
  `url` varchar(200) DEFAULT NULL,
  `level` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0:anyone can see and can use 1:anyone can see but non-vip user need unlock 2:only vip can see',
  `unlock_action` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1:Share with Wechat to unlock  2.Download app to unlock',
  `name` varchar(100) DEFAULT NULL,
  `tips` varchar(100) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tid_type` (`tid`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户模板表
CREATE TABLE IF NOT EXISTS `unlock_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uid` int(11) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '1:wika 2:qr',
  `tids` varchar(100) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uid_type` (`uid`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户基本信息表
CREATE TABLE IF NOT EXISTS `user_base` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `wika_id` varchar(100) DEFAULT NULL COMMENT '微卡id',
  `password` char(64) DEFAULT NULL COMMENT '密码',
  `name` varchar(200) DEFAULT NULL COMMENT '名字',
  `gender` tinyint(1) DEFAULT NULL COMMENT '性别：0(unknow),1(male),2(female)',
  `avatar` varchar(300) DEFAULT NULL COMMENT '头像',
  `wika_template` tinyint(4) NOT NULL DEFAULT '1' COMMENT '微卡模板',
  `face_code_type` tinyint(4) DEFAULT NULL COMMENT '脸码模板',
  `face_qr_code` varchar(300) DEFAULT NULL COMMENT '脸码url',
  `email` varchar(150) DEFAULT NULL COMMENT '邮箱',
  `birth` date DEFAULT NULL COMMENT '生日',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机',
  `mobile_confirmed` tinyint(4) DEFAULT '1' COMMENT '手机是否确认过',
  `location` varchar(300) DEFAULT NULL COMMENT '地区',
  `type` tinyint(1) DEFAULT NULL COMMENT '帐号类型',
  `status` tinyint(1) DEFAULT NULL COMMENT '帐号状态',
  `feature` int(11) DEFAULT NULL COMMENT '帐号特征',
  `introduce` varchar(200) DEFAULT NULL COMMENT '个人简介',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `activation` tinyint(4) NOT NULL DEFAULT '0' COMMENT '帐号激活状态',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`user_id`),
  KEY `mobile` (`mobile`,`mobile_confirmed`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--用户基本计数信息表
CREATE TABLE IF NOT EXISTS `user_count_base` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `following_count` int(11) DEFAULT '0' COMMENT '关注人数',
  `follower_count` int(11) DEFAULT '0' COMMENT '被关注人数',
  `friend_count` int(11) DEFAULT '0' COMMENT '朋友数',
  `introduce_count` int(11) DEFAULT '0' COMMENT '推荐数',
  `visited_count` int(11) DEFAULT '0' COMMENT '被访问数',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户设备token表
CREATE TABLE IF NOT EXISTS `user_device` (
  `device_token` varchar(200) NOT NULL COMMENT '设备token',
  `device_type` tinyint(4) NOT NULL COMMENT 'token类型 1(IOS)，2（android）',
  `user_id` int(11) NOT NULL COMMENT '这个设备上的用户id',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间戳',
  PRIMARY KEY (`device_token`,`device_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户职业表
CREATE TABLE IF NOT EXISTS `user_occupation` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `corp` varchar(400) DEFAULT NULL COMMENT '公司',
  `industry` int(11) NOT NULL DEFAULT '0' COMMENT '行业',
  `position` varchar(400) DEFAULT NULL COMMENT '职位',
  `job_type` int(11) NOT NULL DEFAULT '0' COMMENT '职类',
  `department` varchar(400) DEFAULT NULL COMMENT '部门',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间戳',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户二维码头像表
CREATE TABLE IF NOT EXISTS `user_qr_code` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `qr_id` int(11) NOT NULL COMMENT '二维码id',
  `qr_url` varchar(100) DEFAULT NULL COMMENT '二维码url',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`qr_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户个性化设置表
CREATE TABLE IF NOT EXISTS `user_settings` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `email_privacy` tinyint(4) NOT NULL DEFAULT '0' COMMENT '邮箱隐私设置',
  `mobile_privacy` tinyint(4) NOT NULL DEFAULT '0' COMMENT '手机隐私设置',
  `notification` bigint(20) NOT NULL DEFAULT '0' COMMENT '消息接收设置',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间戳',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户身价详情表
CREATE TABLE IF NOT EXISTS `user_value_detail` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `userinfo_value` int(11) DEFAULT '0' COMMENT '个人信息身价',
  `sharewika_value` int(11) DEFAULT '0' COMMENT '分享身价',
  `visited_value` int(11) DEFAULT '0' COMMENT '被访问身价',
  `changeqr_value` int(11) DEFAULT '0' COMMENT '解锁qr身价。刚开始定义的是更换qr触发身价，所以列名有歧义',
  `changewika_value` int(11) DEFAULT '0' COMMENT '解锁wika身价',
  `following_value` int(11) DEFAULT '0' COMMENT '关注身价',
  `total_value` int(11) DEFAULT '0' COMMENT '总身价',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  KEY `total_value` (`total_value`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 用户身价趋势表
CREATE TABLE IF NOT EXISTS `user_value_trend` (
  `user_id` int(11) NOT NULL COMMENT '用户id',
  `user_value` int(11) NOT NULL COMMENT '用户身价',
  `day_start_time` bigint(20) NOT NULL COMMENT '一天的开始时间',
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间戳',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`day_start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/** 微卡模版数据导入 **/
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(1,1,0,'','简洁','免费',0);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(2,1,0,'','恭贺新禧','免费',0);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(3,1,1,'','“码”上有钱','下载应用解锁',2);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(4,1,1,'','炫彩','分享到微信解锁',1);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(5,1,1,'','暮光','下载应用解锁',2);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(6,1,1,'','水晶','分享到微信解锁',1);

/** 脸码模版数据导入 **/
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(1,2,0,'http://3.f1.dajieimg.com/group1/M00/3A/D3/CgpAmVLPUEyAN9agAAAAe6qVTF0605c.png','巴黎','免费',0);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(2,2,0,'http://9.f1.dajieimg.com/group1/M00/47/D9/CgpAo1LPUGqAUmf-AAAAe6qVTF0861c.png','新西兰','免费',0);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(3,2,1,'http://6.f1.dajieimg.com/group1/M00/3A/D3/CgpAmVLPUHuAG2wRAAAAe6qVTF0448c.png','马尔代夫','分享到微信解锁',1);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(4,2,1,'http://0.f1.dajieimg.com/group1/M00/47/D9/CgpAo1LPUI2AIhSLAAAAe6qVTF0552c.png','迪拜','下载应用解锁',2);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(5,2,1,'http://1.f1.dajieimg.com/group1/M00/3A/D3/CgpAmVLPUKCABvXeAAAAe6qVTF0213c.png','樱花','下载应用解锁',2);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(6,2,1,'http://3.f1.dajieimg.com/group1/M00/3A/D3/CgpAmVLPULGANQcFAAAAe6qVTF0575c.png','雾霾','下载应用解锁',2);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(7,2,1,'http://3.f1.dajieimg.com/group1/M00/3A/D3/CgpAmVLPUMWAW84GAAAAe6qVTF0455c.png','天空','分享到微信解锁',1);
insert into `template` (tid,type,level,url,name,tips,unlock_action) values(8,2,1,'http://7.f1.dajieimg.com/group1/M00/47/D9/CgpAo1LPUNSAFyXkAAAAe6qVTF0139c.png','森林','分享到微信解锁',1);

insert into `idsequence` (id,type) values (30000000,2); 
insert into `idsequence` (id,type) values (1,1); 
