package com.why.wuhuiying_fjindong.Shouye.adapter.MyMiaoshaAdpter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.why.wuhuiying_fjindong.R;


/**
 * Created by 小慧莹 on 2017/12/27.
 */

public class MiaoshaHolder extends RecyclerView.ViewHolder {

    public ImageView iv;

    public MiaoshaHolder(View itemView) {
        super(itemView);
        iv = itemView.findViewById(R.id.iv);
    }
}
