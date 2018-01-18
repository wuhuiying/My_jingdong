package com.why.wuhuiying_fjindong.Fenlei.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.why.wuhuiying_fjindong.Fenlei.bean.YoufenleiBean;
import com.why.wuhuiying_fjindong.R;

import java.util.List;

/**
 * Created by 小慧莹 on 2018/1/9.
 */

public class MyYAdapter extends RecyclerView.Adapter<MyYAdapter.ViewHolder>{
    private  List<YoufenleiBean.DataBean> data;
    private  FragmentActivity activity;
    private ViewHolder viewHolder;


    public MyYAdapter(FragmentActivity activity, List<YoufenleiBean.DataBean> data) {
        this.activity=activity;
        this.data=data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.fenlei_youbian_listview, null);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {




        //创建适配器
        MyYouZiAdapter myYouZiAdapter=new MyYouZiAdapter(activity, data);
        viewHolder.rec_youbian.setAdapter(myYouZiAdapter);
        viewHolder.rec_youbian.setLayoutManager(new LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false
        ));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView rec_youbian;
        public ViewHolder(View itemView) {
            super(itemView);

            rec_youbian = itemView.findViewById(R.id.rec_youbian);
        }
    }
}
