package com.guaju.sugertea;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

public class MainActivity extends Activity implements TencentLocationListener{
    private static final String TAG = "MainActivity";

    private TencentLocationManager locationManager;
    private TencentLocationRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getActionBar();
        actionBar.hide();
        actionBar.setCustomView(R.layout.head);
//        actionBar.show();
        initLocation();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            String[] permissions = {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            };

            if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(permissions, 0);
            }
        }else{
            int error = locationManager.requestLocationUpdates(request, this);
        }

    }

    private void initLocation() {
        request = TencentLocationRequest.create();
        request.setInterval(5000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_NAME);
        request.setAllowCache(true);


        locationManager = TencentLocationManager.getInstance(this);


    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        if (TencentLocation.ERROR_OK == i) {
            // 定位成功
            String name = tencentLocation.getName();
            String address = tencentLocation.getAddress();
            Log.e(TAG, "onLocationChanged: "+name+"--"+address );

        } else {
            // 定位失败
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        Log.e(TAG, "onStatusUpdate: gps开启了||关闭了" );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if (grantResults[0]!=0&&grantResults[1]!=0&&grantResults[2]!=0){
            Toast.makeText(this, "请打开定位权限", Toast.LENGTH_SHORT).show();
        } else if (grantResults[0]==0&&grantResults[1]==0&&grantResults[2]==0){
            int error = locationManager.requestLocationUpdates(request, this);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(this);
    }
}
