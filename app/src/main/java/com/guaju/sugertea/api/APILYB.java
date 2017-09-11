package com.guaju.sugertea.api;

import android.support.annotation.NonNull;

import com.guaju.sugertea.model.lybbean.Test;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by guaju on 2017/8/21.
 */

public interface APILYB {

    @POST("checkUser")
    Observable<Test> login(
            @NonNull @Query("uname") String username,
            @NonNull @Query("pass") String pass,
            @NonNull @Query("errornum") String num,
            @NonNull @Query("sign") String sign
    );
}
