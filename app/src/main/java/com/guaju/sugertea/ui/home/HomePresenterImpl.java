package com.guaju.sugertea.ui.home;

import android.text.TextUtils;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.guaju.sugertea.adpter.HomeShopAdapter;
import com.guaju.sugertea.app.App;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.dao.bean.DaoSession;
import com.guaju.sugertea.dao.bean.UserInfo;
import com.guaju.sugertea.dao.bean.UserInfoDao;
import com.guaju.sugertea.httputil.HttpHelper;
import com.guaju.sugertea.model.bean.ADBean;
import com.guaju.sugertea.utils.SPUtils;

import java.util.List;

/**
 * Created by guaju on 2017/8/30.
 */

public class HomePresenterImpl implements HomeContract.HomePresenter {
    private HomeContract.HomeView view;
    HomeFragment fragment ;

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
        fragment = (HomeFragment) this.view;
        for (ADBean.ContentBean bean:content){
            DefaultSliderView textSliderView = new DefaultSliderView(fragment.getActivity());
            String url = Constant.IMAGE_AD + bean.getPic();
            textSliderView.image(url);
            slider.addSlider(textSliderView);
        }

    }

    @Override
    public void requestHomeListData(String paixu,String  page) {
        fragment = (HomeFragment) this.view;
        //TODO 获取首页list信息
        DaoSession daoSession = App.getDaoSession();
        //先我们存储的bean的dao 管理者
        UserInfoDao userInfoDao = daoSession.getUserInfoDao();
        SPUtils sp = SPUtils.getInstance(fragment.getActivity(), Constant.SPNAME);
        String phonenum = (String) sp.getSp("phonenum", String.class);
        String location = (String) sp.getSp("location", String.class);

        //传入key
            UserInfo load = userInfoDao.load(phonenum);
            String openId = load.getOpenId();
            HttpHelper.getInstance().getHomeShopList(
                    TextUtils.isEmpty(phonenum)?null:phonenum,
                    paixu,
                    TextUtils.isEmpty(location)?null:location,
                    page);





    }

    @Override
    public void setHomeListAdapter(HomeShopAdapter homeShopAdapter) {
        fragment.rv_list.setAdapter(homeShopAdapter);
    }

    public HomePresenterImpl(HomeContract.HomeView view) {
        this.view = view;
    }
}
