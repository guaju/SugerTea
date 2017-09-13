package com.guaju.animcircle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.guaju.animcircle.R;

/**
 * Created by guaju on 2017/9/11.
 */

public class AnimCircle extends View {
    private static final String TAG = "AnimCircle";
    private Paint paint,paintBlue;
    private float radius;
    private int rwidth;
    private float arc=0.0f;
    private int count=0;
    private float flag=0.0f;
    private TextPaint textPaint;
    private String percentS="";
    private float percentReal=0.0f;
    private float v;


    public AnimCircle(Context context) {
        super(context);
        init();
    }

    public AnimCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
         init();
    }



    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //MeasureSpec.AT_MOST;   wrap_content match_parent     追求父容器最大值
//        MeasureSpec.EXACTLY;   精确的  16dp 17sp
       // MeasureSpec.UNSPECIFIED;  未指定的



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
        paintBlue = new Paint();
        //上边一层的画笔
        paintBlue.setColor(getResources().getColor(R.color.blue));
        paintBlue.setStrokeWidth(getResources().getDimensionPixelSize(R.dimen.paintBlue));
        paintBlue.setStyle(Paint.Style.STROKE);
        paintBlue.setAntiAlias(true);
        paintBlue.setStrokeCap(Paint.Cap.ROUND);
        // 用画笔把字画出来
        textPaint = new TextPaint();
        textPaint.setColor(Color.BLUE);
        textPaint.setTextSize(getResources().getDimensionPixelSize(R.dimen.textsize));



        radius=getResources().getDimensionPixelSize(R.dimen.radius);

    }
    //在ondraw 中千万不能干什么？  千万不能初始化画笔等其他控件
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        init();
        Log.e(TAG, "onDraw: " );
        //画东西就可以了
        /**
         * 参数1：x轴圆心坐标
         * 参数2：y轴圆心坐标
         * 参数3：半径
         * 参数4：画笔
         */
        float strokeWidth = paint.getStrokeWidth();
        float strokeBlueWidth = paintBlue.getStrokeWidth();
        canvas.drawCircle(radius+strokeWidth,radius+strokeWidth,radius,paint);

        canvas.drawArc(strokeWidth,
                strokeWidth,
                2.0f*radius+strokeWidth,
                2.0f*radius+strokeWidth,
                -90.0f,arc,false,paintBlue);
        //总共的长度
//        float v = textPaint.getTextWidths(percentS,0,percentS.length(),floats);

        float textSize = textPaint.getTextSize();
        float v = (count+"%").length() * textSize;
        //想要居中显示
        float x = (2.0f * (radius + strokeWidth)-v/2.0f) / 2.0f;
        float y=   (radius + strokeWidth)+textSize/2.0f;
        canvas.drawText(count+"%",x,y,textPaint);

    }
    public void setARC(float arc){
           this.arc=arc;
        //重绘
           postInvalidate();
    }
     public  void setFlagARC(float num){
          this.flag=num;

     }
     //需要用户传几十的数字，按照百分比的方式显示
     public void setPercent(float percent){
         if (percent>100.0f){
             flag=360.0f;
//             percentS="100%";
             percentReal=100;
             return;
         }

          //需要把比例转成 弧度
          if (percent<=1){
              //说明传的是一个百分比
              flag=percent*360;
              percentReal=percent*100;
//              percentS=percent*100+"%";
          }
          else{
              //说明传的是几十 或十几，需要转化成百分比
              float v = percent / 100;
              flag = v * 360;//弧度制
              percentReal=percent;
//              percentS=percent+"%";

          }


     }

    public void  startDraw(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(1000);
                while (arc<flag) {
                    SystemClock.sleep(100);
                    arc+=10.0f;
                    //速度
                    float cishu = flag / 10.0f;
                    //我们要算每次加多少值
                    v = percentReal / cishu;
                    count+=v;
                    //适用于在子线程中发送请求让onDraw方法重绘  invalidate
//                    invalidate();//只能用于主线程
                    postInvalidate();
                }
                if (arc>=flag){
                    count= (int) percentReal;
                    postInvalidate();
                }
            }
        }).start();
    }

}
