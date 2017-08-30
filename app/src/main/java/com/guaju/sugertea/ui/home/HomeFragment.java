package com.guaju.sugertea.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.guaju.sugertea.R;
import com.guaju.sugertea.base.BaseFragment;
import com.guaju.sugertea.model.bean.ADBean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by guaju on 2017/8/30.
 */

public class HomeFragment extends BaseFragment implements HomeContract.HomeView {

    private SliderLayout slider;
    private View v;
    private HomeContract.HomePresenter presenter;

    @Override
    protected void initData() {
    }

    @Override
    protected View initView(LayoutInflater inflater) {
        EventBus.getDefault().register(this);
        presenter=new HomePresenterImpl(this);
        v = inflater.inflate(R.layout.fragment_home,null,false);
        slider = (SliderLayout) v.findViewById(R.id.slider);
        return v;
    }
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void  updateVP(List<ADBean.ContentBean> content){
        presenter.readADdata(content,slider);
        updateSlider();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void  updateVP(HashMap<Double,Double> map){
        //读map的数据
        Set<Map.Entry<Double, Double>> entries = map.entrySet();
        //拿到第一条记录 ，因为只有一条
        Map.Entry<Double, Double> next = entries.iterator().next();

        presenter.requestADdata(next.getKey()+","+next.getValue());
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void showError(HomeFragment homeFragment){
         showNetError();
    }

    @Override
    public void updateSlider() {
        //开始自动轮播
        slider.startAutoCycle();

    }

    @Override
    public void showNetError() {
        Toast.makeText(getActivity(), "网络异常，请检查您的网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
