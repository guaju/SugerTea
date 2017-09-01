package com.guaju.sugertea.adpter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guaju.sugertea.R;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.model.bean.TuijianShopBean;

import java.util.ArrayList;

/**
 * Created by guaju on 2017/9/1.
 */

public class HomeItemGvAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<TuijianShopBean.ListBean> list;

    public HomeItemGvAdapter(Context context, ArrayList<TuijianShopBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.item_tuijian_shop, null);
        ViewHolder viewHolder = new ViewHolder(v);
        TuijianShopBean.ListBean listBean = list.get(position);
        Glide.with(context).load(Constant.IMAGE_SHOP+listBean.getTubiao()).into(viewHolder.iv);
        viewHolder.tv.setText(listBean.getMingcheng());
        return v;
    }

    class ViewHolder{
        ImageView iv;
        TextView tv;
        ViewHolder(View v){
          iv = (ImageView) v.findViewById(R.id.iv);
          tv=(TextView)v.findViewById(R.id.tv);
      }

    }
}
