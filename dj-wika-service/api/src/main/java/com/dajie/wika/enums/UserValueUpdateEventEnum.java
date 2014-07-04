package com.dajie.wika.enums;

import java.util.HashMap;
import java.util.Map;

import com.dajie.common.bean.CodedEnum;

public enum UserValueUpdateEventEnum implements CodedEnum{
	
	UNKNOWN(0),
	ADDUSERINFO(1),
	SHAREWIKA(2),
	VISITED(3),
	CHANGEQR(4),
	CHANGEWIKA(5),
	FOLLOWING(6),
	UNFOLLOWING(7)
	;

	private int code;

    private UserValueUpdateEventEnum(int code) {
	      this.code = code;
	}
	    
	    
	@Override
	public int getCode() {
		// TODO Auto-generated method stub
		return code;
	}
	
    public static UserValueUpdateEventEnum parse(int code) {
        for (UserValueUpdateEventEnum status : UserValueUpdateEventEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return UNKNOWN;
    }
    
    private static Map valuemap=new HashMap<UserValueUpdateEventEnum,Integer>(){{
    	put(ADDUSERINFO, 500);
    	put(SHAREWIKA,100);
    	put(VISITED,50);
    	put(CHANGEQR, 1000);
    	put(CHANGEWIKA, 1000);
    	put(FOLLOWING, 100);
    	put(UNFOLLOWING, -100);
    }};
    
    private static Map mysqlmap=new HashMap<UserValueUpdateEventEnum,String>(){{
    	put(ADDUSERINFO, "userinfo_value");
    	put(SHAREWIKA,"sharewika_value");
    	put(VISITED,"visited_value");
    	put(CHANGEQR, "changeqr_value");
    	put(CHANGEWIKA, "changewika_value");
    	put(FOLLOWING, "following_value");
    	put(UNFOLLOWING, "following_value");
    }};
    public static int getValueByType(UserValueUpdateEventEnum type){
    	return (Integer)valuemap.get(type);
    }
    public static String getMysqlColumnByType(UserValueUpdateEventEnum type){
    	return (String)mysqlmap.get(type);
    }

}
