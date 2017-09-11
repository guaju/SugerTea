package com.guaju.sugertea.widget;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.guaju.sugertea.common.LifeCycle;


/**
 * Created by lfs on 16/10/25.
 */

public class LoadMoreRecyclerView extends RecyclerView implements LifeCycle {
    private OnLoadMoreListener mLoadMoreListener;
    private final int VISIBLE_THRESHOLD = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private BaseLoadMoreAdapter mAdapter;
    private boolean mIsEnd;//是否全部加载
    private boolean mNeedLoadMore = true;

    private LinearLayoutManager llm;


    public OnScrollListener mScrollListener = new OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //判断是不是加载结束了，或者需不需要刷新
            if (mIsEnd || !mNeedLoadMore) {
                //如果加载结束了或者不需要刷新了就return
                return;
            }
            //拿到总共的条目数
            totalItemCount = llm.getItemCount();
            //拿到最下边的条目位子
            lastVisibleItem = llm.findLastVisibleItemPosition();
            //如果现在没有加载，并且 当前的总共条目小于屏幕最下边这个条目的位置+5  并且 当前总共条目大于5 就开始刷新
            if (!loading && totalItemCount <= (lastVisibleItem + VISIBLE_THRESHOLD)&&totalItemCount>VISIBLE_THRESHOLD) {
                if (mLoadMoreListener != null) {
                    mLoadMoreListener.onLoadMore();
                }
                loading = true;
                mAdapter.setIsLoading(loading);
            }
        }
    };


    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //添加了一个默认的 删除数据 添加数据的条目动画
        setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        init();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        mAdapter = (BaseLoadMoreAdapter) adapter;
    }
    public void clearAdapter(){
        mAdapter=null;
    }

    /**
     * 初始化上拉加载
     */
    private void init() {
        if (getLayoutManager() instanceof LinearLayoutManager) {
            llm = (LinearLayoutManager) getLayoutManager();
            addOnScrollListener(mScrollListener);
        }
    }

    /**
     * 加载完成
     */
    public void setLoadComplete() {
        loading = false;
        if (mAdapter==null){
            super.setAdapter(mAdapter);
        }else{
        mAdapter.setIsLoading(loading);
        mAdapter.notifyDataSetChanged();
        }
    }

    public void refresh() {
        mIsEnd = false;
        loading = true;
        if (mAdapter!=null){
        mAdapter.refresh();
        }
    }

    /**
     * 设置是否有分割线
     *
     * @param have
     */
    public void setHaveDivider(boolean have) {
        if (have) {
//            addItemDecoration(new DividerItemDecoration(getViewContext(), DividerItemDecoration.VERTICAL_LIST, R.drawable.shape_horizontal_divider));
        }
    }

    /**
     * 是否是最后一页
     */
    public void setIsEnd() {
        mIsEnd = true;
        if (mAdapter==null){
            super.setAdapter(mAdapter);
        }else{
        mAdapter.setIsEnd();
        mAdapter.setIsLoading(loading);
        mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 是否需要加载更多功能
     */
    public void setNeedLoadMore(boolean needLoadMore) {
        mNeedLoadMore = needLoadMore;
        mAdapter.setNeedLoadMore(needLoadMore);
    }

    /**
     * 设置加载更多接口
     *
     * @param onLoadMoreListener
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        mLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void destroy() {
        removeOnScrollListener(mScrollListener);
    }


    /**
     * 加载更多接口 ，需要把加载数据的方法写过来
     */
    public static interface OnLoadMoreListener {
        void onLoadMore();//需要把加载数据的方法写过来
    }

}
