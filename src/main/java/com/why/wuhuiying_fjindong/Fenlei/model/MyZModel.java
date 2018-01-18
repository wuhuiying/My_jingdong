package com.why.wuhuiying_fjindong.Fenlei.model;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Fenlei.bean.ZuoFenleiBean;
import com.why.wuhuiying_fjindong.Fenlei.prester.MyZPrester;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 小慧莹 on 2018/1/9.
 */

public class MyZModel {

    private final MyZPrester myZPrester;

    public MyZModel(MyZPrester myZPrester) {
        this.myZPrester=myZPrester;
    }

    public void getZModelData(String url) {
        OkHttpUtil.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
     if(response.isSuccessful()){
         String string = response.body().string();
         ZuoFenleiBean zuoFenleiBean=new Gson().fromJson(string,ZuoFenleiBean.class);
         myZPrester.Onsuccess(zuoFenleiBean);
     }
            }
        });
    }
}
