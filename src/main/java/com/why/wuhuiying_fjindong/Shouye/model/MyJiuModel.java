package com.why.wuhuiying_fjindong.Shouye.model;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Shouye.bean.JiuGoGeBean;
import com.why.wuhuiying_fjindong.Shouye.prester.IMJiugonggePrester;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 小慧莹 on 2018/1/8.
 */

public class MyJiuModel {
    private IMJiugonggePrester myJiugonggePrester;
    private JiuGoGeBean jiuGoGeBean;

    public MyJiuModel(IMJiugonggePrester myJiugonggePrester) {
        this.myJiugonggePrester=myJiugonggePrester;
    }

    public void getJiuModelData(String shoppingApi) {
        OkHttpUtil.doGet(shoppingApi, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String string = response.body().string();
                    jiuGoGeBean = new Gson().fromJson(string,JiuGoGeBean.class);
                }
                 myJiugonggePrester.Onsucess(jiuGoGeBean);

            }
        });
    }
}
