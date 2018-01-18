package com.why.wuhuiying_fjindong.My.model;

import android.util.Log;

import com.why.wuhuiying_fjindong.My.inters.MyJieKou;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Administrator on 2017/12/6/006.
 */

public class MyModel {
    public static void getContentMessge(String url, final MyJieKou jie){
        OkHttpClient okHttpClient = new OkHttpClient();
        Request build = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(build);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                jie.onShiBai("请求失败！");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String ss=response.body().string();
                Log.i("jiba","======购物"+ss);
                jie.onChengGong(ss);
            }
        });
    }
}
