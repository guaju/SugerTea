package com.guaju.sugertea.model;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

/**
 * Created by guaju on 2017/8/16.
 */

public class MyQQLocationManager {
    private static MyQQLocationManager myQQLocationManager;
    public static Activity mAct;
    private static final String TAG = "MyQQLocationManager";
    private TencentLocationManager locationManager;
    private TencentLocationRequest request;

    public MyQQLocationManager(Activity act) {
        this.mAct=act;
    }

    public static MyQQLocationManager getInstance(Activity act){
        if (myQQLocationManager==null){
             myQQLocationManager=new MyQQLocationManager(act);
        }
        return myQQLocationManager;
    }


    public static class QQLocationListener implements TencentLocationListener {

        @Override
        public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
            if (TencentLocation.ERROR_OK == i) {
                // 定位成功
                String name = tencentLocation.getName();
                String address = tencentLocation.getAddress();
                Log.e(TAG, "onLocationChanged: " + name + "--" + address);

            } else {
                // 定位失败
            }
        }

        @Override
        public void onStatusUpdate(String s, int i, String s1) {
            Log.e(TAG, "onStatusUpdate: gps开启了||关闭了");
        }

    }

     //使用时先初始化
    private  void initLocation() {
        request = TencentLocationRequest.create();
        request.setInterval(5000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);
        request.setAllowCache(true);
        locationManager = TencentLocationManager.getInstance(mAct);
    }

    private void locate(){
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            if (mAct.checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED)
            {
                mAct.requestPermissions(permissions, 0);
            }
        }else{
            int error = locationManager.requestLocationUpdates(request,new QQLocationListener());
        }

    }

}