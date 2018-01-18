package com.why.wuhuiying_fjindong.Shouye.model;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Shouye.bean.LuoBoBean;
import com.why.wuhuiying_fjindong.Shouye.prester.MyShouPrester;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by 小慧莹 on 2018/1/8.
 */

public class MyShouModel {
    private List<String> imgesUrl;
    private  MyShouPrester myShouPrester;

    public MyShouModel(MyShouPrester myShouPrester) {
        this.myShouPrester=myShouPrester;
    }

    public void getShouModelData(String lunbo_api) {
        OkHttpUtil.doGet(lunbo_api, new Callback() {

            private List<LuoBoBean.DataBean> data;

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
if(response.isSuccessful()){
    String string = response.body().string();

    LuoBoBean luoBoBean=new Gson().fromJson(string,LuoBoBean.class);
    myShouPrester.Onsuccess(luoBoBean);

}

            }
        });

    }
}
