package com.dajie.wika.dao;

import java.util.List;

import com.dajie.wika.model.PlatformMap;

public interface PlatformMapDAO {
	List<PlatformMap> getPlatformMapByUserId(int userId);

	PlatformMap getPlatformMapByUserIdAndType(int userId, int type);

	PlatformMap getPlatformMap(String platformUid, int type);

	int insert(PlatformMap map);

	int update(PlatformMap map);

	int delete(int userId, int type);
	
	int delete(String platformUid,int type);

}
