package com.why.wuhuiying_fjindong.Fenlei.prester;

import com.why.wuhuiying_fjindong.Fenlei.Youfrag;
import com.why.wuhuiying_fjindong.Fenlei.bean.YoufenleiBean;
import com.why.wuhuiying_fjindong.Fenlei.model.MyYModel;

/**
 * Created by 小慧莹 on 2018/1/9.
 */

public class MyYPrester implements IMYPrester {
    private  Youfrag youfrag;
    private final MyYModel myYModel;

    public MyYPrester(Youfrag youfrag) {
        this.youfrag=youfrag;
        myYModel = new MyYModel(this);
    }

    public void getYPresterData(String shopApi) {
        myYModel.getYModelData(shopApi);

    }

    @Override
    public void Onsuccess(YoufenleiBean youfenleiBean) {
        youfrag.Onsuccess(youfenleiBean);

    }
}
