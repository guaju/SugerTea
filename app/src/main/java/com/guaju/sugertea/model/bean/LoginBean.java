package com.guaju.sugertea.model.bean;

import android.os.Parcel;

/**
 * Created by lfs on 2017/7/19.
 */

public class LoginBean {
  private UserInfoBean user;

    public UserInfoBean getUser() {
        return user;
    }

    public void setUser(UserInfoBean user) {
        this.user = user;
    }



    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.user = in.readParcelable(UserInfoBean.class.getClassLoader());
    }


}
