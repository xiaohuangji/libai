/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.dajie.mobile.mcp.api.dao.AppInfoDAO;
import com.dajie.mobile.mcp.api.entity.MobileClientAppInfo;

/**
 * @author wei.cheng
 * 
 */
public class AppInfoDAOImpl extends JdbcDaoSupport implements AppInfoDAO {

    private static Logger logger = LoggerFactory.getLogger(AppInfoDAOImpl.class);

    private final static String SQL_GET_ALL = "select * from app_info";

    @Override
    public List<MobileClientAppInfo> getAll() {
        List<MobileClientAppInfo> appInfoList = null;
        try {
            appInfoList = getJdbcTemplate().query(SQL_GET_ALL, new AppInfoRowMapper());
        } catch (Exception e) {
            logger.error("AppInfoDAOImpl ", e);
        }
        return appInfoList;
    }

    private class AppInfoRowMapper implements RowMapper<MobileClientAppInfo> {

        @Override
        public MobileClientAppInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            MobileClientAppInfo appInfo = new MobileClientAppInfo();
            appInfo.setAppId(rs.getInt("app_id"));
            appInfo.setAppName(rs.getString("app_name"));
            appInfo.setAppUrl(rs.getString("app_url"));
            appInfo.setSecretKey(rs.getString("secret_key"));
            appInfo.setCreateTime(rs.getDate("create_time"));
            return appInfo;
        }

    }

}
