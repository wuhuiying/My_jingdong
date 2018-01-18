package com.why.wuhuiying_fjindong.Gouwuche.prester;

import com.why.wuhuiying_fjindong.Gouwuche.Shangpinxiangqing;
import com.why.wuhuiying_fjindong.Gouwuche.bean.MyShangPingBean;
import com.why.wuhuiying_fjindong.Gouwuche.model.MyShangPinModel;

/**
 * Created by 小慧莹 on 2018/1/10.
 */

public class MyShangPinPrester implements IMShangPinPrester{
    private final Shangpinxiangqing shangpinxiangqing;
    private final MyShangPinModel myShangPinModel;

    public MyShangPinPrester(Shangpinxiangqing shangpinxiangqing) {
        this.shangpinxiangqing=shangpinxiangqing;
        myShangPinModel = new MyShangPinModel(this);
    }

    public void getPShangpinData(String shangping) {
        myShangPinModel.getMShangpinData(shangping);
    }


    @Override
    public void Onsuccess(MyShangPingBean myShangPingBean) {
        shangpinxiangqing.Onsuccess(myShangPingBean);
    }
}
