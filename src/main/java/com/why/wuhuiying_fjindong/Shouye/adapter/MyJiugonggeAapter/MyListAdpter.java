package com.why.wuhuiying_fjindong.Shouye.adapter.MyJiugonggeAapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.why.wuhuiying_fjindong.R;
import com.why.wuhuiying_fjindong.Shouye.bean.JiuGoGeBean;


import java.util.List;

/**
 * Created by 小慧莹 on 2017/12/26.
 */

public class MyListAdpter extends RecyclerView.Adapter<MyListHolder>{
    private  Context context;
    private List<JiuGoGeBean.DataBean> data;

    public MyListAdpter(Context context, List<JiuGoGeBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public MyListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //条目的视图
        View view = View.inflate(context, R.layout.jiugongge_layout, null);
        //根据条目的视图创建viewholder
        MyListHolder myListHolder=new MyListHolder(view);
        return myListHolder;

    }

    @Override
    public void onBindViewHolder(MyListHolder holder, int position) {
       holder.tv.setText(data.get(position).getName());
        Glide.with(context).load(data.get(position).getIcon()).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
