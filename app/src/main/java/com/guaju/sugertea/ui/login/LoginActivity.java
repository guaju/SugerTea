package com.guaju.sugertea.ui.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guaju.sugertea.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by guaju on 2017/8/23.
 */

public class LoginActivity  extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private String phonenumber;
    private TextView password_button;
    private EventHandler eventHandler;
    private EditText password_input,phone_input;
    private Button sign_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 如果希望在读取通信录的时候提示用户，可以添加下面的代码，并且必须在其他代码调用之前，否则不起作用；如果没这个需求，可以不加这行代码
//        SMSSDK.setAskPermisionOnReadContact(boolShowInDialog) ；
        initView();
        setMsm();


    }

    private void setMsm() {
        // 创建EventHandler对象
        // 处理你自己的逻辑
        eventHandler = new EventHandler() {

            private Handler handler;

            public void afterEvent(int event, int result, Object data) {
                if (data instanceof Throwable) {
                    Throwable throwable = (Throwable)data;
                    String msg = throwable.getMessage();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginActivity.this, "错误", Toast.LENGTH_SHORT).show();
                        } });
                } else {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        // 处理你自己的逻辑
                        handler = new Handler(getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                        Toast.makeText(LoginActivity.this,"请求验证码中。。。" , Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    //说明已经校验成功
                    if(event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                        //登录进去了就
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this,"登录中。。。" , Toast.LENGTH_SHORT).show();
                                //页面跳转
                            }
                        });
                    }
                }

            }
        };

        // 注册监听器
        SMSSDK.registerEventHandler(eventHandler);
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
                String regExp = "^(1[3,4,5,7,8][0-9]\\d{8})$";
                Pattern p = Pattern.compile(regExp);
                Matcher m = p.matcher(phonenumber);

                if ((!TextUtils.isEmpty(phonenumber))&&m.matches()){
                SMSSDK.getVerificationCode("86",phonenumber);
                }else{
                    Toast.makeText(LoginActivity.this, "请重新填写您的手机号", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sign_button.setOnClickListener(new View.OnClickListener() {

            private String verificationCode;

            @Override
            public void onClick(View v) {
                verificationCode = password_input.getText().toString().trim();
                if (!TextUtils.isEmpty(verificationCode)){
                      SMSSDK.submitVerificationCode("86",phonenumber,verificationCode);
                }
            }
        });

    }

    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
