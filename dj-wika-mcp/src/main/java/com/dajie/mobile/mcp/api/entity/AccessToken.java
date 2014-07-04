/**
 * $Id $
 * Copyright 2009-2013 DAJIE-INC. All rights reserved.
 */
package com.dajie.mobile.mcp.api.entity;

///**
// * 
// */
//package com.dajie.mobile.mcp.api.entity;
//
//import java.util.List;
//
//
///**
// * @author wei.cheng
// *
// */
//public class AccessToken {
//
//    private int appId;
//
//    private int userId;
//
//    private int passHash;
//
//    private long expire;
//
//    private List<Integer> auths;
//
//    /** 值不能超过９位，否则会有问题 */
//    public int getAppId() {
//        return appId;
//    }
//
//    public void setAppId(int appId) {
//        this.appId = appId;
//    }
//
//    public int getUserId() {
//        return userId;
//    }
//
//    public void setUserId(int userId) {
//        this.userId = userId;
//    }
//
//    public long getExpire() {
//        return expire;
//    }
//
//    public void setExpire(long expire) {
//        if (expire <= 0) {//不允许出现这种值
//            return;
//        }
//        if (expire % 10 == 0) {
//            expire++;
//        }
//        this.expire = expire;
//    }
//
//    public List<Integer> getAuths() {
//        return auths;
//    }
//
//    public void setAuths(List<Integer> auths) {
//        this.auths = auths;
//    }
//
//    /**
//     * 是否已经过期
//     * 
//     * @return true expired | false normal
//     */
//    public boolean isExpired() {
//        return System.currentTimeMillis() > this.expire;
//    }
//
//    public int getPassHash() {
//        return passHash;
//    }
//
//    public void setPassHash(int passHash) {
//        this.passHash = passHash;
//    }
//
//    /**
//     * 是否授权
//     * 
//     * @param authId
//     * @return true authed | false
//     */
//    public boolean isAuthed(int authId) {
//        if (this.auths == null) {
//            return false;
//        }
//        return this.auths.contains(authId);
//    }
//
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(this.getClass().getSimpleName());
//        sb.append(" appId=");
//        sb.append(this.appId);
//        sb.append(",userId=");
//        sb.append(this.userId);
//        sb.append(",passHash=");
//        sb.append(this.passHash);
//        sb.append(",tick=");
//        sb.append(this.expire);
//        sb.append(",auth=[");
//        for (int i = 0; i < auths.size(); i++) {
//            if (i > 0) {
//                sb.append(",");
//            }
//            sb.append(auths.get(i));
//        }
//        sb.append("]");
//        return sb.toString();
//    }
// }
