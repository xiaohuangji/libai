package com.dajie.wika.model;

import java.io.Serializable;

public class QRTemplate implements Serializable {

    private static final long serialVersionUID = 8284357139541385504L;

    private int QTId;

    private String QTUrl;

    private String name;

    private String tips;

    /**
     * 脸码模版的级别，级别越高，有权限见到的用户越少 0:anyone can see and can use 1:anyone can
     * see but non-vip user need unlock 2:only vip can see
     **/
    private int level;
    
    private int unlockAction;

    public int getQTId() {
        return QTId;
    }

    public void setQTId(int qTId) {
        QTId = qTId;
    }

    public String getQTUrl() {
        return QTUrl;
    }

    public void setQTUrl(String qTUrl) {
        QTUrl = qTUrl;
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
