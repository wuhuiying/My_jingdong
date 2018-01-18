package com.why.wuhuiying_fjindong.Dingdan.prester;

import com.why.wuhuiying_fjindong.Dingdan.bean.MyDingdanBean;
import com.why.wuhuiying_fjindong.Dingdan.fragment.Frag_Daizhifu;
import com.why.wuhuiying_fjindong.Dingdan.fragment.Frag_Yizhifu;
import com.why.wuhuiying_fjindong.Dingdan.model.MyDingDanModel;


/**
 * Created by 小慧莹 on 2018/1/15.
 */

public class MyDingDanPrester implements IMDingdanPrester {
    private Frag_Daizhifu frag_daizhifu;
    private Frag_Yizhifu frag_yizhifu;
    private  MyDingDanModel myDingDanModel;

    public MyDingDanPrester(Frag_Daizhifu frag_daizhifu) {

        this.frag_daizhifu = frag_daizhifu;


        //创建modelceng
        myDingDanModel = new MyDingDanModel(this);
    }



    public void getDingdanPresterData(String getorderPai, int page) {
        myDingDanModel.getDingdanModelData(getorderPai,page);
    }

    @Override
    public void Onsuccess(MyDingdanBean myDingdanBean) {
        frag_daizhifu.Onsuccess(myDingdanBean);
    }
}
