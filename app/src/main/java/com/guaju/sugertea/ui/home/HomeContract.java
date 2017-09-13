package com.guaju.sugertea.ui.home;

import android.support.v7.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.guaju.sugertea.adpter.HomeShopAdapter;
import com.guaju.sugertea.model.bean.ADBean;
import com.guaju.sugertea.model.bean.TuijianShopBean;

import java.util.ArrayList;
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
        //初始化首页推荐商户的view
        void showVp2(ArrayList<TuijianShopBean.ListBean> list );
        //让recyclerview展示信息
        void  showHomeShopLists(RecyclerView rv);


    }
    interface HomePresenter{

        //获取首页的推荐商户的信息
        void requestTuijianShops();

        //请求广告图片 数据
        void requestADdata(String zuobiao);

       //解析广告数据，展示slider
        void readADdata(List<ADBean.ContentBean> content, SliderLayout slider);
       //拿到首页list信息
        void  requestHomeListData(String paixu,String  page);

        void setHomeListAdapter(HomeShopAdapter homeShopAdapter);
    }


}
