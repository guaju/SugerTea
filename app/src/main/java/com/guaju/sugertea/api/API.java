package com.guaju.sugertea.api;

import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.model.bean.HomeShopBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by guaju on 2017/8/21.
 */

public interface API {
    /**
     * 首页店铺列表
     *
     * @param bs
     * @param openid
     * @param order      （0默认 2距离 1评分）
     * @param coordinate
     * @param page
     * @return
     */
    @GET(Constant.SHOP)
    Observable<HomeShopBean> homeShop(
            @Query("bs") String bs,
            @Query("openid") String openid,
            @Query("paixu") int order,
            @Query("zuobiao") String coordinate,
            @Query("page") int page
    );

//    /**
//     * @param bs         {@link BSConstant#SEARCH_SHOP}
//     * @param openid
//     * @param keyword
//     * @param order
//     * @param coordinate
//     * @param page
//     * @return
//     */
//    @GET(Constant.SHOP)
//    Observable<BaseListBean<HomeShopBean>> searchShop(
//            @Query("bs") String bs,
//            @Query("openid") String openid,
//            @Query("guanjianci") String keyword,
//            @Query("paixu") int order,
//            @Query("zuobiao") String coordinate,
//            @Query("page") int page
//    );



}
