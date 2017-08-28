package com.guaju.sugertea.ui.main;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.guaju.sugertea.R;
import com.guaju.sugertea.httputil.HttpHelper;
import com.guaju.sugertea.model.MyQQLocationManager;
import com.guaju.sugertea.model.StatusBarManager;
import com.guaju.sugertea.model.bean.HomeShopBean;
import com.guaju.sugertea.ui.mine.MineFragment;
import com.guaju.sugertea.utils.MeasureUtils;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends FragmentActivity implements MainContract.MainView{
    private static final String TAG = "MainActivity";
    private MainPresenter mainPresenter;
    private TencentLocationManager locationManager;
    private TencentLocationRequest request;
    private TextView tv_location;
    private EditText search;
    private FrameLayout fl_msg;
    public int statusBarHeight;
    private TextView tv;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.tv);
        setPresenter(this);
        mainPresenter.mainView.setActionBar(this);
        mainPresenter.mainView.setStatusBar(this);
        HttpHelper.getInstance().getShopList("104cca5fad614b53e494e5198f4cdb47", "116.125584,40.232219");
        EventBus.getDefault().register(this);
        test();

    }

    private void test() {
        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
               if (checkedId==R.id.rb_mine){
                   FragmentManager fm = getSupportFragmentManager();
                   FragmentTransaction ft = fm.beginTransaction();
                   MineFragment mineFragment = new MineFragment();
                   ft.add(R.id.fl_content,mineFragment,"mine");
                   ft.commit();
               }

            }
        });
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
    @Subscribe(threadMode=ThreadMode.MainThread)
    public void showToast22(MyEvent event){
        Toast.makeText(this, event.bean.getCode()+"hahaha", Toast.LENGTH_SHORT).show();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setStatusBar(Activity act) {


        StatusBarManager.setTranStatusBar(act);
        statusBarHeight = MeasureUtils.getStatusBarHeight(this);

    }

    @Override
    public void setLocationText(String address) {
        if (address==null&&"".equals(address)){
            tv_location.setText("定位中...");
        }else{
            tv_location.setText(address);
        }
    }

    public static class MyEvent{
        HomeShopBean bean;

        public MyEvent(HomeShopBean bean) {
            this.bean = bean;
        }

        public void setBean(HomeShopBean bean){
            this.bean=bean;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
