package com.guaju.sugertea.httputil;

import android.util.Log;

import com.guaju.sugertea.api.API;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.model.bean.HomeShopBean;
import com.guaju.sugertea.ui.main.MainActivity;

import de.greenrobot.event.EventBus;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static de.greenrobot.event.EventBus.TAG;

/**
 * Created by guaju on 2017/8/21.
 */

public class HttpHelper {
    private static HttpHelper helper=new HttpHelper();
    private Retrofit retrofit;
    private  final API api;

    private HttpHelper(){
        retrofit=new Retrofit.Builder()
                .baseUrl(Constant.ROOT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        api = retrofit.create(API.class);
    }
    public static  HttpHelper getInstance(){
        return    helper;
    }

   /*
    访问商户列表
     */
   public void getShopList(String openId,String location){
       Observable<HomeShopBean> jingxuanshanghu = api.homeShop("jingxuanshanghu", openId, 0, location, 1);
       jingxuanshanghu.subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Action1<HomeShopBean>() {
                   @Override
                   public void call(HomeShopBean homeShopBean) {
                       Log.e(TAG, "call: "+homeShopBean.getCode() );
                       EventBus.getDefault().post(new MainActivity.MyEvent(homeShopBean));
                   }
               });

   }


}
