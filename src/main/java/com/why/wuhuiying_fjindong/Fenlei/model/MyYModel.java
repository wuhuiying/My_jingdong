package com.why.wuhuiying_fjindong.Fenlei.model;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Fenlei.bean.YoufenleiBean;
import com.why.wuhuiying_fjindong.Fenlei.prester.MyYPrester;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 小慧莹 on 2018/1/9.
 */

public class MyYModel {
    private final MyYPrester myYPrester;

    public MyYModel(MyYPrester myYPrester) {
        this.myYPrester=myYPrester;
    }

    public void getYModelData(String shopApi) {
        OkHttpUtil.doGet(shopApi, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
             if(response.isSuccessful()){
                 String string = response.body().string();
                 YoufenleiBean youfenleiBean=new Gson().fromJson(string,YoufenleiBean.class);
                 myYPrester.Onsuccess(youfenleiBean);
             }
            }
        });
    }
}
