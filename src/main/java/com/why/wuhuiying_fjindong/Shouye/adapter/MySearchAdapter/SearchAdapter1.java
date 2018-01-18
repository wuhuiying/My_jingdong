package com.why.wuhuiying_fjindong.Shouye.adapter.MySearchAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.why.wuhuiying_fjindong.Gouwuche.Shangpinxiangqing;
import com.why.wuhuiying_fjindong.R;

import com.why.wuhuiying_fjindong.Shouye.bean.SearchBean;

import java.util.List;

/**
 * Created by 小慧莹 on 2018/1/6.
 */
public class SearchAdapter1 extends RecyclerView.Adapter<SearchAdapter1.ViewHolder> {
    Context context;
    List<SearchBean.DataBean> data;
    private Onitemclick onitemclick;

    public SearchAdapter1(Context context, List<SearchBean.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_lisyview2, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        SearchBean.DataBean dataBean = data.get(position);
        String images = dataBean.getImages();
        String[] split = images.split("\\|");
        //设置数据
        Glide.with(context).load(split[0]).into(holder.iv);
        holder.tv2.setText("原价"+dataBean.getPrice()+""+"元");
        holder.tv2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
        holder.tv1.setText(dataBean.getTitle());
        holder.tv3.setText("折扣价"+dataBean.getBargainPrice()+"元");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pid = data.get(position).getPid() + "";
                Toast.makeText(context,pid,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(context, Shangpinxiangqing.class);
                intent.putExtra("pid",pid);
                context.startActivity(intent);
            }
        });
    }



    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv1,tv2,tv3;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.img);
            tv1 = itemView.findViewById(R.id.title);
            tv3 = itemView.findViewById(R.id.barginprice);
            tv2 = itemView.findViewById(R.id.price);

        }
    }
    //设置适配器接口，自定义接口
    public interface Onitemclick{
        void OnItemClilkListener(View view,int position);
    }
    public  void setOnItemClilkListener(Onitemclick onitemclick){

        this.onitemclick = onitemclick;
    }
}