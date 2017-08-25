package com.guaju.sugertea.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lfs on 2017/5/15.
 */

public class BaseBean<T> {
    private int code;
    private T obj;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

}
