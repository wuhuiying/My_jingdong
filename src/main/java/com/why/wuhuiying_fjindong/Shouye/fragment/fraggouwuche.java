package com.why.wuhuiying_fjindong.Shouye.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.why.wuhuiying_fjindong.ApiUtil.API;
import com.why.wuhuiying_fjindong.Dingdan.bean.MyDingdanBean;
import com.why.wuhuiying_fjindong.DingdanActivity;
import com.why.wuhuiying_fjindong.Dingdan.bean.MyCreatDingdan;
import com.why.wuhuiying_fjindong.Gouwuche.MyDingPrester;
import com.why.wuhuiying_fjindong.Gouwuche.adapter.MyGouAdapter;
import com.why.wuhuiying_fjindong.Gouwuche.bean.CountPriceBean;
import com.why.wuhuiying_fjindong.Gouwuche.bean.MyGouWuBean;
import com.why.wuhuiying_fjindong.Gouwuche.prester.MyGouPrester;
import com.why.wuhuiying_fjindong.Gouwuche.view.IMCreatView;
import com.why.wuhuiying_fjindong.Gouwuche.view.IMGouView;
import com.why.wuhuiying_fjindong.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小慧莹 on 2018/1/8.
 */

public class fraggouwuche extends Fragment implements IMGouView, IMCreatView, View.OnClickListener{
    private View view;
    private ExpandableListView expanble;
    private RelativeLayout relative_progress;
    private MyGouAdapter myGouAdapter;
    private SharedPreferences why;
    private CheckBox check_all;
    private TextView text_total;
    private TextView text_buy;
    private MyGouPrester myGouPrester;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                CountPriceBean countPriceBean = (CountPriceBean) msg.obj;
                text_total.setText("合计" + countPriceBean.getPriceString());
                text_buy.setText("去结算" + countPriceBean.getCount());
            }

        }
    };
    private MyDingPrester myDingPrester;
    private String code;
    private String msg;
    private MyGouWuBean myGouWuBean;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.gouwuche, container, false);
        //获取控件
        findView();
        //创建p层
        myGouPrester = new MyGouPrester(this);
        myDingPrester = new MyDingPrester(this);
        //去掉默认的指示器
        expanble.setGroupIndicator(null);

        //全选设置点击事件
        check_all.setOnClickListener(this);

        why = getActivity().getSharedPreferences("why", Context.MODE_PRIVATE);

        //去结算跳到订单页面
        text_buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double price = 0;


                List<MyGouWuBean.DataBean> data = myGouWuBean.getData();
//                Log.e("HHH=========",data.toString());
                for (int i = 0; i < myGouWuBean.getData().size(); i++) {
                    List<MyGouWuBean.DataBean.ListBean> list = myGouWuBean.getData().get(i).getList();
                    for (int j = 0; j < list.size(); j++) {
                        MyGouWuBean.DataBean.ListBean listBean = list.get(j);
                        //选中的时候计算数量和价格
                        if (listBean.getSelected() == 1) {
                            price += listBean.getBargainPrice() * listBean.getNum();

                        }
                    }
                }
                //给价钱格式转换一下，以免出现一串数字
                DecimalFormat decimalFormat = new DecimalFormat("0.00"); //使用java下的包
                String formatprice = decimalFormat.format(price);
                myDingPrester.getDingPresterData(API.CREATE_API, formatprice);


            }
        });


        return view;

    }

    /**
     * 获取控件
     */
    private void findView() {
        expanble = view.findViewById(R.id.expanble);
        relative_progress = view.findViewById(R.id.relative_progress);
        check_all = view.findViewById(R.id.check_all_01);
        text_total = view.findViewById(R.id.text_total);
        text_buy = view.findViewById(R.id.text_buy);
    }


    /**
     * 在onresume方法里写，只要购物车页面显示，就去网络获取新的数据，获取购物车数据操作放到生命周期上
     */
    @Override
    public void onResume() {
        super.onResume();
        //MVP——p层获取购物车网络数据
        myGouPrester.getGouPresterData(API.GETCARTS_API, why.getString("uid", ""));


    }

    @Override
    public void Onsuccess(final MyGouWuBean myGouWuBean) {
        this.myGouWuBean=myGouWuBean;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //获取数据成功，隐藏进度条
                relative_progress.setVisibility(View.GONE);
                if (myGouWuBean != null) {
                    //根据一个组中的二级列表是否选中，判断当前一级列表是否选中
                    for (int i = 0; i < myGouWuBean.getData().size(); i++) {
                        Log.e("why-----", myGouWuBean.toString());
                        if (isChildInGroupChecked(i, myGouWuBean)) {
                            myGouWuBean.getData().get(i).setGroup_check(true);
                        }
                    }
                    /**
                     * 判断所有的组是否选中
                     */
                    check_all.setChecked(isAllGroupChecked(myGouWuBean));
                    //设置适配器
                    myGouAdapter = new MyGouAdapter(getActivity(), myGouWuBean, handler, myGouPrester, relative_progress);
                    expanble.setAdapter(myGouAdapter);
                    /**
                     * 展开所有的组
                     */
                    for (int i = 0; i < myGouWuBean.getData().size(); i++) {
                        //expandGroup是默认展开所有的组,ExpandableListView 默认是展开的
                        expanble.expandGroup(i);
                    }
                    /**
                     * 计算总价和商品的数量
                     */
                    myGouAdapter.setPriceAndCount();
                } else {
                    Toast.makeText(getActivity(), "购物车空,请您添加商品", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**
     * 判断所有的组是否选中
     */
    private boolean isAllGroupChecked(MyGouWuBean myGouWuBean) {
        for (int i = 0; i < myGouWuBean.getData().size(); i++) {
            //表示没有选中的组
            if (!myGouWuBean.getData().get(i).isGroup_check()) {
                return false;
            }
        }

        return true;
    }

    /**
     * 当前组中s所有的子条目是否选中
     *
     * @param i
     * @param myGouWuBean
     * @return
     */
    private boolean isChildInGroupChecked(int i, MyGouWuBean myGouWuBean) {
        //当前组中字条目的数据（二级列表）
        List<MyGouWuBean.DataBean.ListBean> listbean = myGouWuBean.getData().get(i).getList();
        for (int j = 0; j < listbean.size(); j++) {
            //未选中的条目
            if (listbean.get(j).getSelected() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_all_01:
                //所有的子条目跟着条目的状态改变
                if (myGouAdapter != null) {
                    myGouAdapter.setAllChildChecked(check_all.isChecked());
                }
                break;
        }
    }

    /**
     * 创建订单的数据
     *
     * @param myCreatDingdan
     */
    @Override
    public void Onsuccess(final MyCreatDingdan myCreatDingdan) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                code = myCreatDingdan.getCode();
                msg = myCreatDingdan.getMsg();

                if (code.equals("0")) {
                    Intent intent = new Intent(getActivity(), DingdanActivity.class);
                    startActivity(intent);
                } else if (code.equals("1")) {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


}