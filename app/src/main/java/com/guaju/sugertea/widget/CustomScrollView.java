package com.guaju.sugertea.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.Toast;

/**
 * Created by guaju on 2017/9/4.
 */

public class CustomScrollView extends ScrollView {

    private final static int SCROLL_DURATION = 400;
    private final static float OFFSET_RADIO = 1.8f;
    private int headerHeight = 0;
    private boolean enableRefresh = true;
    private boolean refreshing = false;
    private int lastY;
    private Scroller scroller = null;
    private OnRefreshScrollViewListener listener = null;
    private LinearLayout scrollContainer = null;
    private ScrollViewHeader headerView = null;
    ScrollViewListener svl;
    public CustomScrollView(Context context) {
        super(context);
        if (!isInEditMode()) {
            initView(context);
        }
    }
    public interface  ScrollViewListener{
        void onscroll(CustomScrollView csv,int l, int t, int oldl, int oldt);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initView(context);
        }
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

    /**
     * 初始化view
     */
    private void initView(Context context) {
        scroller = new Scroller(context);
        headerView = new ScrollViewHeader(context);
        LinearLayout.LayoutParams headerViewParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //scrollview只允许嵌套一个子布局
        scrollContainer = new LinearLayout(context);
        scrollContainer.addView(headerView, headerViewParams);
        scrollContainer.setOrientation(LinearLayout.VERTICAL);
        addView(scrollContainer);
        //提前获取headerView的高度
        headerView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    @SuppressWarnings("deprecation")
                    @Override
                    public void onGlobalLayout() {
                        // TODO Auto-generated method stub
                        headerHeight = headerView.getHeight();
                        headerView.updateMargin(-headerHeight);
                        headerView.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
    }

    /**
     * 设置内容区域
     *
     * @param context
     * @param
     */
    public void setupContainer(Context context, View containerView) {
        scrollContainer.addView(containerView);
    }

    /**
     * 设置scroll是否可以刷新
     *
     * @param enableRefresh
     */
    public void setEnableRefresh(boolean enableRefresh) {
        this.enableRefresh = enableRefresh;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int deltY = (int) (ev.getY() - lastY);
                lastY = (int) ev.getY();
//                Logger.d("getScrollY:" + getScrollY());
                if (getScrollY() == 0
                        && (deltY > 0 || headerView.getTopMargin() > -headerHeight)) {
                    updateHeader(deltY/OFFSET_RADIO);
                    return true;
                }
                break;
            default:
                //这里没有使用action_up的原因是，可能会受到viewpager的影响接收到action_cacel事件
//                Logger.d("ev.getAction: " +ev.getAction());
                if (getScrollY() == 0) {
//                    Logger.d("topMargin():" + headerView.getTopMargin());
                    if (headerView.getTopMargin() > 0 && enableRefresh && !refreshing) {
                        refreshing = true;
                        headerView.setState(ScrollViewHeader.STATE_REFRESHING);
                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                // TODO Auto-generated method stub
                                if(listener != null) {
                                    listener.onRefresh();
                                    refreshing = false;
                                    Toast.makeText(getContext(),"更新成功", Toast.LENGTH_SHORT).show();
                                    resetHeaderView();
                                }
                            }
                        }, 3000);
                    }
//                    Logger.d("resetHeaderView...");
                    resetHeaderView();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 更新headerview的高度,同时更改状态
     *
     * @param deltY
     */
    public void updateHeader(float deltY) {
        int currentMargin = (int) (headerView.getTopMargin() + deltY);
        headerView.updateMargin(currentMargin);
        if(enableRefresh && !refreshing) {
            if (currentMargin > 0) {
                headerView.setState(ScrollViewHeader.STATE_READY);
            } else {
                headerView.setState(ScrollViewHeader.STATE_NORMAL);
            }
        }
    }

    /**
     * 重置headerview的高度
     */
    public void resetHeaderView() {
        int margin = headerView.getTopMargin();
        if(margin == -headerHeight) {
            return ;
        }
        if(margin < 0 && refreshing) {
            //当前已经在刷新，又重新进行拖动,但未拖满,不进行操作
            return ;
        }
        int finalMargin = 0;
        if(margin <= 0 && !refreshing) {
            finalMargin = headerHeight;
        }
//        Logger.d("margin: " + margin);
//        Logger.d("finalMargin: " + finalMargin);
        //松开刷新，或者下拉刷新，又松手，没有触发刷新
        scroller.startScroll(0, -margin, 0, finalMargin + margin, SCROLL_DURATION);

        invalidate();
    }

    /**
     * 开始刷新
     */
    public void startRefresh() {
        refreshing = true;
        headerView.setState(ScrollViewHeader.STATE_REFRESHING);
        if(listener != null) {
//            Logger.d("xxx: " + headerHeight);
            scroller.startScroll(0, 0, 0, headerHeight, SCROLL_DURATION);
            invalidate();
            listener.onRefresh();
        }
    }

    /**
     * 停止刷新
     */
    public void stopRefresh() {
        if(refreshing) {
            refreshing = false;
            resetHeaderView();
        }
    }

    @Override
    public void computeScroll() {
        // TODO Auto-generated method stub
        if(scroller.computeScrollOffset()) {
//            Logger.d("getCurrY: " + scroller.getCurrY());
            headerView.updateMargin(-scroller.getCurrY());
            //继续重绘
            postInvalidate();
        }
        super.computeScroll();
    }

    public void setOnRefreshScrollViewListener(OnRefreshScrollViewListener listener) {
        this.listener = listener;
    }

    public interface OnRefreshScrollViewListener {
        public void onRefresh();
    }
}
