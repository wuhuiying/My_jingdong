package com.why.wuhuiying_fjindong.Shouye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;


import com.why.wuhuiying_fjindong.ApiUtil.API;
import com.why.wuhuiying_fjindong.Fenlei.Youfrag;
import com.why.wuhuiying_fjindong.Fenlei.adapter.MyZAdapter;
import com.why.wuhuiying_fjindong.Fenlei.bean.YoufenleiBean;
import com.why.wuhuiying_fjindong.Fenlei.bean.ZuoFenleiBean;
import com.why.wuhuiying_fjindong.Fenlei.view.IMYView;
import com.why.wuhuiying_fjindong.Fenlei.view.IMZView;

import com.why.wuhuiying_fjindong.Fenlei.prester.MyZPrester;
import com.why.wuhuiying_fjindong.R;
import com.why.wuhuiying_fjindong.Shouye.Resou.ResouActivity;
import com.why.wuhuiying_fjindong.Shouye.SearchActivity;

import java.util.List;

/**
 * Created by 小慧莹 on 2018/1/8.
 */

public class fragfenlei extends Fragment implements IMZView{
    private static  int totalHeight=0;
    private View view;
    private ListView fenleilv;
    private MyZPrester myZPrester;
    public static int mPosition;
    private MyZAdapter myZAdapter;
    private TextView tv_sousuo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fenlei,container,false);
        //获取控件
        findView();


        //建立左边分类的中间层
        myZPrester = new MyZPrester(this);

        myZPrester.getZPresterData(API.SHOPPING_API);

        fenleilv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //调用左边条目的点击事件
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //拿到当前位置
                mPosition=i;
                //点击listViewitem回滚居中
                totalHeight = fenleilv.getMeasuredHeight() - 120;
                fenleilv.smoothScrollToPositionFromTop(mPosition, totalHeight /2,50);
                myZAdapter.notifyDataSetChanged();
               Youfrag youfrag= Youfrag.getYoufrag(mPosition);


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,youfrag).commit();

            }
        });



        return view;
    }

    private void findView() {
        fenleilv = view.findViewById(R.id.fenleilv);
        tv_sousuo = view.findViewById(R.id.tv_sousuo);
        tv_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ResouActivity.class);

                startActivity(intent);
            }
        });
    }

    @Override
    public void Onsuccess(final ZuoFenleiBean zuoFenleiBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<ZuoFenleiBean.DataBean> data = zuoFenleiBean.getData();
               //创建适配器
                myZAdapter = new MyZAdapter(getActivity(),data);
                fenleilv.setAdapter(myZAdapter);
                //替换布局





            }
        });

    }






}
