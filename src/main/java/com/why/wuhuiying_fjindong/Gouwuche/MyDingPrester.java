package com.why.wuhuiying_fjindong.Gouwuche;

import com.why.wuhuiying_fjindong.Dingdan.bean.MyCreatDingdan;
import com.why.wuhuiying_fjindong.Gouwuche.model.MyDingModel;
import com.why.wuhuiying_fjindong.Gouwuche.prester.IMCreatPrester;
import com.why.wuhuiying_fjindong.Shouye.fragment.fraggouwuche;

/**
 * Created by 小慧莹 on 2018/1/15.
 */

public class MyDingPrester implements IMCreatPrester {
    private com.why.wuhuiying_fjindong.Shouye.fragment.fraggouwuche fraggouwuche;
    private final MyDingModel myDingModel;

    public MyDingPrester(fraggouwuche fraggouwuche) {
        this.fraggouwuche = fraggouwuche;
        myDingModel = new MyDingModel(this);
    }


    @Override
    public void Onsuccess(MyCreatDingdan myCreatDingdan) {
        fraggouwuche.Onsuccess(myCreatDingdan);
    }

    public void getDingPresterData(String createApi, String formatprice) {
        myDingModel.getDingModelData(createApi,formatprice);
    }
}
