package com.dajie.wika.weixin.model;

import java.io.Serializable;

/**
 * Created by wills on 2/10/14.
 *
 */
public class Error implements Serializable {

    private int errcode ;

    private String errmsg ;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "Error{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
