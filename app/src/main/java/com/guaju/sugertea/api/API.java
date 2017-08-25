package com.guaju.sugertea.api;

import android.support.annotation.NonNull;

import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.model.bean.BaseBean;
import com.guaju.sugertea.model.bean.HomeShopBean;
import com.guaju.sugertea.model.bean.LoginBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    /**
     * 登录
     *
     * @param bs
     * @param phone
     * @param sms
     * @param wxOpenId
     * @param unionId
     * @param cid
     * @return
     */
    @FormUrlEncoded
    @POST(Constant.USER)
    Observable<BaseBean<LoginBean>> login(
            @Field("bs") String bs,
            @Field("phone") String phone,
            @Field("sms") String sms,
            @Field("wxopenid") String wxOpenId,
            @Field("unionid") String unionId,
            @Field("cid") String cid,
            @Field("wxname") String wxName,
            @Field("wxtouxiang") String wxAvatar
    );

    /**
     * 获得验证码
     *
     * @param bs
     * @param phone
     * @param ip
     * @param mac
     * @param time
     * @param sign
     * @param type
     * @return
     */
    @GET(Constant.USER)
    Observable<BaseBean> getVerifycode(
            @NonNull @Query("bs") String bs,
            @NonNull @Query("phone") String phone,
            @NonNull @Query("ip") String ip,
            @NonNull @Query("mac") String mac,
            @NonNull @Query("time") long time,
            @NonNull @Query("sign") String sign,
            @NonNull @Query("leixing") String type
    );



}
