package com.why.wuhuiying_fjindong.Fenlei;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.why.wuhuiying_fjindong.ApiUtil.API;
import com.why.wuhuiying_fjindong.Fenlei.adapter.MyYAdapter;
import com.why.wuhuiying_fjindong.Fenlei.bean.YoufenleiBean;
import com.why.wuhuiying_fjindong.Fenlei.prester.MyYPrester;
import com.why.wuhuiying_fjindong.Fenlei.view.IMYView;
import com.why.wuhuiying_fjindong.R;

import java.util.List;

/**
 * Created by 小慧莹 on 2018/1/9.
 */

public class Youfrag extends Fragment implements IMYView{

    private View view;
    private RecyclerView rec_youbian;
    private MyYPrester myYPrester;
     private  int cid;
    private List<YoufenleiBean.DataBean> data;
    private MyYAdapter myYAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fenlei_youbian_listview, container, false);
        //获取控件
        findView();
        myYPrester = new MyYPrester(this);
        cid=getArguments().getInt("cid");
        myYPrester.getYPresterData(API.SHOP_API+cid);


        return view;
    }

    private void findView() {
        rec_youbian = view.findViewById(R.id.rec_youbian);
    }

    @Override
    public void Onsuccess(final YoufenleiBean youfenleiBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data = youfenleiBean.getData();
                //设置适配器
                rec_youbian.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
                myYAdapter = new MyYAdapter(getActivity(),data);
                rec_youbian.setAdapter(myYAdapter);
            }
        });
    }



    public static Youfrag getYoufrag(int mPosition) {
        Youfrag youfrag=new Youfrag();
        Bundle bundle=new Bundle();
        bundle.putInt("cid",mPosition);
        youfrag.setArguments(bundle);

        return youfrag;
    }
}
