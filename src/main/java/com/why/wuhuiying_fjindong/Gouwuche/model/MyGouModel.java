package com.why.wuhuiying_fjindong.Gouwuche.model;


import android.util.Log;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.OkHttpUtil;
import com.why.wuhuiying_fjindong.Gouwuche.bean.MyGouWuBean;
import com.why.wuhuiying_fjindong.Gouwuche.prester.MyGouPrester;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


/**
 * Created by 小慧莹 on 2018/1/11.
 */

public class MyGouModel {
    private final MyGouPrester myGouPrester;

    public MyGouModel(MyGouPrester myGouPrester) {
        this.myGouPrester = myGouPrester;
    }

    public void getGouModelData(String getcartsApi, String uid) {
        Map<String, String> p=new HashMap<>();
        p.put("uid","11443");
        p.put("source","android");
        OkHttpUtil.doPost(getcartsApi, p, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){

                    String json = response.body().string();
                    Log.d("WHY+++++","++++++++++++++++++++"+json);
                    if ("null".equals(json)){

                        myGouPrester.Onsuccess(null);

                    }else {
                        //解析
                        MyGouWuBean myGouWuBean = new Gson().fromJson(json,MyGouWuBean.class);


                        //接口回调...presenter层
                        myGouPrester.Onsuccess(myGouWuBean);
                    }



                }
            }
        });


    }

}