package com.why.wuhuiying_fjindong.Shouye.prester;


import com.why.wuhuiying_fjindong.Shouye.bean.SearchBean;
import com.why.wuhuiying_fjindong.Shouye.model.MySeachModel;
import com.why.wuhuiying_fjindong.Shouye.SearchActivity;

/**
 * Created by 小慧莹 on 2018/1/6.
 */

public class MySearchPrester implements IMSearchPrester {
    private SearchActivity searchActivity;
    private MySeachModel mySeachModel;

    public MySearchPrester(SearchActivity searchActivity) {
        this.searchActivity=searchActivity;
        mySeachModel = new MySeachModel(this);
    }

    public void getSearchPrester(String guanjianApi, String keywords) {
mySeachModel.getSearchModelData(guanjianApi,keywords);
    }

    @Override
    public void Onsuccess(SearchBean searchBean) {
        searchActivity.Onsuccess(searchBean);

    }
}
