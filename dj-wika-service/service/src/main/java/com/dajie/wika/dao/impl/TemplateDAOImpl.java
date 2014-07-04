package com.dajie.wika.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dajie.common.framework.ibatis.Dao;
import com.dajie.wika.constants.MobileConstant;
import com.dajie.wika.dao.TemplateDAO;
import com.dajie.wika.model.QRTemplate;
import com.dajie.wika.model.WikaTemplate;

@Component("templateDAO")
public class TemplateDAOImpl extends Dao implements TemplateDAO {

    private static final String SQL_PREFIX = "com.dajie.wika.dao.TemplateDAO.";

    public TemplateDAOImpl() {
        super(MobileConstant.DAJIE_MOBILE, MobileConstant.MOBILE_MASTER);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WikaTemplate> getWikaTemplates() {        
        return (List<WikaTemplate>) selectList(SQL_PREFIX + "getWikaTemplates");
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<QRTemplate> getQRTemplates() {       
        return (List<QRTemplate>) selectList(SQL_PREFIX + "getQRTemplates");
    }

}
