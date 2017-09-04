package com.guaju.sugertea.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * Created by guaju on 2017/9/4.
 */

public class CustomScrollView extends ScrollView {
    ScrollViewListener svl;
    public CustomScrollView(Context context) {
        super(context);
    }
    public interface  ScrollViewListener{
        void onscroll(CustomScrollView csv,int l, int t, int oldl, int oldt);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setScrollViewListener(ScrollViewListener listener){
           this.svl=listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if(svl!=null){
            svl.onscroll(this,l,t,oldl,oldt);
        }
    }
}
