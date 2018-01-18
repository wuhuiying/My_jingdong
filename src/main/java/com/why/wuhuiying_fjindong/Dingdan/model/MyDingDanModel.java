package com.why.wuhuiying_fjindong.Dingdan.model;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Dingdan.bean.MyDingdanBean;
import com.why.wuhuiying_fjindong.Dingdan.prester.MyDingDanPrester;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 小慧莹 on 2018/1/15.
 */

public class MyDingDanModel {
    private MyDingDanPrester myDingDanPrester;

    public MyDingDanModel(MyDingDanPrester myDingDanPrester) {
        this.myDingDanPrester = myDingDanPrester;
    }

    public void getDingdanModelData(String getorderPai, int page) {
        Map<String, String> p=new HashMap<>();
        p.put("uid","11443");
        p.put("page",page+"");
        OkHttpUtil.doPost(getorderPai, p, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String string = response.body().string();
                    MyDingdanBean myDingdanBean=new Gson().fromJson(string,MyDingdanBean.class);
                    myDingDanPrester.Onsuccess(myDingdanBean);

                }

            }
        });
    }
}
