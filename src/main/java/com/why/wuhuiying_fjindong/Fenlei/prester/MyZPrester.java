package com.why.wuhuiying_fjindong.Fenlei.prester;

import android.support.v4.app.FragmentActivity;

import com.why.wuhuiying_fjindong.ApiUtil.API;
import com.why.wuhuiying_fjindong.Fenlei.bean.ZuoFenleiBean;
import com.why.wuhuiying_fjindong.Fenlei.model.MyZModel;
import com.why.wuhuiying_fjindong.Shouye.fragment.fragfenlei;

/**
 * Created by 小慧莹 on 2018/1/9.
 */

public class MyZPrester implements IMZPrester {
    private  fragfenlei fragfenlei;
    private final MyZModel myZModel;

    public MyZPrester(fragfenlei fragfenlei) {
        this.fragfenlei=fragfenlei;
        //创建model层获取数据
        myZModel = new MyZModel(this);
    }



    public void getZPresterData(String url) {
        myZModel.getZModelData(url);

    }

    @Override
    public void Onsuccess(ZuoFenleiBean zuoFenleiBean) {
        fragfenlei.Onsuccess(zuoFenleiBean);
    }
}
