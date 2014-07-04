package com.dajie.wika.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.QRcodeDAO;
import com.dajie.wika.model.UserQRSet;

@Component("qRcodeDAO")
public class QRcodeDAOImpl extends Dao implements QRcodeDAO{

	private static final String SQL_PREFIX = "com.dajie.wika.dao.QRcodeDAO.";
	
	public QRcodeDAOImpl() {
		super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
	}

    @PostConstruct
    public void init() {
        this.initResource();
    }

	@Override
	public int insertQRcode(List<UserQRSet> userQRSets) {
		if(userQRSets==null||userQRSets.size()==1){
			return 0;
		}
		return insert(SQL_PREFIX+"insertUserQRSets", userQRSets);
	}

	@Override
	public List<UserQRSet> getQRcode(int userId) {
		// TODO Auto-generated method stub
		return (List<UserQRSet>)selectList(SQL_PREFIX+"getUserQRcode", userId);
	}

	@Override
	public String getQRcodeUrl(int userId, int qrId) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("userId",userId);
        map.put("qrId",qrId);
		return (String)selectOne(SQL_PREFIX+"getUserQRcodeUrl", map);
	}

}
