package com.guaju.sugertea.ui.main;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.guaju.sugertea.R;
import com.guaju.sugertea.model.MyQQLocationManager;
import com.guaju.sugertea.model.StatusBarManager;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

public class MainActivity extends Activity implements MainContract.MainView{
    private static final String TAG = "MainActivity";
    private MainPresenter mainPresenter;
    private TencentLocationManager locationManager;
    private TencentLocationRequest request;
    private TextView tv_location;
    private EditText search;
    private FrameLayout fl_msg;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);
        setPresenter(this);
        mainPresenter.mainView.setActionBar(this);
        mainPresenter.mainView.setStatusBar(this);

    }
    private void setPresenter(MainContract.MainView mainView){
        mainPresenter=new MainPresenter(mainView);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if (grantResults[0]!=0&&grantResults[1]!=0&&grantResults[2]!=0){
            Toast.makeText(this, "请打开定位权限", Toast.LENGTH_SHORT).show();
        } else if (grantResults[0]==0&&grantResults[1]==0&&grantResults[2]==0){
            int error = locationManager.requestLocationUpdates(request, new MyQQLocationManager.QQLocationListener());
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void setActionBar(Activity act) {
        ActionBar actionBar = getActionBar();
        //让actionbar使用自定义的布局样式
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.head);
        View view = LayoutInflater.from(this).inflate(R.layout.head, null, false);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        search = (EditText) view.findViewById(R.id.search);
        fl_msg = (FrameLayout) view.findViewById(R.id.fl_msg);

//       //1.在代码中设置背景可用
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setStatusBar(Activity act) {
        StatusBarManager.setTranStatusBar(act);
    }

    @Override
    public void setLocationText(String address) {
        if (address==null&&"".equals(address)){
            tv_location.setText("定位中...");
        }else{
            tv_location.setText(address);
        }
    }
}
