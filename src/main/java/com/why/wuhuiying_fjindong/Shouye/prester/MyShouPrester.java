package com.why.wuhuiying_fjindong.Shouye.prester;

import com.why.wuhuiying_fjindong.Shouye.bean.JiuGoGeBean;
import com.why.wuhuiying_fjindong.Shouye.bean.LuoBoBean;
import com.why.wuhuiying_fjindong.Shouye.fragment.fragshouye;
import com.why.wuhuiying_fjindong.Shouye.model.MyShouModel;

/**
 * Created by 小慧莹 on 2018/1/8.
 */

public class MyShouPrester implements IMShouPrester{
    private  fragshouye fragshouye;
    private final MyShouModel myShouModel;

    public MyShouPrester(fragshouye fragshouye) {
        this.fragshouye=fragshouye;
        myShouModel = new MyShouModel(this);

    }


    public void getShouPresterData(String lunbo_api) {
        myShouModel.getShouModelData(lunbo_api);
    }

    @Override
    public void Onsuccess(LuoBoBean luoBoBean) {
        fragshouye.Onsuccess(luoBoBean);
    }

}
