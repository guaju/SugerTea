package com.guaju.sugertea.ui.mine;

import android.app.ActionBar;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.guaju.sugertea.R;
import com.guaju.sugertea.base.BaseFragment;
import com.guaju.sugertea.ui.login.LoginActivity;
import com.guaju.sugertea.ui.main.MainActivity;

/**
 * Created by guaju on 2017/8/22.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private FrameLayout fl_icon;

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater) {
        View v = inflater.inflate(R.layout.fragment_mine, null, false);
        MainActivity activity = (MainActivity) getActivity();
        ActionBar actionBar = activity.getActionBar();
        actionBar.hide();
        int statusBarHeight = activity.statusBarHeight;
        v.setPadding(0,statusBarHeight,0,0);
        fl_icon = (FrameLayout) v.findViewById(R.id.fl_icon);
        fl_icon.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fl_icon:
                if ("未登录".equals("未登录")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;
            default:
                break;
        }

    }
}
