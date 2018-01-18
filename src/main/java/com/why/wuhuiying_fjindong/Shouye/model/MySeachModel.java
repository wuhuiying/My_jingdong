package com.why.wuhuiying_fjindong.Shouye.model;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Shouye.bean.SearchBean;
import com.why.wuhuiying_fjindong.Shouye.prester.MySearchPrester;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by 小慧莹 on 2018/1/6.
 */

public class MySeachModel {
    private MySearchPrester mySearchPrester;

    public MySeachModel(MySearchPrester mySearchPrester) {
        this.mySearchPrester=mySearchPrester;
    }

    public void getSearchModelData(String guanjianApi, String keywords) {
        Map<String, String> p=new HashMap<>();
        p.put("keywords",keywords);
        p.put("page","1");
        p.put("source","android");
        OkHttpUtil.doPost(guanjianApi, p, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {


                if(response.isSuccessful()){
                    String string = response.body().string();
                    SearchBean searchBean=new Gson().fromJson(string,SearchBean.class);
                    mySearchPrester.Onsuccess(searchBean);
                }
            }
        });
    }
}
