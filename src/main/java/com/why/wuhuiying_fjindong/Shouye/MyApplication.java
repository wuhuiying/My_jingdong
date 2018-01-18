package com.why.wuhuiying_fjindong.Shouye;

import android.app.Application;

import com.dash.zxinglibrary.activity.ZXingLibrary;
import com.why.wuhuiying_fjindong.ImageLoderutils.ImageLoaderUtil;


public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化zxing库
        ZXingLibrary.initDisplayOpinion(this);
        ImageLoaderUtil.getImag(this);

    }
}
