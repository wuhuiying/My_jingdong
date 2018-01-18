package com.why.wuhuiying_fjindong.Shouye;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.why.wuhuiying_fjindong.R;
import com.why.wuhuiying_fjindong.Shouye.fragment.FraWode;
import com.why.wuhuiying_fjindong.Shouye.fragment.fragfaxian;
import com.why.wuhuiying_fjindong.Shouye.fragment.fragfenlei;
import com.why.wuhuiying_fjindong.Shouye.fragment.fraggouwuche;
import com.why.wuhuiying_fjindong.Shouye.fragment.fragshouye;

/**
 * Created by 小慧莹 on 2018/1/8.
 */

public class Shouye extends AppCompatActivity {
    private FrameLayout frameLayout;
    private RadioButton shou;
    private RadioButton fen;
    private RadioButton fa;
    private RadioButton gou;
    private RadioButton my;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        shou = (RadioButton) findViewById(R.id.shou);
        fen = (RadioButton) findViewById(R.id.fen);
        fa = (RadioButton) findViewById(R.id.fa);
        gou = (RadioButton) findViewById(R.id.gou);
        my = (RadioButton) findViewById(R.id.my);
        //刚进页面显示首页的内容
        rg.check(R.id.shou);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fragshouye()).commit();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                if(i==R.id.shou){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fragshouye()).commit();
                }
                if(i==R.id.fen){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fragfenlei()).commit();
                }
                if(i==R.id.fa){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fragfaxian()).commit();
                }
                if(i==R.id.gou){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new fraggouwuche()).commit();
                }
                if(i==R.id.my){
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new FraWode()).commit();
                }


            }
        });

    }
}