package com.guaju.sugertea.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by guaju on 2017/8/22.
 */

public abstract class BaseFragment extends Fragment {
    private static final String TAG = "BaseFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=initView(inflater);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initData();

    }

    protected abstract void initData();

    //初始化view,让子类实现
    protected abstract  View initView(LayoutInflater inflater);

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: 执行了" );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: 执行了" );
    }
}
