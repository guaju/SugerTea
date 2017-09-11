
package com.guaju.sugertea.widget;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.dianjiake.android.R;


/**
 * 可自定义比例的FrameLayout
 */
public class ProportionLayout extends FrameLayout {
    private double mWidthPro;
    private double mHeightPro;

    public ProportionLayout(Context context) {
        this(context, null);
    }

    public ProportionLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProportionLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProportionLayout, defStyleAttr, 0);
        //从xml布局中拿到的宽高
        mWidthPro = (double) a.getFloat(R.styleable.ProportionLayout_width_pro, 0f);
        mHeightPro = (double) a.getFloat(R.styleable.ProportionLayout_height_pro, 0f);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //  拿到父类传过来的宽高
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));
        //让孩子的宽等于父类指定的宽
        int childWidthSize = getMeasuredWidth();
        //如果布局中的宽高小于0的话就让孩子的宽高设为父类指定的宽高
        if (mWidthPro <= 0 || mHeightPro <= 0) {
            heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);

        } else {
            //不然的话就定为父类传过来的高
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
            //高是通过父类指定的宽*高/指定的宽，让子控件的高等比例缩放
            heightMeasureSpec = MeasureSpec.makeMeasureSpec((int) (childWidthSize * mHeightPro / mWidthPro), MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
