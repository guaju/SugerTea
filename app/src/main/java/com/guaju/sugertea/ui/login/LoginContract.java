package com.guaju.sugertea.ui.login;

/**
 * Created by guaju on 2017/8/25.
 */

public interface LoginContract {
     interface LoginView{
         //发送验证码，或者校验失败的逻辑
         void showError();
         //发送验证码的吐司
         void showSendingToast();
         //验证码验证成功，跳转的toast
         void loginSuccessToast();
         //切换页面的逻辑
         void switchPage();
         //校验手机号失败吐司
         void vertifyPhoneNumError();
     }
     interface LoginPresenter{
         //注册短信验证码监听的逻辑
         void registSendCodeListener();
         //发送短信验证码
         void sendCode(String phoneNum);
         //发送校验请求
         void vertifyCode(String phonenumber,String code);
         //取消注册短信验证码监听
         void unRegistSendCodeListener();
     }

}
