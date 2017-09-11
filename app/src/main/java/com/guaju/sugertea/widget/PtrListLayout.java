package com.guaju.sugertea.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.guaju.sugertea.R;
import com.guaju.sugertea.common.LifeCycle;



/**
 * Created by Sacowiw on 2017/6/8.
 */

public class PtrListLayout extends LoadingLayout implements PtrHandler, LifeCycle {
    PtrFrameLayout ptr;
    LoadMoreRecyclerView rv;
    BaseLoadMoreAdapter adapter;
    int appBarOffset;

    public PtrListLayout(Context context) {
        super(context);
    }

    public PtrListLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PtrListLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected View provideContentView() {
        View view = UIUtil.inflate(R.layout.ptr_list_layout, this);
        ptr = (PtrFrameLayout) view.findViewById(R.id.ptr);
//        PtrDefaultHeader header = new PtrDefaultHeader(getViewContext());
//        ptr.setHeaderView(header);
//        ptr.addPtrUIHandler(header);
        rv = (LoadMoreRecyclerView) view.findViewById(R.id.recycler_view);
        ptr.setPtrHandler(this);
        return view;
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return appBarOffset == 0 && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        rv.refresh();
        if (mListener != null) {
            mListener.onReload();
        }
    }

    @Override
    public void destroy() {
        adapter.destroy();
        rv.destroy();
        rv = null;
        adapter = null;
        removeAllViews();
    }

    //以下为对外暴露接口

    public void setAdapter(BaseLoadMoreAdapter adapter) {
        rv.setLayoutManager(new WrapContentLinearLayoutManager(getContext()));
        this.adapter = adapter;
        rv.setAdapter(adapter);
    }

    public void statusAll() {
        super.statusSuccess();
        ptr.refreshComplete();
        rv.setIsEnd();
    }

    @Override
    public void statusSuccess() {
        super.statusSuccess();
        ptr.refreshComplete();
        rv.setLoadComplete();
    }

    @Override
    public void statusEmpty() {
        super.statusEmpty();
        ptr.refreshComplete();
    }

    @Override
    public void statusError() {
        super.statusError();
        ptr.refreshComplete();
    }

    public RecyclerView getRecyclerView() {
        return rv;
    }


    public PtrFrameLayout getPtr() {
        return ptr;
    }

    /**
     * 是否需要上拉加载功能
     *
     * @param need
     */
    public void setNeedLoadMore(boolean need) {
        rv.setNeedLoadMore(need);
    }

    public void setNeedPtr(boolean need) {
        ptr.setEnabled(need);
    }

    public void setOnLoadMoreListener(LoadMoreRecyclerView.OnLoadMoreListener listener) {
        rv.setOnLoadMoreListener(listener);
    }

    public void clearAdapter(){
        rv.clearAdapter();
    }
    public void setAppBarOffset(int appBarOffset) {
        this.appBarOffset = appBarOffset;
    }
}
