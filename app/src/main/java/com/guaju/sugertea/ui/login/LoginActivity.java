package com.guaju.sugertea.ui.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guaju.sugertea.R;

import cn.smssdk.EventHandler;
import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by guaju on 2017/8/23.
 */

public class LoginActivity  extends AppCompatActivity implements LoginContract.LoginView {
    private static final String TAG = "LoginActivity";
    private LoginContract.LoginPresenter loginPresenter;
    private String phonenumber;
    private TextView password_button;
    private EventHandler eventHandler;
    private EditText password_input,phone_input;
    private Button sign_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);
        loginPresenter=new LoginPresenterImpl2(this);
        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
        //SMSSDK.setAskPermisionOnReadContact(boolShowInDialog) ；
        //注册短信验证码监听
//        loginPresenter.registSendCodeListener();
        initView();


    }
    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        password_button = (TextView)findViewById(R.id.password_button);
        password_input = (EditText) findViewById(R.id.password_input);
        phone_input = (EditText) findViewById(R.id.phone_input);
        sign_button = (Button) findViewById(R.id.sign_button);
        //给文字加点击事件，让服务器发送验证码
        password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phonenumber = phone_input.getText().toString().trim();
               //发送短信验证码
                loginPresenter.sendCode(phonenumber);
            }
        });
        sign_button.setOnClickListener(new View.OnClickListener() {
            private String verificationCode;
            @Override
            public void onClick(View v) {
                //再次拿到edittext里面的文字
                phonenumber = phone_input.getText().toString().trim();
                verificationCode = password_input.getText().toString().trim();
                 //校验
//                loginPresenter.vertifyCode(phonenumber,verificationCode);
                loginPresenter.vertifyCode(phonenumber,verificationCode);

                //调回到MainActivity

            }
        });

    }

    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
//        loginPresenter.unRegistSendCodeListener();

    }

    @Override
    public void showError() {
        showToast("验证码异常。。。请稍后重试");

    }

    private void showToast(final String info) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, info, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void showSendingToast() {
      showToast("验证码发送中");
    }

    @Override
    public void loginSuccessToast() {
        showToast("登录成功，正在跳转");

    }

    @Override
    public void switchPage() {
        //切换页面
    }

    @Override
    public void vertifyPhoneNumError() {
        Toast.makeText(LoginActivity.this, "请重新填写您的手机号", Toast.LENGTH_SHORT).show();

    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void finishActivity(FinishEvent event){
        //销毁当前activity
        finish();
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void loginEvent(String info) {
        Toast.makeText(this, info, Toast.LENGTH_SHORT).show();
    }
    public static class FinishEvent{
      public  String  flag="finish";

    }
}
