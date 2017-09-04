package com.guaju.floatingmenu;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;

public class MainActivity extends AppCompatActivity {
    private FullHeightListView  myLv;
    private ScrollView mySv;
    private LinearLayout linear_middle;
    private LinearLayout linear_dong;
    private LinearLayout linear_jing;
    private int[] location;
    private int[] location2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        location = new int[2];
        location2 = new int[2];
        initViews();
        }

    private void initViews(){
        myLv = (FullHeightListView )findViewById(R.id.linkage_lv);
        mySv = (ScrollView)findViewById(R.id.linkage_scroll);
        //找到黄色布局id
        linear_middle = (LinearLayout)findViewById(R.id.linkage_linear_middle);
        //要滚动的menu
        linear_dong = (LinearLayout)findViewById(R.id.linkage_linear_menudong);
        //悬浮在scrollview上方的menu
        linear_jing = (LinearLayout)findViewById(R.id.linkage_linear_jing);
        //listview适配器
        LinkAgeBaseAdaper adapter = new LinkAgeBaseAdaper();
        myLv.setAdapter(adapter);

//        setListViewHeightBasedOnChildren(myLv);
        //核心代码
        mySv.setOnTouchListener(new View.OnTouchListener(){
            private int lastY = 0;
            private int touchEventId = 10000;   //设置了一个标记值，区分我是在做滑动监听
            Handler handler = new Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if (msg.what == touchEventId) {
                        //只有滑动时触发
                        if (lastY != mySv.getScrollY()) {
                            //scrollview一直在滚动，会触发
                            handler.sendMessageDelayed(
                                    handler.obtainMessage(touchEventId, mySv), 5);
                            //拿到当时手指点击scrollview的y轴位置
                            lastY = mySv.getScrollY();
                            //拿到当前控件在屏幕的那个距离，距离值保存在参数中
                            linear_dong.getLocationOnScreen(location);
                            linear_jing.getLocationOnScreen(location2);
                            //动的到静的位置时，静的显示。动的实际上还是往上滚动，但我们看到的是静止的那个
                            if (location[1] <= location2[1]) {
                                linear_jing.setVisibility(View.VISIBLE);
                            } else {
                                //静止的隐藏了
                                linear_jing.setVisibility(View.GONE);
                            }
                        }
                    }
                }
            };

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()){
                    case MotionEvent.ACTION_MOVE://移动
                        handler.sendEmptyMessage(touchEventId);
                        break;
                    case MotionEvent.ACTION_UP://抬起
                        handler.sendEmptyMessageDelayed(touchEventId, 5);
                        break;
                }
                return false;
            }

        });

    }

    class LinkAgeBaseAdaper extends BaseAdapter {

        @Override
        public int getCount() {
            return 20;
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }

        @Override
        public View getView(int arg0, View convertView, ViewGroup arg2) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            if(convertView == null){
                convertView = inflater.inflate(R.layout.item_textview, null);
            }
            return convertView;
        }

    }
    /**动态改变listView的高度*/
    public void setListViewHeightBasedOnChildren(ListView listView) {
        //拿到listview的适配器
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        //定义一个变量去存储整个高度
        int totalHeight = 0;

        for (int i = 0; i < listAdapter.getCount(); i++) {
            //从适配器中取得单个条目的view
            View listItem = listAdapter.getView(i, null, listView);
            //重新设置测量模式：作用是避免有时直接去view的宽高取不到，需要重新写一遍view,measure方法
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
//           totalHeight += 80;
        }
        //拿到listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
//          params.height = 80 * (listAdapter.getCount() - 1);
//          params.height = 80 * (listAdapter.getCount());
        //把单个条目的高度总和+divider的高度总和+让margin值为0，定义成layoutparams的高度，然后
        //再把高度给他设置上，这样listview就有一个新的layoutparams
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        //把margin值置为0，即没有margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(0, 0, 0, 0);
        listView.setLayoutParams(params);

    }
}