package com.dajie.wika.weixin.dao;

import com.dajie.wika.weixin.dao.inject.DAO;
import com.dajie.wika.weixin.model.WXUserInfo;
import com.dajie.wika.weixin.model.WXUserTrace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by wills on 2/14/14.
 */
@DAO
public interface UserDAO {

    @Select("select * from wx_user where openid=#{openid}")
    public WXUserInfo getUserInfo(@Param("openid")String openid);

    @Insert("replace into wx_user (openid,nickname,sex,language,city,province,country,headimgurl,subscribe_time) values (" +
            "#{openid},#{nickname},#{sex},#{language},#{city},#{province},#{country},#{headimgurl},#{subscribe_time})")
    public int insertUserInfo(WXUserInfo userInfo);

    @Update("update wx_user set subscribe＝0 where openid=#{openid}")
    public int unSubscribe(@Param("openid")String openid);

    @Insert("replace into wx_user_trace (openid,type,recall_flag) values (#{openid},#{type},#{recall_flag})")
    public int recordUserTrace(@Param("openid")String openid,@Param("type")int type,@Param("recall_flag")int recall_flag);

    @Select("select * from wx_user_trace where openid=#{openid}")
    public WXUserTrace getUserTrace(@Param("openid")String openid);

    @Update("update wx_user set headimgurl_upload=#{headimgurl} where openid=#{openid}")
    public int updateUserHeadUrl(@Param("openid")String openid,@Param("headimgurl")String headimgurl);

    @Select("select * from wx_user_trace where update_time < from_unixtime(#{endTime}) and update_time > from_unixtime(#{startTime}) ")
    public List<WXUserTrace> getNeedRecallUser(@Param("startTime")long startTime,@Param("endTime")long endTime);
}
