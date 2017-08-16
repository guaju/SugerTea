package com.guaju.sugertea.utils;

import android.content.Context;

/**
 * Created by guaju on 2017/8/16.
 */

public class MeasureUtils {

    //测量状态栏高度
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelSize(resId);
        }
        return result;
    }

}
