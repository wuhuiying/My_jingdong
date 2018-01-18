package com.why.wuhuiying_fjindong.Gouwuche.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by 小慧莹 on 2018/1/11.
 */

public class MyExanable extends ExpandableListView {
    public MyExanable(Context context) {
        super(context);
    }

    public MyExanable(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyExanable(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //自定义二级列表


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //高度
        int height = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, height);
    }
}
