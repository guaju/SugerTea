package com.guaju.sugertea.ui.home;

import com.daimajia.slider.library.SliderLayout;
import com.guaju.sugertea.model.bean.ADBean;

import java.util.List;

/**
 * Created by guaju on 2017/8/30.
 */

public interface HomeContract {
    interface  HomeView{
        //更新viewpager、slider
        void updateSlider();
        //显示网络异常
        void showNetError();


    }
    interface HomePresenter{
        //请求广告图片 数据
        void requestADdata(String zuobiao);

       //解析广告数据，展示slider
        void readADdata(List<ADBean.ContentBean> content, SliderLayout slider);
    }


}
