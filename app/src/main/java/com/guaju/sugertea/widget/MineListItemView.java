package com.guaju.sugertea.widget;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.guaju.sugertea.R;

/**
 * Created by guaju on 2017/8/22.
 */

public class MineListItemView extends FrameLayout {
    private TextView tv_name,tv_order_num;
    private String title;
    private int icon;
    private ImageView iv_title;

    public MineListItemView(@NonNull Context context) {
        super(context);
        initView();
    }


    public MineListItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
        int attributeCount = attrs.getAttributeCount();
        for (int i=0;i<attributeCount;i++){
            String attributeName = attrs.getAttributeName(i);
            if (attributeName.equals("text")){
                title= attrs.getAttributeValue(i);
            }
            if (attributeName.equals("icon")){
                icon = attrs.getAttributeResourceValue(i, R.mipmap.ic_launcher);
            }
        }
         setNameAndIcon(title,icon);
    }

    public MineListItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.mine_item,null, false);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_name.setTextColor(getResources().getColor(R.color.grey));
        tv_name.setTextSize(getResources().getDimensionPixelSize(R.dimen.mine_title_size));
        tv_order_num = (TextView) view.findViewById(R.id.tv_order_num);
        iv_title = (ImageView) view.findViewById(R.id.iv_title);

        this.addView(view);
    }

    public void setNum(String num){
        if (!TextUtils.isEmpty(num)){
        tv_order_num.setText(num);
        }

    }
    private void setNameAndIcon(String str,int id){
        if (!TextUtils.isEmpty(str)){
        tv_name.setText(str);
        }
        if (id!=0){
            iv_title.setBackgroundResource(id);
        }
    }


}
