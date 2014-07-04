package com.dajie.wika.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dajie.wika.dao.TemplateDAO;
import com.dajie.wika.dao.UnlockTemplateDAO;
import com.dajie.wika.enums.UserValueUpdateEventEnum;
import com.dajie.wika.model.QRTemplate;
import com.dajie.wika.model.UserQRTemplate;
import com.dajie.wika.model.UserWikaTemplate;
import com.dajie.wika.model.WikaTemplate;
import com.dajie.wika.service.TemplateService;
import com.dajie.wika.service.UserCountService;

@Service("templateService")
public class TemplateServiceImpl implements TemplateService {

    @Autowired
    private TemplateDAO templateDAO;

    @Autowired
    private UnlockTemplateDAO unlockTemplateDAO;

    @Autowired
    private UserCountService userCountService;

    private static Logger logger = LoggerFactory.getLogger(TemplateServiceImpl.class);

    private List<WikaTemplate> getWikaTemplates() {
        return templateDAO.getWikaTemplates();
    }

    private List<QRTemplate> getQRTemplates() {
        return templateDAO.getQRTemplates();
    }

    @Override
    public WikaTemplate getCurrentWikaTemplate(int userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<UserWikaTemplate> getWikaTemplates(int userId) {
        // boolean isVip = accountService.isVip(userId);

        boolean isVip = false;

        List<WikaTemplate> wts = getWikaTemplates();
        List<UserWikaTemplate> uwts = new ArrayList<UserWikaTemplate>();

        for (WikaTemplate wt : wts) {
            // vip用户可以看到和使用所有模板
            if (isVip) {
                UserWikaTemplate uwt = new UserWikaTemplate();
                uwt.setWTId(wt.getWTId());
                uwt.setWTUrl(wt.getWTUrl());
                uwt.setName(wt.getName());
                uwt.setTips(wt.getTips());
                uwt.setLevel(wt.getLevel());
                uwt.setUnlockAction(wt.getUnlockAction());
                uwt.setStatus(1);
                uwts.add(uwt);
            } else {
                // 如果用户不是vip, 但是模板的级别是只有vip可见
                if (wt.getLevel() == 2) continue;

                UserWikaTemplate uwt = new UserWikaTemplate();
                uwt.setWTId(wt.getWTId());
                uwt.setWTUrl(wt.getWTUrl());
                uwt.setName(wt.getName());
                uwt.setTips(wt.getTips());
                uwt.setLevel(wt.getLevel());
                uwt.setUnlockAction(wt.getUnlockAction());

                // 如果用户不是vip, 但是模板的级别需要解锁才可以使用
                if (wt.getLevel() == 1) {
                    uwt.setStatus(0);
                    String tids = unlockTemplateDAO.getUnlockedWikaTids(userId);
                    String[] s = StringUtils.split(StringUtils.defaultString(tids), ',');
                    for (String tid : s) {
                        if (tid.equals(String.valueOf(wt.getWTId()))) {
                            uwt.setStatus(1);
                        }
                    }
                } else {
                    // 模板的级别是所有人可以使用
                    uwt.setStatus(1);
                }
                uwts.add(uwt);
            }
        }
        return uwts;
    }

    @Override
    public List<UserQRTemplate> getQRTemplates(int userId) {
        // boolean isVip = accountService.isVip(userId);

        boolean isVip = false;

        List<QRTemplate> qts = getQRTemplates();
        List<UserQRTemplate> uqts = new ArrayList<UserQRTemplate>();

        for (QRTemplate qt : qts) {
            // vip用户可以看到和使用所有模板
            if (isVip) {
                UserQRTemplate uqt = new UserQRTemplate();
                uqt.setQTId(qt.getQTId());
                uqt.setQTUrl(qt.getQTUrl());
                uqt.setName(qt.getName());
                uqt.setTips(qt.getTips());
                uqt.setLevel(qt.getLevel());
                uqt.setUnlockAction(qt.getUnlockAction());
                uqt.setStatus(1);
                uqts.add(uqt);
            } else {
                // 如果用户不是vip, 但是模板的级别是只有vip可见
                if (qt.getLevel() == 2) continue;

                UserQRTemplate uqt = new UserQRTemplate();
                uqt.setQTId(qt.getQTId());
                uqt.setQTUrl(qt.getQTUrl());
                uqt.setName(qt.getName());
                uqt.setTips(qt.getTips());
                uqt.setLevel(qt.getLevel());
                uqt.setUnlockAction(qt.getUnlockAction());

                // 如果用户不是vip, 但是模板的级别需要解锁才可以使用
                if (qt.getLevel() == 1) {
                    uqt.setStatus(0);
                    String tids = unlockTemplateDAO.getUnlockedQRTids(userId);
                    String[] s = StringUtils.split(StringUtils.defaultString(tids), ',');
                    for (String tid : s) {
                        if (tid.equals(String.valueOf(qt.getQTId()))) {
                            uqt.setStatus(1);
                        }
                    }
                } else {
                    // 模板的级别是所有人可以使用
                    uqt.setStatus(1);
                }
                uqts.add(uqt);
            }
        }
        return uqts;

    }

    @Override
    public int unlockWikaTemplate(int userId, int WTId) {
        String tids = unlockTemplateDAO.getUnlockedWikaTids(userId);
        String[] s = StringUtils.split(StringUtils.defaultString(tids), ',');
        for (String tid : s) {
            if (tid.equals(String.valueOf(WTId))) return 0;
        }
        String newTids = "";
        int daoResult = 0;

        try {
            if (StringUtils.isBlank(tids)) {
                newTids = "" + WTId;
                unlockTemplateDAO.insertUnlockedWikaTids(userId, newTids);
            } else {
                newTids = tids + "," + WTId;
                unlockTemplateDAO.updateUnlockedWikaTids(userId, newTids);
            }
        } catch (Exception e) {
            daoResult = -1;
            logger.error(e.getMessage(), e);
        }
        userCountService.updateUserValue(userId, UserValueUpdateEventEnum.CHANGEWIKA);
        return daoResult;
    }

    @Override
    public int unlockQRTemplate(int userId, int QTId) {
        String tids = unlockTemplateDAO.getUnlockedQRTids(userId);
        String[] s = StringUtils.split(StringUtils.defaultString(tids), ',');
        for (String tid : s) {
            if (tid.equals(String.valueOf(QTId))) return 0;
        }
        String newTids = "";
        int daoResult = 0;

        try {
            if (StringUtils.isBlank(tids)) {
                newTids = "" + QTId;
                unlockTemplateDAO.insertUnlockedQRTids(userId, newTids);
            } else {
                newTids = tids + "," + QTId;
                unlockTemplateDAO.updateUnlockedQRTids(userId, newTids);
            }
        } catch (Exception e) {
            daoResult = -1;
        }
        userCountService.updateUserValue(userId, UserValueUpdateEventEnum.CHANGEQR);
        return daoResult;
    }

}
