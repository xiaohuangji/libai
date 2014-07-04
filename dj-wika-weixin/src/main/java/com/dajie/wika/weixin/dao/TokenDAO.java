package com.dajie.wika.weixin.dao;

import com.dajie.wika.weixin.dao.inject.DAO;
import com.dajie.wika.weixin.model.Token;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

/**
 * Created by wills on 2/11/14.
 */

@DAO
public interface TokenDAO {

    @Select("select * from wx_token where type=1")
    public Token getToken();

    @Insert("replace into wx_token (type,access_token) values (1,#{access_token})")
    public int setToken(Token token);
}
