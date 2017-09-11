package com.guaju.permissiontest;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by guaju on 2017/9/11.
 */

public class PermissionActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void call(View v){
        /**
         * 1.判断有没有
         * 2.如果没有，请求，如果有就做操作
         * 3.写回调
         * 4.如果请求成功了就做操作，如果没有就弹提示（
         * 给一个dialog,上边有一个跳转到设置页面的按钮
         * 一般来说，写个吐司就行了）
         */

            //请求打电话权限
        PermissionGen.with(this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.CALL_PHONE)
                .request();


    }
    private void callPhone() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        Uri parse = Uri.parse("tel:10086");
        intent.setData(parse);
        startActivity(intent);
    }

    @PermissionSuccess(requestCode = 100)
    public void doSomething(){
        Toast.makeText(this, "Contact permission is granted", Toast.LENGTH_SHORT).show();
         callPhone();
    }

    @PermissionFail(requestCode = 100)
    public void doFailSomething(){
        Toast.makeText(this, "Contact permission is not granted失败了aaaaa", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //回调
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
