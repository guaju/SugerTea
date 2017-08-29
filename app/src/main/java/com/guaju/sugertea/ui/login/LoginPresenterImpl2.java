package com.guaju.sugertea.ui.login;

import android.text.TextUtils;

import com.guaju.sugertea.httputil.HttpHelper;

/**
 * Created by guaju on 2017/8/25.
 */

public class LoginPresenterImpl2 implements LoginContract.LoginPresenter {
    private LoginContract.LoginView loginView;

    public LoginPresenterImpl2(LoginContract.LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void registSendCodeListener() {

    }

    @Override
    public void sendCode(String phoneNum) {
        if (TextUtils.isEmpty(phoneNum)){
            //弹吐司
           loginView.vertifyPhoneNumError();
            return;
        } else{
            //当前系统时间 因为java的系统时间比js的大1000倍，所以除以1000
            long curTime= System.currentTimeMillis() / 1000;
            String ip="117.136.38.79";
            String mac="02:00:00:00:00:00";
            String type="1";
            HttpHelper.getInstance().getVerifyCode(phoneNum,curTime,ip,mac);
        }




    }

    @Override
    public void vertifyCode(String phonenumber, String code) {
         HttpHelper.getInstance().login(phonenumber,code);


    }

    @Override
    public void unRegistSendCodeListener() {

    }
}
