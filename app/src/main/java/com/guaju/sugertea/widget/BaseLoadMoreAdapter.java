package com.guaju.sugertea.widget;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dianjiake.android.R;
import com.dianjiake.android.common.LifeCycle;
import com.dianjiake.android.util.CheckEmptyUtil;
import com.dianjiake.android.util.UIUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * RecyclerView 上拉加载更多 Adapter 基类
 * Created by Fesen on 2015/12/3.
 */
public abstract class BaseLoadMoreAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> implements LifeCycle {
    private static int VIEW_PROGRESS = -0x101;
    private static int VIEW_END = -0x102;
    private static int VIEW_NORMAL = -0x003;
    protected List<T> mItems;
    private int mType = VIEW_PROGRESS;
    private RecyclerView.ViewHolder mEndViewHolder;
    private boolean mIsLoading;
    private boolean mIsLoadMore = true;//是否有上拉夹在功能

    private int mLoadAllTextRes;
   //定义一个集合 状态 一些viewholder，装载不同类型的布局（一个页面中的）的viewholder
    List<BaseViewHolder> viewHolders = new ArrayList<>();
    //通过接口回调的方法实现点击事件
    private OnItemClickListener onItemClickListener;

    //构造方法
    public BaseLoadMoreAdapter(List<T> items) {
        super();
        mItems = items;
    }

    @Deprecated
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder vh = null;
        //如果当前的状态属于加载中或者滑动底部的状态时
        if (viewType == VIEW_PROGRESS || viewType == VIEW_END) {
            //返回一个展示进度的viewholder
            vh = ProgressViewHolder.newInstance(parent);
        } else {
            //返回一个自己的viewHolder
            vh = myOnCreateViewHolder(parent, viewType);
        }
        viewHolders.add(vh);
        return vh;
    }

    /**
     * 获得该位置下的item
     *
     * @param positon
     */
    public T getItem(int positon) {
        return positon < mItems.size() ? mItems.get(positon) : null;
    }

    //替换掉对应位置的元素，这的意思是把list集合中对应位置的元素替换掉
    public void setItem(int position, T t) {
        if (position < mItems.size()) {
            mItems.set(position, t);
        }
    }

    /**
     * 删除条目，此动作挺危险的，没什么事就别调用了
     */
    public void deleteItem(int position) {
        if (position < mItems.size()) {
            mItems.remove(position);
            notifyItemRemoved(position);
        }
    }
    //设置是否需要加载更多
    public void setNeedLoadMore(boolean needLoadMore) {
        mIsLoadMore = needLoadMore;
    }

     //如果需要上拉加载更多，就展示所有元素+1，如果不需要就直接是条目数
    @Override
    public int getItemCount() {
        return mIsLoadMore ? mItems.size() + 1 : mItems.size();
    }
    //多布局的适配器方法
    @Deprecated
    @Override
    public int getItemViewType(int position) {
        //如果当前显示的是最后一条，或者没有任何数据的话
        if (mIsLoadMore && (getItemCount() == 1 || position == getItemCount() - 1)) {
            //返回一个进度条
            return mType;
        } else {

            return myGetItemViewType(position);
        }
    }


    @Deprecated
    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (getItemViewType(position) != VIEW_PROGRESS && getItemViewType(position) != VIEW_END) {
            myOnBindViewHolder(holder, position);
        } else {
            //如果是到底了或者是加载中，展示进度条
            ((ProgressViewHolder) holder).setItemViewVisible(mType == VIEW_END || mIsLoading);
            //设置是否到底了
            ((ProgressViewHolder) holder).setIsEnd(mType == VIEW_END, mItems.size());
            //设置加载完成之后的文字id i
            ((ProgressViewHolder) holder).setLoadAllText(mLoadAllTextRes);
        }
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.onItemClickListener = clickListener;
    }
    //提供的方法，让子类去调用，作用就是点击条目触发什么操作
    public void onClick(T t, int position) {
        if (onItemClickListener != null) {
            onItemClickListener.onClick(t, position);
        }
    }
    //解绑binder  并且释放资源
    @Override
    public void destroy() {
        //遍历存储viewholder的集合，释放资源
        if (!CheckEmptyUtil.isEmpty(viewHolders)) {
            for (BaseViewHolder vh : viewHolders) {
                vh.destroy();
            }
        }
        viewHolders.clear();
        viewHolders = null;
    }

    public void setIsEnd() {
        mType = VIEW_END;
    }

    public void refresh() {
        mType = VIEW_PROGRESS;
    }

    /**
     * 是否正在加载
     */
    public void setIsLoading(boolean isLoading) {
        mIsLoading = isLoading;
    }

    /**
     * 全部加载提示文本
     *
     * @param loadAllTextRes
     */
    public void setLoadAllText(@StringRes int loadAllTextRes) {
        mLoadAllTextRes = loadAllTextRes;
    }


    public abstract void myOnBindViewHolder(BaseViewHolder holder, int position);

   //抽象方法，让子类提供一个viewholder
    public abstract BaseViewHolder myOnCreateViewHolder(ViewGroup parent, int viewType);
    //返回对应的type（类型）
    public abstract int myGetItemViewType(int position);


    public static class ProgressViewHolder extends BaseViewHolder {
        View itemView;
        View loadMore;
        View end;
        TextView loadAllTextView;
       //拿到进度条的viewholder
        public static ProgressViewHolder newInstance(ViewGroup parent) {
            return new ProgressViewHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_end_and_load_more, parent, false));

        }
         //进度条viewholder
        private ProgressViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            loadMore = itemView.findViewById(R.id.footer_end_and_load_more_load);
            end = itemView.findViewById(R.id.footer_end_and_load_more_end);
            loadAllTextView = (TextView) itemView.findViewById(R.id.footer_end_and_load_more_end);
        }
       //设置条目是否显示
        private void setItemViewVisible(boolean visible) {
            itemView.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        //设置是否到底部
        private void setIsEnd(boolean isEnd, final int size) {
            loadMore.setVisibility(isEnd ? View.GONE : View.VISIBLE);
            end.setVisibility(isEnd ? View.VISIBLE : View.GONE);
            if (isEnd) {
                end.getLayoutParams().height = size > 9 ? UIUtil.getDimensionPixelSize(R.dimen.toolbar_size) : 0;
                end.postInvalidate();
            }
        }
       //设置完全加载完的文字
        void setLoadAllText(@StringRes int loadAllTextRes) {
            if (loadAllTextRes != 0) {
                loadAllTextView.setText(loadAllTextRes);
            }
        }

        @Override
        public void destroy() {

        }
    }



    //条目点击接口
    public interface OnItemClickListener {
        void onClick(Object t, int position);
    }
}
