package com.guaju.sugertea.ui.main;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.guaju.sugertea.R;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.factory.FragmentFactory;
import com.guaju.sugertea.httputil.HttpHelper;
import com.guaju.sugertea.model.MyQQLocationManager;
import com.guaju.sugertea.model.StatusBarManager;
import com.guaju.sugertea.model.bean.HomeShopBean;
import com.guaju.sugertea.ui.home.HomeFragment;
import com.guaju.sugertea.ui.login.ChooseLoginActivity;
import com.guaju.sugertea.ui.mine.MineFragment;
import com.guaju.sugertea.utils.MeasureUtils;
import com.guaju.sugertea.utils.SPUtils;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class MainActivity extends FragmentActivity implements MainContract.MainView {
    public static final int RB_MINE = 3;
    private static final String TAG = "MainActivity";
    private MainPresenter mainPresenter;
    private TencentLocationManager locationManager;
    private TencentLocationRequest request;
    private TextView tv_location;
    private EditText search;
    private FrameLayout fl_msg;
    public int statusBarHeight;
    private TextView tv;
    //存储点击的那个radiobutton
    private int savedPage = 0;
    private MineFragment mineFragment;
    private HomeFragment homeFragment;
    private FragmentManager fm;
    public TencentLocation tencentLocation;
    private ActionBar actionBar;
    private View customActionbar;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();

        setPresenter(this);
        mainPresenter.mainView.setActionBar(this);
//        mainPresenter.mainView.setStatusBar(this);
        mainPresenter.getLocation(tencentLocation);
        HttpHelper.getInstance().getShopList("104cca5fad614b53e494e5198f4cdb47", "116.125584,40.232219");
        EventBus.getDefault().register(this);
        test();
        switch2Fragment("home");

    }
//切换fragment逻辑
    private void test() {
        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {


            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if ((checkedId == R.id.rb_mine)) {
                    switch2Fragment("mine");
                }
                if ((checkedId == R.id.rb_home)) {
                    switch2Fragment("home");
                }


            }
        });
    }

    private void setPresenter(MainContract.MainView mainView) {
        mainPresenter = new MainPresenter(mainView);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //（处理登录逻辑）切换到之前点击的那个页面
        if (savedPage == RB_MINE ) {
            switch2Fragment("mine");
        }
    }

    //重置fragment的方法
    void resetFragment(){
        FragmentTransaction ft = fm.beginTransaction();
        if (homeFragment!=null){
            ft.hide(homeFragment);
        }
        if (mineFragment!=null){
            ft.hide(mineFragment);
        }
        ft.commit();
    }



    public void switch2Fragment(String name) {

        resetFragment();
        //判断是不是登录状态
        if ((Boolean) SPUtils.getInstance
                (MainActivity.this, Constant.SPNAME)
                .getSp("islogin", Boolean.class)) {
            FragmentTransaction ft = fm.beginTransaction();
            if ("mine".equals(name)) {
                if (mineFragment == null) {
                    mineFragment = (MineFragment) FragmentFactory.getInstance().createMine();
                    ft.add(R.id.fl_content, mineFragment, "mine");
                }
                else{
                    ft.show(mineFragment);
                }
                ft.commit();
                actionBar.hide();
            }
            if ("home".equals(name)) {
                    if (homeFragment == null) {
                        homeFragment = (HomeFragment) FragmentFactory.getInstance().createHome();
                        ft.add(R.id.fl_content, homeFragment, name);

                    }else{
                        ft.show(homeFragment);
                    }
                ft.commit();
                //在回到首页的时候显示actionbar
                actionBar.show();
                } }

             else {
                startActivity(new Intent(MainActivity.this, ChooseLoginActivity.class));
                savedPage = RB_MINE;
            }
    }

        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions,
        int[] grantResults){
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            //可在此继续其他操作。
            if (grantResults[0] != 0 && grantResults[1] != 0 && grantResults[2] != 0) {
                Toast.makeText(this, "请打开定位权限", Toast.LENGTH_SHORT).show();
            } else if (grantResults[0] == 0 && grantResults[1] == 0 && grantResults[2] == 0) {
                int error = locationManager.requestLocationUpdates(request, new MyQQLocationManager.QQLocationListener());
            }

        }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    public void setActionBar(Activity act) {
        actionBar = getActionBar();
        //让actionbar使用自定义的布局样式
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.head);
        customActionbar = LayoutInflater.from(this).inflate(R.layout.head, null, true);
        tv_location = (TextView) customActionbar.findViewById(R.id.tv_location);
        search = (EditText) customActionbar.findViewById(R.id.search);
        fl_msg = (FrameLayout) customActionbar.findViewById(R.id.fl_msg);


//       //1.在代码中设置背景可用
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void showToast22(MyEvent event) {
        Toast.makeText(this, event.bean.getCode() + "hahaha", Toast.LENGTH_SHORT).show();

    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setStatusBar(Activity act) {


        StatusBarManager.setTranStatusBar(act);
        statusBarHeight = MeasureUtils.getStatusBarHeight(this);

    }

    @Override
    public void setLocationText(String address) {
        if (address == null && "".equals(address)) {
            tv_location.setText("");
        } else {
            tv_location.setText(address);
        }
        actionBar.setCustomView(customActionbar);

    }

    public static class MyEvent {
        HomeShopBean bean;

        public MyEvent(HomeShopBean bean) {
            this.bean = bean;
        }

        public void setBean(HomeShopBean bean) {
            this.bean = bean;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
