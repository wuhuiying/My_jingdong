package com.why.wuhuiying_fjindong.Gouwuche.model;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Gouwuche.bean.MyShangPingBean;
import com.why.wuhuiying_fjindong.Gouwuche.prester.MyShangPinPrester;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 小慧莹 on 2018/1/10.
 */

public class MyShangPinModel {
    private final MyShangPinPrester myShangPinPrester;

    public MyShangPinModel(MyShangPinPrester myShangPinPrester) {
        this.myShangPinPrester=myShangPinPrester;
    }

    public void getMShangpinData(String shangping) {
        OkHttpUtil.doGet(shangping, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                   if(response.isSuccessful()){
                       String string = response.body().string();
                       MyShangPingBean myShangPingBean=new Gson().fromJson(string,MyShangPingBean.class);
                       myShangPinPrester.Onsuccess(myShangPingBean);
                   }
            }
        });
    }
}
