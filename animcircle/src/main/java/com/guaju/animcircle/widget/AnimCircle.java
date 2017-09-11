package com.guaju.animcircle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.guaju.animcircle.R;

/**
 * Created by guaju on 2017/9/11.
 */

public class AnimCircle extends View {
    private static final String TAG = "AnimCircle";
    private Paint paint;
    private float radius;
    private int rwidth;


    public AnimCircle(Context context) {
        super(context);
        init();
    }

    public AnimCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        int attributeCount = attrs.getAttributeCount();
        init();

    }

    public AnimCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
      //自己指定宽高
        if (MeasureSpec.getMode(widthMeasureSpec)==MeasureSpec.AT_MOST||MeasureSpec.getMode(heightMeasureSpec)==MeasureSpec.AT_MOST){
        int width= (int) (2*radius+2*paint.getStrokeWidth());
        int height= (int) (2*radius+2*paint.getStrokeWidth());
        setMeasuredDimension(width,height);
        }
        else{
            int wSize = MeasureSpec.getSize(widthMeasureSpec);
            rwidth=wSize;
            radius= (rwidth-2* paint.getStrokeWidth())/2;
            //让ondraw（）再调用一次
            postInvalidate();
            int hSize = MeasureSpec.getSize(heightMeasureSpec);
            int wm = MeasureSpec.makeMeasureSpec(wSize, MeasureSpec.EXACTLY);
            int hm = MeasureSpec.makeMeasureSpec(hSize, MeasureSpec.EXACTLY);
            super.onMeasure(wm,hm);
        }


    }

    //初始化画笔
    void init(){
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.xiguared));
        //设置粗度
        paint.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.paint));
        //设置画笔的模式,这个是只有边 ，没有填充色的
        paint.setStyle(Paint.Style.STROKE);
        //再给一个属性就是  抗锯齿  让你的画笔更加圆滑一些
        paint.setAntiAlias(true);
        if (rwidth==0){
        radius=getResources().getDimensionPixelSize(R.dimen.radius);
        }
        else{
          radius= (rwidth-2* paint.getStrokeWidth())/2;
        }
    }
    //在ondraw 中千万不能干什么？  千万不能初始化画笔等其他控件
    @Override
    protected void onDraw(Canvas canvas) {
        Log.e(TAG, "onDraw: " );
        //画东西就可以了
        /**
         * 参数1：x轴圆心坐标
         * 参数2：y轴圆心坐标
         * 参数3：半径
         * 参数4：画笔
         */
        float strokeWidth = paint.getStrokeWidth();
        canvas.drawCircle(radius+strokeWidth,radius+strokeWidth,radius,paint);
    }
}
