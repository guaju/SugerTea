package com.guaju.sugertea.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.guaju.sugertea.R;

/**
 * Created by guaju on 2017/8/29.
 */

public class ChooseLoginActivity extends Activity implements View.OnClickListener{

    private Button login_choose_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choose);
        initView();
    }

    private void initView() {
        login_choose_phone = (Button) findViewById(R.id.login_choose_phone);
        login_choose_phone.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_choose_phone:
                startActivity(new Intent(ChooseLoginActivity.this,LoginActivity.class));
                //一跳转即销毁
                finish();
                break;
            default:
                break;

        }
    }
}
