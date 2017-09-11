package com.guaju.sugertea.ui.home;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.guaju.sugertea.R;
import com.guaju.sugertea.adpter.HomeItemGvAdapter;
import com.guaju.sugertea.adpter.TuijianShopPagerAdapter;
import com.guaju.sugertea.base.BaseFragment;
import com.guaju.sugertea.model.bean.ADBean;
import com.guaju.sugertea.model.bean.TuijianShopBean;
import com.guaju.sugertea.ui.main.MainActivity;
import com.guaju.sugertea.widget.CustomScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

/**
 * Created by guaju on 2017/8/30.
 */

public class HomeFragment extends BaseFragment implements HomeContract.HomeView {
    private static final String TAG = "HomeFragment";
    private static final int SCROLLTAGDELAY = 888;
    //指定一个专门scroll事件的标记值
    int SCROLLTAG = 999;
    private SliderLayout slider;
    private ViewPager vp;
    private View v;
    private HomeContract.HomePresenter presenter;
    private GridView gridview;
    //定义一个装载gridview的集合
    private ArrayList<GridView> gridViews = new ArrayList<>();
    private GridView item_gridview;
    private ImageView vp1;
    private ImageView vp2;
    private View menu_dong;
    private View menu_jing;
    private CustomScrollView ptrsv;
    //定义两个int[]类型的参数，来存储动静menu的坐标
    int locationdong[] = new int[2]; //第一个数保存的是view距离屏幕坐标的值，第二个是距离顶部的值
    int locationjing[] = new int[2];
    int scrolllefttop[] = new int[2];
    //定义变量 检查scrollview是否处于停止状态
    float touchY = 0;


    //为滚动的操作添加handler
    private Handler mScrollHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (SCROLLTAG == msg.what) {
                    Log.e(TAG, "handleMessage: touchy"+touchY+"--scrolly"+ptrsv.getScrollY());
                if (touchY!=ptrsv.getScrollY()){
                    //说明惯性事件发生，手指猛滑会发生
                   return;
                }
                 /*
                如果用的是scrollview可以用这个
                 */
//                拿到动静menu的坐标
                menu_dong.getLocationOnScreen(locationdong);
                menu_jing.getLocationOnScreen(locationjing);
//                判断view距离顶部的值
                if (locationdong[1] > locationjing[1]) {
                    menu_jing.setVisibility(View.GONE);
                } else {
                    menu_jing.setVisibility(View.VISIBLE);
                }

                //__________________________________________________________________________________
            }
            if (SCROLLTAGDELAY == msg.what){
                //此判断仅仅是避免多次执行这里面的操作
                if (touchY!=ptrsv.getScrollY()){
                    menu_dong.getLocationOnScreen(locationdong);
                    menu_jing.getLocationOnScreen(locationjing);
                    if (locationdong[1] > locationjing[1]) {
                        menu_jing.setVisibility(View.GONE);
                    } else {
                        menu_jing.setVisibility(View.VISIBLE);
                    }
                }
            }

        }
    };
    private MainActivity ma;
    private int downY;
    private int moveY;
    private int upY;
    private FrameLayout fl;
    private int vpheight;
    private View fragment_home_home;


    @Override
    protected void initData() {
        //请求数据
        presenter.requestTuijianShops();

    }

    @Override
    protected View initView(LayoutInflater inflater) {

        EventBus.getDefault().register(this);
        presenter = new HomePresenterImpl(this);
        v = inflater.inflate(R.layout.fragment_home, null, false);
        fragment_home_home = inflater.inflate(R.layout.fragment_home_content, null, false);
        fl = (FrameLayout) v.findViewById(R.id.fl);
        //找到scrollview
        ptrsv = (CustomScrollView) v.findViewById(R.id.ptrsv);
        vp1 = (ImageView) fragment_home_home.findViewById(R.id.vp1);
        vp2 = (ImageView) fragment_home_home.findViewById(R.id.vp2);
        //设置内容
        ptrsv.setupContainer(getActivity(),fragment_home_home);

        //两个菜单view
        menu_dong = v.findViewById(R.id.menu_dong);
        menu_jing = v.findViewById(R.id.menu_jing);
        vpheight = getResources().getDimensionPixelSize(R.dimen.viewpagerheightplus);
        ma = (MainActivity) getActivity();
        //给scrollview添加监听
        initScrollViewListener();
        slider = (SliderLayout) v.findViewById(R.id.slider);
        vp = (ViewPager) v.findViewById(R.id.vp);
        return v;
    }

    //为scrollview添加监听事件
    private void initScrollViewListener() {
        ptrsv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当手指滑动或者当手指抬起的时候给handler发送事件，让handler处理滚动的逻辑
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    //记录当时的scrolly值 ，不会出现惯性的事件
                    touchY=ptrsv.getScrollY();
                    mScrollHandler.sendEmptyMessage(SCROLLTAG);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //当我up的时候touchY就不变了 ，只有当up的时候才会出现惯性事件
                    touchY=ptrsv.getScrollY();
                    mScrollHandler.sendEmptyMessage(SCROLLTAG);
                    //可能会有flying动作
                }
                //不管move还是up都延迟发送消息，并且消息是另外一个what值
                mScrollHandler.sendEmptyMessageDelayed(SCROLLTAGDELAY,100);
                return false;
            }
        });
        //接口回调
        //5.让用户传入接口
        //背景渐变效果
        ptrsv.setScrollViewListener(new CustomScrollView.ScrollViewListener() {
            @Override
            public void onscroll(CustomScrollView csv,int t) {
                    Log.e(TAG, "onscroll:t的值 "+t );
                if (t <= 0) {
                    // 只是layout背景透明(仿知乎滑动效果)
                    ma.customActionbar.setBackgroundColor(Color.argb(0,255, 255, 255));
                    //添加条目

                } else{
                    //大于某值得时候就需要设置透明度

                    float scale = (float) t / vpheight;  //透明度按照比例去设置
                    float alpha = (255 * scale);  //算出当前的透明度值
                        Log.e(TAG, "onscroll: hehe"+t );
                    // 只是layout背景透明(仿知乎滑动效果)
                       int alphaint=(int)alpha;
                    //因为算出来的值可能永远不能等于255，所以这么设置
                    if(alpha>=250){
                        alphaint=255;
                    }
                    ma.customActionbar.setBackgroundColor(Color.argb(alphaint,255, 255, 255));
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void updateVP(List<ADBean.ContentBean> content) {
        presenter.readADdata(content, slider);
        updateSlider();
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void updateVP(HashMap<Double, Double> map) {
        //读map的数据
        Set<Map.Entry<Double, Double>> entries = map.entrySet();
        //拿到第一条记录 ，因为只有一条
        Map.Entry<Double, Double> next = entries.iterator().next();

        presenter.requestADdata(next.getKey() + "," + next.getValue());
    }

    @Subscribe(threadMode = ThreadMode.MainThread)
    public void showError(HomeFragment homeFragment) {
        showNetError();
    }

    //当获取到推荐商户时，怎么做
    @Subscribe(threadMode = ThreadMode.MainThread)
    public void showVP2(TuijianShopBean obj) {
        //TODO 更新第二个viewpager
        ArrayList<TuijianShopBean.ListBean> list = (ArrayList<TuijianShopBean.ListBean>) obj.getList();
        showVp2(list);

    }


    @Override
    public void updateSlider() {
        //开始自动轮播
        slider.startAutoCycle();

    }

    @Override
    public void showNetError() {
        Toast.makeText(getActivity(), "网络异常，请检查您的网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVp2(ArrayList<TuijianShopBean.ListBean> list) {
        //先初始化gridview
        double v = list.size() / 8.0d;
        //往上取值
        int ceil = (int) Math.ceil(v);
        for (int i = 0; i < ceil; i++) {
            //添加新集合，里面装载8条数据
            ArrayList<TuijianShopBean.ListBean> listBeen = generateNewList(list, i);
            item_gridview = (GridView) View.inflate(getActivity(), R.layout.item_gridview, null);
            //根据新集合出来的适配器
            HomeItemGvAdapter homeItemGvAdapter = new HomeItemGvAdapter(getActivity(), listBeen);
            item_gridview.setAdapter(homeItemGvAdapter);
            gridViews.add(item_gridview);
        }
        TuijianShopPagerAdapter ad = new TuijianShopPagerAdapter(gridViews);
        vp.setAdapter(ad);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    resetViewPagerIndi();
                    vp1.setBackgroundResource(R.drawable.viewpager_selected);
                } else {
                    resetViewPagerIndi();
                    vp2.setBackgroundResource(R.drawable.viewpager_selected);
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void resetViewPagerIndi() {
        vp1.setBackgroundResource(R.drawable.viewpager_normal);
        vp2.setBackgroundResource(R.drawable.viewpager_normal);
    }

    //每八条创建新的集合
    private ArrayList<TuijianShopBean.ListBean> generateNewList(ArrayList<TuijianShopBean.ListBean> lists, int page) {
        ArrayList<TuijianShopBean.ListBean> listBeen = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (8 * page + i <= lists.size()) {
                listBeen.add(lists.get(8 * page + i));
            }
        }
        return listBeen;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
