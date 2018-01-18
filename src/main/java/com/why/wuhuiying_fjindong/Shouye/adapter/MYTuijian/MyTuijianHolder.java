package com.why.wuhuiying_fjindong.Shouye.adapter.MYTuijian;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.why.wuhuiying_fjindong.R;


/**
 * Created by 小慧莹 on 2017/12/27.
 */

public class MyTuijianHolder extends RecyclerView.ViewHolder {

    public   ImageView iv;
    public  TextView name;
    public  TextView price;

    public MyTuijianHolder(View itemView) {
        super(itemView);
        iv = itemView.findViewById(R.id.iv);
        name = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.price);
    }
}
