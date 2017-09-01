package com.guaju.sugertea.ui.home;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.httputil.HttpHelper;
import com.guaju.sugertea.model.bean.ADBean;

import java.util.List;

/**
 * Created by guaju on 2017/8/30.
 */

public class HomePresenterImpl implements HomeContract.HomePresenter {
    private HomeContract.HomeView view;

    @Override
    public void requestTuijianShops() {
        HttpHelper.getInstance().getTuijianShops();
    }

    @Override
    public void requestADdata(String zuobiao) {
        HttpHelper.getInstance().getAD(zuobiao);
    }

    @Override
    public void readADdata(List<ADBean.ContentBean> content, SliderLayout slider) {
        HomeFragment fragment = (HomeFragment) this.view;
        for (ADBean.ContentBean bean:content){
            DefaultSliderView textSliderView = new DefaultSliderView(fragment.getActivity());
            String url = Constant.IMAGE_AD + bean.getPic();
            textSliderView.image(url);
            slider.addSlider(textSliderView);
        }

    }

    public HomePresenterImpl(HomeContract.HomeView view) {
        this.view = view;
    }
}
