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
public class MineFragment extends BaseFragment implements View.OnClickListener,MineView {
    private FrameLayout fl_icon;
    private MainActivity activity;
    private MineContract.MinePresenter presenter;
    private View v;
    @Override
    protected void initData() {
    //本生命周期在view创建完成之后调用
        presenter=new MinePresenterImpl(this);
        presenter.hide();
    }
    @Override
    protected View initView(LayoutInflater inflater) {
        v = inflater.inflate(R.layout.fragment_mine, null, false);
        activity = (MainActivity) getActivity();
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
     //隐藏actionbar的逻辑
    @Override
    public void hideActionBar() {
        ActionBar actionBar = activity.getActionBar();
        actionBar.hide();
        int statusBarHeight = activity.statusBarHeight;
        v.setPadding(0,statusBarHeight,0,0);
    }
}
