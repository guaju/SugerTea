package com.guaju.sugertea.model.bean;

/**
 * Created by guaju on 2017/8/29.
 */

public class LoginInfo {
    private String phonenum;
    private String code;
    private LoginBean obj;

    public LoginInfo(String phonenum, String code, LoginBean obj) {
        this.phonenum = phonenum;
        this.code = code;
        this.obj = obj;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LoginBean getObj() {
        return obj;
    }

    public void setObj(LoginBean obj) {
        this.obj = obj;
    }
}
