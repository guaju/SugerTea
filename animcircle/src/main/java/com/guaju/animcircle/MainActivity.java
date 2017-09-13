package com.guaju.animcircle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.guaju.animcircle.widget.AnimCircle;

public class MainActivity extends AppCompatActivity {

    private AnimCircle ac;
    private float count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ac = (AnimCircle) findViewById(R.id.ac);
        drawCircle();



    }

    private void drawCircle() {

//        ac.setFlagARC(90.0f);
        ac.setPercent(360);
        //手动控制动画
        ac.startDraw();
    }


}
