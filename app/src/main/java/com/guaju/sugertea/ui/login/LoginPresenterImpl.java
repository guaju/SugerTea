package com.guaju.sugertea.ui.login;

import android.os.Handler;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by guaju on 2017/8/25.
 */

public class LoginPresenterImpl implements LoginContract.LoginPresenter {
    private LoginContract.LoginView loginView;
    private EventHandler eventHandler;

    public LoginPresenterImpl(LoginContract.LoginView loginView) {
        this.loginView = loginView;

    }

    @Override
    public void registSendCodeListener() {
        // 创建EventHandler对象
        // 处理你自己的逻辑
        //失败的逻辑
        //展示错误信息
        // 处理你自己的逻辑
        //发送验证码吐司
        //说明已经校验成功
        //校验成功事件
        eventHandler = new EventHandler() {

            private Handler handler;

            public void afterEvent(int event, int result, Object data) {
                //失败的逻辑
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    String msg = throwable.getMessage();
                    //展示错误信息
                    loginView.showError();
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                        //发送验证码吐司
                        loginView.showSendingToast();
                    }
                    //说明已经校验成功
                    if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        //校验成功事件
                        loginView.loginSuccessToast();
                        loginView.switchPage();
                    }
                }

            }
        };
        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void sendCode(String phonenumber) {

        String regExp = "^(1[3,4,5,7,8][0-9]\\d{8})$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(phonenumber);

        if ((!TextUtils.isEmpty(phonenumber))&&m.matches()){
            SMSSDK.getVerificationCode("86",phonenumber);
        }else{
            //提示用户手机号校验失败
            loginView.vertifyPhoneNumError();
        }

    }

    @Override
    public void vertifyCode(String phonenumber,String code) {
        if (!TextUtils.isEmpty(code)){
            SMSSDK.submitVerificationCode("86",phonenumber,code);
        }
    }

    @Override
    public void unRegistSendCodeListener() {
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
