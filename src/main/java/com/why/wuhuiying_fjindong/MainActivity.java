package com.why.wuhuiying_fjindong;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.why.wuhuiying_fjindong.Shouye.Shouye;


public class MainActivity extends AppCompatActivity {

    int i=0;
    Handler h=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            i++;
            if(i==3){
                Intent intent = new Intent(MainActivity.this, Shouye.class);
                startActivity(intent);
                finish();
            }else{
                h.sendEmptyMessageDelayed(0,1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        h.sendEmptyMessageDelayed(0,1000);
    }
}
