package com.why.wuhuiying_fjindong.Fenlei.adapter;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.why.wuhuiying_fjindong.Fenlei.bean.ZuoFenleiBean;
import com.why.wuhuiying_fjindong.R;
import com.why.wuhuiying_fjindong.Shouye.fragment.fragfenlei;

import java.util.List;

/**
 * Created by 小慧莹 on 2018/1/9.
 */

public class MyZAdapter extends BaseAdapter{
    private final FragmentActivity activity;
    private final List<ZuoFenleiBean.DataBean> data;
    private TextView tv;

    public MyZAdapter(FragmentActivity activity, List<ZuoFenleiBean.DataBean> data) {
        this.activity=activity;
        this.data=data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

             view = View.inflate(activity, R.layout.fenlei_zuobian_listview, null);
            tv = view.findViewById(R.id.zuobibian_tv);
            tv.setText(data.get(i).getName());
            //点击条目时背景变化
        if(i == fragfenlei.mPosition) {
            tv.setTextColor(Color.RED);
            view.setBackgroundColor(Color.parseColor("#f1f5f5"));
        }
        else {
            tv.setTextColor(Color.parseColor("#000000"));
            view.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        return view;
    }
}
