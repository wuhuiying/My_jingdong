package com.why.wuhuiying_fjindong.My.presenter;


import com.why.wuhuiying_fjindong.My.inters.MyJieKou;
import com.why.wuhuiying_fjindong.My.model.MyModel;

/**
 * Created by Administrator on 2017/12/6/006.
 */

public class MyPresenter {




    public void getContent(String url, final MyJieKou jie){
        MyModel.getContentMessge(url, new MyJieKou() {
            @Override
            public void onChengGong(String json) {
                jie.onChengGong(json);
            }

            @Override
            public void onShiBai(String ss) {
                jie.onShiBai(ss);
            }
        });
    }
}
