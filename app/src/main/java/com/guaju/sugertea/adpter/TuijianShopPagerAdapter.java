package com.guaju.sugertea.adpter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by guaju on 2017/9/1.
 */

public class TuijianShopPagerAdapter extends PagerAdapter {
    private ArrayList<GridView> lists;

    public TuijianShopPagerAdapter(ArrayList<GridView> lists) {
        this.lists = lists;
    }

    @Override
    public int getCount() {
        return lists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (lists.get(position).getParent()!=null){
            ViewGroup parent = (ViewGroup) (lists.get(position).getParent());
            parent.removeView(lists.get(position));
        }
        container.addView(lists.get(position));
        return lists.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
