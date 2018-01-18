package com.why.wuhuiying_fjindong.Fenlei.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.why.wuhuiying_fjindong.Fenlei.bean.YoufenleiBean;
import com.why.wuhuiying_fjindong.R;
import com.why.wuhuiying_fjindong.Shouye.SearchActivity;

import java.util.List;

/**
 * Created by 小慧莹 on 2018/1/9.
 */

class MyYouZiAdapter extends RecyclerView.Adapter<MyYouZiAdapter.ViewHolder>{


    private  FragmentActivity activity;
    private  List<YoufenleiBean.DataBean> list;
    private ViewHolder viewHolder;
    private MyYouZiIziAdapter myYouZiIziAdapter;
    private MyYouZiIziAdapter.OnItemClickLister onItemClickLister;

    public MyYouZiAdapter(FragmentActivity activity, List<YoufenleiBean.DataBean> list) {
        this.activity=activity;
        this.list=list;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.fenleiyouzi, null);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {


        String name = list.get(position).getName();
        viewHolder.tv.setText(name);
        List<YoufenleiBean.DataBean.ListBean> listbean = this.list.get(position).getList();
        if(listbean!=null){
            myYouZiIziAdapter = new MyYouZiIziAdapter(activity,listbean);
            holder.rec_zi.setAdapter(myYouZiIziAdapter);
            holder.rec_zi.setLayoutManager(new GridLayoutManager(activity,3));
          myYouZiIziAdapter.setOnItemclickListener(new MyYouZiIziAdapter.OnItemClickLister() {
              @Override
              public void OnItemclick(View view, int position) {
                  Toast.makeText(activity,position+"click",Toast.LENGTH_SHORT).show();
              }

              @Override
              public void OnItemLongclick(View view, int position) {

              }
          });


        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
       TextView tv;
        RecyclerView rec_zi;
        public ViewHolder(View itemView) {
            super(itemView);
             rec_zi = itemView.findViewById(R.id.rec_youzi);
            tv = itemView.findViewById(R.id.youbianzi_tv);
        }
    }

}
