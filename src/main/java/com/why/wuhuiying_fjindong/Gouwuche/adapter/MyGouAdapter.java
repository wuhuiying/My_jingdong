package com.why.wuhuiying_fjindong.Gouwuche.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.why.wuhuiying_fjindong.ApiUtil.API;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Gouwuche.bean.CountPriceBean;
import com.why.wuhuiying_fjindong.Gouwuche.bean.MyGouWuBean;
import com.why.wuhuiying_fjindong.Gouwuche.prester.MyGouPrester;
import com.why.wuhuiying_fjindong.R;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 小慧莹 on 2018/1/11.
 */

public class MyGouAdapter extends BaseExpandableListAdapter {
    private final FragmentActivity activity;
    private MyGouWuBean myGouWuBean;
    private Handler handler;
    private MyGouPrester myGouPrester;
    private RelativeLayout relative_progress;
    private int childIndex;
    private final SharedPreferences sp;
    private final SharedPreferences.Editor edit;
    private String uid;
    private int allIndex;
    private static final String TAG = "MyGouAdapter";

    public MyGouAdapter(FragmentActivity activity, MyGouWuBean myGouWuBean, Handler handler, MyGouPrester myGouPrester, RelativeLayout relative_progress) {

        this.activity = activity;
        this.myGouWuBean = myGouWuBean;
        this.handler = handler;
        this.myGouPrester = myGouPrester;
        this.relative_progress = relative_progress;
        sp = activity.getSharedPreferences("why", Context.MODE_PRIVATE);
        edit = sp.edit();
        uid = sp.getString("uid", "");
    }




    @Override
    public int getGroupCount() {
        return myGouWuBean.getData().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return myGouWuBean.getData().get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return myGouWuBean.getData().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPostion, int childPosition) {
        return myGouWuBean.getData().get(groupPostion).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        final GroupHolder groupHolder;
        //找到组的布局
        if (view == null) {
            view = View.inflate(activity, R.layout.gouwuche_group, null);
            groupHolder = new GroupHolder();
            groupHolder.group_check = view.findViewById(R.id.group_check_01);
            groupHolder.group_text = view.findViewById(R.id.group_text_01);
            view.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) view.getTag();
        }
        final MyGouWuBean.DataBean dataBean = myGouWuBean.getData().get(groupPosition);
        //赋值
        groupHolder.group_check.setChecked(dataBean.isGroup_check());
        groupHolder.group_text.setText(dataBean.getSellerName());
        //一级列表的点击事件
        groupHolder.group_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐藏进度条
                relative_progress.setVisibility(View.VISIBLE);
                /**
                 * 点击一级列表的时候，子条目需要一个个的去执行更新，全部执行完之后再去请求查询购物车
                 使用递归的方法，例如一组里面三个孩子，0，1，2
                 */
                childIndex = 0;
                //更新购物车
                updataChildCheckedInGroup(groupHolder.group_check.isChecked(), dataBean);

            }
        });
        return view;
    }

    /**
     * 点击一级列表的时候，去改变所有子条目的状态
     *
     * @param checked
     * @param dataBean
     */
    private void updataChildCheckedInGroup(final boolean checked, final MyGouWuBean.DataBean dataBean) {
        MyGouWuBean.DataBean.ListBean listBean = dataBean.getList().get(childIndex);
        //请求更新购物车的接口...更新成功之后,再次请求查询购物车的接口,进行数据的展示
        Map<String, String> p = new HashMap<>();
        p.put("uid", "11443");
        p.put("sellerid", String.valueOf(listBean.getSellerid()));
        p.put("pid", String.valueOf(listBean.getPid()));
        p.put("selected", String.valueOf(checked ? 1 : 0));
        p.put("num", String.valueOf(listBean.getNum()));

        OkHttpUtil.doPost(API.UPDATECARTS_API, p, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    childIndex++;
                    if (childIndex < dataBean.getList().size()) {
                        //继续更新
                        updataChildCheckedInGroup(checked, dataBean);

                    } else {
                        //请求查询购物车的操作，重新展示数据
                        myGouPrester.getGouPresterData(API.GETCARTS_API,uid);
                    }
                }
            }
        });


    }

    @Override
    public View getChildView(int groupPosition, int childPostion, boolean b, View view, ViewGroup viewGroup) {
        final ChildHolder childHolder;
        if (view == null) {
            view = View.inflate(activity, R.layout.gouwuche_child, null);
            childHolder = new ChildHolder();
            //获取控件
            childHolder.child_check = view.findViewById(R.id.child_check_01);
            childHolder.text_title = view.findViewById(R.id.child_title);
            childHolder.child_image = view.findViewById(R.id.child_image_01);
            childHolder.text_price = view.findViewById(R.id.child_price);
            childHolder.text_add = view.findViewById(R.id.text_add);
            childHolder.text_jian = view.findViewById(R.id.text_jian);
            childHolder.text_num = view.findViewById(R.id.text_num);
            childHolder.text_delete = view.findViewById(R.id.text_delete_01);

            view.setTag(childHolder);
        } else {
            childHolder = (ChildHolder) view.getTag();
        }
        final MyGouWuBean.DataBean.ListBean listBean = myGouWuBean.getData().get(groupPosition).getList().get(childPostion);
        //赋值
        childHolder.text_title.setText(listBean.getTitle());
        childHolder.text_price.setText("¥" + listBean.getBargainPrice());
        String[] images = listBean.getImages().split("\\|");
        Glide.with(activity).load(images[0]).into(childHolder.child_image);
        //setText()我们使用一定是设置字符串,不然运行时候会报错
        childHolder.text_num.setText(listBean.getNum() + "");
        //根据0，1设置是否选中
        childHolder.child_check.setChecked(listBean.getSelected() == 0 ? false : true);

        /**
         * 点击进行购物车操作，给checkbox设置点击事件更新是否选中
         */
        childHolder.child_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //进度条显示
                relative_progress.setVisibility(View.VISIBLE);
                //uid=71&sellerid=1&pid=1&selected=0&num=10
                Map<String, String> p = new HashMap<>();
                p.put("uid", "11443");
                p.put("sellerid", String.valueOf(listBean.getSellerid()));
                p.put("pid", String.valueOf(listBean.getPid()));
                p.put("selected", String.valueOf(listBean.getSelected() == 0?1:0));//listBean.getSelected()...0--->1,,,1--->0
                p.put("num", String.valueOf(listBean.getNum()));
                Log.e(TAG, "onResponse: "+String.valueOf(listBean.getNum()) );
                OkHttpUtil.doPost(API.UPDATECARTS_API, p, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e(TAG, "onResponse: "+response.body().string() );
                        if (response.isSuccessful()) {
                            //更新成功之后,再次请求查询购物车的接口,进行数据的展示[]]

                            myGouPrester.getGouPresterData(API.GETCARTS_API, uid);
                        }

                    }
                });
            }
        });
        //加
        childHolder.text_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "onClick: "+"===============" );
                //进度条要显示
                relative_progress.setVisibility(View.VISIBLE);
                Map<String, String> p = new HashMap<>();
                p.put("uid", "11443");
                p.put("sellerid", String.valueOf(listBean.getSellerid()));
                p.put("pid", String.valueOf(listBean.getPid()));
                p.put("selected", String.valueOf(listBean.getSelected()));
                p.put("num", String.valueOf(listBean.getNum() + 1));

                OkHttpUtil.doPost(API.UPDATECARTS_API, p, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            myGouPrester.getGouPresterData(API.GETCARTS_API, uid);
                        }
                    }
                });
            }
        });

        //减
        childHolder.text_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = listBean.getNum();
                if (num == 1) {
                    return;
                }

                //progressBar要显示
                relative_progress.setVisibility(View.VISIBLE);
                Map<String, String> p = new HashMap<>();
                p.put("uid", "11443");
                p.put("sellerid", String.valueOf(listBean.getSellerid()));
                p.put("pid", String.valueOf(listBean.getPid()));
                p.put("selected", String.valueOf(listBean.getSelected()));
                p.put("num", String.valueOf(num - 1));
                OkHttpUtil.doPost(API.UPDATECARTS_API, p, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            myGouPrester.getGouPresterData(API.GETCARTS_API, uid);
                        }
                    }
                });

            }
        });
//删除
        childHolder.text_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> p = new HashMap<>();
                p.put("uid", "11443");
                p.put("pid", String.valueOf(listBean.getPid()));

                OkHttpUtil.doPost(API.deleteCartUrl, p, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            String string = response.body().string();
                            myGouPrester.getGouPresterData(API.GETCARTS_API, uid);
                        }
                    }
                });
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 计算总价和商品的数量
     */
    public void setPriceAndCount() {
        //双精度double计算价格
        double price = 0;
        int count = 0;
        for (int i = 0; i < myGouWuBean.getData().size(); i++) {
            List<MyGouWuBean.DataBean.ListBean> list = myGouWuBean.getData().get(i).getList();
            for (int j = 0; j < list.size(); j++) {
                MyGouWuBean.DataBean.ListBean listBean = list.get(j);
                //选中的时候计算数量和价格
                if (listBean.getSelected() == 1) {
                    price += listBean.getBargainPrice() * listBean.getNum();
                    count += listBean.getNum();
                }
            }
        }

        //给价钱格式转换一下，以免出现一串数字
        DecimalFormat decimalFormat = new DecimalFormat("0.00"); //使用java下的包
        String formatprice = decimalFormat.format(price);

        CountPriceBean countPriceBean = new CountPriceBean(formatprice, count);
        //发送给Activity显示
        Message msg = Message.obtain();
        msg.what = 0;
        msg.obj = countPriceBean;
        handler.sendMessage(msg);
    }

    /**
     * 更新子条目状态，跟着全选状态改变
     *
             * @param checked
     */
            public void setAllChildChecked(boolean checked) {
                //显示进度条
                relative_progress.setVisibility(View.VISIBLE);
               ArrayList<MyGouWuBean.DataBean.ListBean> allList = new ArrayList<>();
                for (int i = 0; i < myGouWuBean.getData().size(); i++) {
                    for (int j = 0; j < myGouWuBean.getData().get(i).getList().size(); j++) {
                        allList.add(myGouWuBean.getData().get(i).getList().get(j));
                    }
                }
                //更新子条目，用递归思想
                allIndex = 0;
                updateAllChild(checked, allList);


            }

            /**
             * 更新所有的子条目
             *
             * @param checked
             * @param allList
             */
            private void updateAllChild(final boolean checked, final ArrayList<MyGouWuBean.DataBean.ListBean> allList) {
                MyGouWuBean.DataBean.ListBean listBean = allList.get(allIndex);
                Map<String, String> p = new HashMap<>();
                p.put("uid", "11443");
                p.put("sellerid", String.valueOf(listBean.getSellerid()));
                p.put("pid", String.valueOf(listBean.getPid()));
                p.put("selected", String.valueOf(checked?1:0));
                p.put("num", String.valueOf(listBean.getNum()));

                OkHttpUtil.doPost(API.UPDATECARTS_API, p, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    allIndex++;
                    if (allIndex < allList.size()) {
                        updateAllChild(checked, allList);
                    } else {
                       //继续查询
                        myGouPrester.getGouPresterData(API.GETCARTS_API, uid);
                    }
                }
            }
        });

    }

    private class GroupHolder {
        CheckBox group_check;
        TextView group_text;
    }

    private class ChildHolder {
        CheckBox child_check;
        ImageView child_image;
        TextView text_title;
        TextView text_price;
        TextView text_num;
        TextView text_jian;
        TextView text_add;
        TextView text_delete;
    }
}
