package com.guaju.floatingmenu;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by guaju on 2017/9/4.
 */

public class FullHeightListView extends ListView {
    public FullHeightListView(Context context) {
        super(context);
    }

    public FullHeightListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullHeightListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //拿到父类指定的宽
//        int width = MeasureSpec.getSize(widthMeasureSpec);
        //重新指定一个高，右移两位是为了把前两位的mode空出来，真正取得测量模式自己范围里面的最大值
        int height=Integer.MAX_VALUE>> 2;
        //设置一个新的测量空间 MeasureSpec.AT_MOST是追求最大 的测量模式
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }
}
