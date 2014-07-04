package com.dajie.wika.model;

import java.io.Serializable;

public class WikaTemplate implements Serializable {

    private static final long serialVersionUID = -6249732966598104644L;

    private int WTId;

    private String WTUrl;

    private String name;

    private String tips;

    /**
     * 微卡模版的级别，级别越高，有权限见到的用户越少 0:anyone can see and can use 1:anyone can
     * see but non-vip user need unlock 2:only vip can see
     **/
    private int level;
    
    private int unlockAction;

    public int getWTId() {
        return WTId;
    }

    public void setWTId(int wTId) {
        WTId = wTId;
    }

    public String getWTUrl() {
        return WTUrl;
    }

    public void setWTUrl(String wTUrl) {
        WTUrl = wTUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getUnlockAction() {
        return unlockAction;
    }

    public void setUnlockAction(int unlockAction) {
        this.unlockAction = unlockAction;
    }

}
