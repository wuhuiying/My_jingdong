package com.why.wuhuiying_fjindong.Shouye.adapter.MYTuijian;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.why.wuhuiying_fjindong.R;
import com.why.wuhuiying_fjindong.Shouye.bean.LuoBoBean;


import java.util.List;


/**
 * Created by 小慧莹 on 2017/12/27.
 */

public class MyTuijianAdpter extends RecyclerView.Adapter<MyTuijianHolder> {
    private  Context context;
    private List<LuoBoBean.TuijianBean.ListBean> tui;
    private OnItemclickListner onItemclickListner;

    public MyTuijianAdpter(Context context, List<LuoBoBean.TuijianBean.ListBean> tui) {
        this.context=context;
        this.tui=tui;
    }

    @Override
    public MyTuijianHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.tuijian_listview, null);
        MyTuijianHolder tuijianHolder=new MyTuijianHolder(view) ;

        return tuijianHolder;
    }

    @Override
    public void onBindViewHolder(MyTuijianHolder holder, final int position) {
        String s = tui.get(position).getImages().split("\\|")[0] + "";
        Glide.with(context).load(s).into(holder.iv);
        holder.name.setText(tui.get(position).getTitle());
        holder.price.setText(tui.get(position).getPrice()+"");
holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        onItemclickListner.OnItemclick(position);

    }
});

    }

    @Override
    public int getItemCount() {
        return tui.size();
    }

    /**
     * 自定义接口，因为适配器不能设置点击事件
     */
    public interface OnItemclickListner{
        void OnItemclick(int position);
    }
    public  void setOnItemclickListner(OnItemclickListner onItemclickListner){

        this.onItemclickListner = onItemclickListner;
    }
}
