package com.guaju.permissiontest;

import android.Manifest;
import android.os.Build;
import android.view.View;
import android.widget.Toast;

import com.guaju.permissiontest.base.BaseActivity;

public class MainActivity extends BaseActivity {





   public void call(View v){
       /**
        * 1.先检查到底有没有打电话权限
        * 2.如果没有，那么去申请打电话权限 ，如果有直接打电话
        * 3.写一个申请权限的回调
        * 4.在申请失败的回调中去提示用户手动开启权限，如果申请成功就直接打电话
        */
      //拿到当前打电话的权限的状态
       //如果sdk 版本小于23的话 不需要做这个判断和申请
       if (Build.VERSION.SDK_INT>=23){
       ifHasPermission(Manifest.permission.CALL_PHONE,Constants.CALLPHONE_REQUEST);
       } else{
           callPhone();
       }


   }

    @Override
    public void dosomething() {
        callPhone();
    }

    private void callPhone() {
        Toast.makeText(this, "我要打电话了~~~", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void initData() {

    }

    @Override
    public void initView() {
        setContentView(R.layout.activity_main);
    }



}
