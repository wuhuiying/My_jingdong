package com.why.wuhuiying_fjindong.Shouye.prester;

import com.why.wuhuiying_fjindong.Shouye.bean.JiuGoGeBean;
import com.why.wuhuiying_fjindong.Shouye.fragment.fragshouye;
import com.why.wuhuiying_fjindong.Shouye.model.MyJiuModel;

/**
 * Created by 小慧莹 on 2018/1/8.
 */

public class MyJiugonggePrester implements IMJiugonggePrester{
    private  fragshouye fragshouye;
    private final MyJiuModel myJiuModel;

    public MyJiugonggePrester(fragshouye fragshouye) {
        this.fragshouye=fragshouye;
        myJiuModel = new MyJiuModel(this);
    }

    public void getJiuPresterData(String shoppingApi) {
        myJiuModel.getJiuModelData(shoppingApi);

    }

    @Override
    public void Onsucess(JiuGoGeBean jiuGoGeBean) {
        fragshouye.Onsuccess(jiuGoGeBean);
    }
}
