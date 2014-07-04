package com.dajie.wika.enums;

import java.util.HashMap;
import java.util.Map;

import com.dajie.common.bean.CodedEnum;

public enum UserCountUpdateEventEnum implements CodedEnum{
	UNKNOWN(0),
	FOLLOWINGINCR(1),
	FOLLOWERINCR(2),
	FRIENDINCR(3),
	VISITEDINCR(4),
	FOLLOWINGDECR(5),
	FOLLOWERDECR(6),
	FRIENDDECR(7),
	;

    private int code;

    private UserCountUpdateEventEnum(int code) {
        this.code = code;
    }
    
	@Override
	public int getCode() {
		// TODO Auto-generated method stub
		return code;
	}

    public static UserCountUpdateEventEnum parse(int code) {
        for (UserCountUpdateEventEnum status : UserCountUpdateEventEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return UNKNOWN;
    }
    
    private static Map valuemap=new HashMap<UserCountUpdateEventEnum,Integer>(){{
    	put(FOLLOWINGINCR, 1);
    	put(FOLLOWERINCR,1);
    	put(FRIENDINCR,1);
    	put(VISITEDINCR, 1);
    	put(FOLLOWINGDECR,-1);
    	put(FOLLOWERDECR, -1);
    	put(FRIENDDECR, -1);
    }};
    
    private static Map mysqlmap=new HashMap<UserCountUpdateEventEnum,String>(){{
    	put(FOLLOWINGINCR, "following_count");
    	put(FOLLOWERINCR,"follower_count");
    	put(FRIENDINCR,"friend_count");
    	put(VISITEDINCR, "visited_count");
    	put(FOLLOWINGDECR, "following_count");
    	put(FOLLOWERDECR, "follower_count");
    	put(FRIENDDECR,"friend_count");
    }};
    
    public static String getMysqlColumnByType(UserCountUpdateEventEnum type){
    	return (String)mysqlmap.get(type);
    }
    
    public static Integer getValueByType(UserCountUpdateEventEnum type){
    	return (Integer)valuemap.get(type);
    }
}
