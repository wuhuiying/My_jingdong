package com.why.wuhuiying_fjindong.Gouwuche.model;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Dingdan.bean.MyCreatDingdan;
import com.why.wuhuiying_fjindong.Gouwuche.MyDingPrester;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 小慧莹 on 2018/1/15.
 */

public class MyDingModel {
    private final MyDingPrester myDingPrester;

    public MyDingModel(MyDingPrester myDingPrester) {
        this.myDingPrester=myDingPrester;
    }

    public void getDingModelData(String createApi,String formatprice) {
        Map<String, String> p=new HashMap<>();
        p.put("uid","11443");
        p.put("price",formatprice);
        OkHttpUtil.doPost(createApi, p, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String string = response.body().string();
                    MyCreatDingdan myCreatDingdan=new Gson().fromJson(string,MyCreatDingdan.class);
                    myDingPrester.Onsuccess(myCreatDingdan);
                }

            }
        });
    }
}
