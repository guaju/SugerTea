package com.guaju.sugertea.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.guaju.sugertea.R;
import com.guaju.sugertea.constant.Constant;
import com.guaju.sugertea.model.bean.HomeShopListBean;

import java.util.ArrayList;


/**
 * Created by guaju on 2017/9/13.
 */

public class HomeShopAdapter extends RecyclerView.Adapter<HomeShopAdapter.MyViewHolder> {
    private static final String TAG = "HomeShopAdapter";
    Context context;
    ArrayList<HomeShopListBean.ListBean> lists;
    public HomeShopAdapter(Context context, ArrayList<HomeShopListBean.ListBean> lists) {
        this.context = context;
        this.lists = lists;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_home_shop, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
              holder.setContent(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public  class MyViewHolder extends RecyclerView.ViewHolder{
     ImageView iv_homeshop;
     TextView tv_title_homeshop,tv_desc_homeshop,tv_cuxiao_homeshop,tv_quan_homeshop,tv_distance_homeshop;
     RatingBar ratingbar_homeshop;
     LinearLayout ll_cuxiao_homeshop,ll_quan_homeshop;

     public MyViewHolder(View itemView) {
         super(itemView);
         iv_homeshop=(ImageView)itemView.findViewById(R.id.iv_homeshop);
         tv_title_homeshop=(TextView)itemView.findViewById(R.id.tv_title_homeshop);
         tv_desc_homeshop=(TextView)itemView.findViewById(R.id.tv_desc_homeshop);
         tv_cuxiao_homeshop=(TextView)itemView.findViewById(R.id.tv_cuxiao_homeshop);
         tv_distance_homeshop=(TextView)itemView.findViewById(R.id.tv_distance_homeshop);
         tv_quan_homeshop=(TextView)itemView.findViewById(R.id.tv_quan_homeshop);
         ratingbar_homeshop=(RatingBar)itemView.findViewById(R.id.ratingbar_homeshop);
         ll_cuxiao_homeshop=(LinearLayout)itemView.findViewById(R.id.ll_cuxiao_homeshop);
         ll_quan_homeshop=(LinearLayout)itemView.findViewById(R.id.ll_quan_homeshop);

     }
     //专门提供设置内容的方法
     public  void  setContent(HomeShopListBean.ListBean bean){
         //图片设置
         Glide.with(context).load(Constant.IMAGE_SHOP+bean.getLogo()).into(iv_homeshop);
         tv_title_homeshop.setText(bean.getMingcheng());
         tv_desc_homeshop.setText(bean.getJianjie());
         int juli = bean.getJuli();
         float fJuli = juli * 0.001f;
         tv_distance_homeshop.setText(fJuli+"km");
         Log.e(TAG, "setContent: "+fJuli );
         ratingbar_homeshop.setRating(Integer.parseInt(bean.getPingji()));
         int cuxiao = bean.getCuxiao();
         if (cuxiao==0){
             //说明没有促销
             ll_cuxiao_homeshop.setVisibility(View.GONE);
         }else if(cuxiao==1){
             ll_cuxiao_homeshop.setVisibility(View.VISIBLE);
             tv_cuxiao_homeshop.setText(bean.getCuxiaofuwumingcheng());
         }
         //TODO 万一有两个就有异常
         int gongkaikaquan = bean.getGongkaikaquan();
         if (gongkaikaquan==0){
             //说明没有促销
             ll_quan_homeshop.setVisibility(View.GONE);
         }else if(gongkaikaquan==1){
             ll_quan_homeshop.setVisibility(View.VISIBLE);
             tv_quan_homeshop.setText(bean.getKaquanmingcheng());
         }
     }
 }

}
