package com.why.wuhuiying_fjindong.Fenlei.adapter;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.why.wuhuiying_fjindong.Fenlei.bean.YoufenleiBean;
import com.why.wuhuiying_fjindong.R;
import com.why.wuhuiying_fjindong.Shouye.SearchActivity;

import java.util.List;

/**
 * Created by 小慧莹 on 2018/1/10.
 */

class MyYouZiIziAdapter extends RecyclerView.Adapter<MyYouZiIziAdapter.ViewHolder>{
    private  List<YoufenleiBean.DataBean.ListBean> listbean;
    private  FragmentActivity activity;
    private ViewHolder viewHolder;
    private OnItemClickLister onItemClickLister;

    public MyYouZiIziAdapter(FragmentActivity activity, List<YoufenleiBean.DataBean.ListBean> listbean) {
   this.activity=activity;
   this.listbean=listbean;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(activity, R.layout.youbianzizi, null);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
         viewHolder.tv.setText(listbean.get(position).getName());
        String[] image = listbean.get(position).getIcon().split("\\|");
        Glide.with(activity).load(image[0]).into(viewHolder.iv);
        //如果设置了回传事件
        if(onItemClickLister!=null){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = listbean.get(position).getName();
                    Intent intent=new Intent(activity, SearchActivity.class);
                    intent.putExtra("name",name);
                    activity.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listbean.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        ImageView iv;
        public ViewHolder(View itemView) {
            super(itemView);
             tv = itemView.findViewById(R.id.youbian_zizitv);
             iv = itemView.findViewById(R.id.youbian_ziziiv);
        }
    }
    public  interface OnItemClickLister{
        void OnItemclick(View view,int position);
        void OnItemLongclick(View view,int position);

    }
    public  void setOnItemclickListener(OnItemClickLister onItemClickLister){

        this.onItemClickLister = onItemClickLister;
    }

}
