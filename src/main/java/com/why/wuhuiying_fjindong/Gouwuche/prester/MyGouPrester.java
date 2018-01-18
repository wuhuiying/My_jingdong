package com.why.wuhuiying_fjindong.Gouwuche.prester;

import com.why.wuhuiying_fjindong.Gouwuche.bean.MyGouWuBean;
import com.why.wuhuiying_fjindong.Gouwuche.model.MyGouModel;
import com.why.wuhuiying_fjindong.Shouye.fragment.fraggouwuche;

/**
 * Created by 小慧莹 on 2018/1/11.
 */

public class MyGouPrester implements IMGouPrester {
    private final fraggouwuche fraggouwuche;
    private final MyGouModel myGouModel;

    public MyGouPrester(fraggouwuche fraggouwuche) {
        this.fraggouwuche=fraggouwuche;
        //创建m层解析数据
        myGouModel = new MyGouModel(this);
    }

    public void getGouPresterData(String getcartsApi, String uid) {
        myGouModel.getGouModelData(getcartsApi,uid);
    }


    @Override
    public void Onsuccess(MyGouWuBean myGouWuBean) {
fraggouwuche.Onsuccess(myGouWuBean);
    }
}
