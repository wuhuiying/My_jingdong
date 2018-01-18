package com.why.wuhuiying_fjindong.DL.bean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.why.wuhuiying_fjindong.ApiUtil.MyAPI;
import com.why.wuhuiying_fjindong.My.inters.MyJieKou;
import com.why.wuhuiying_fjindong.My.presenter.MyPresenter;
import com.why.wuhuiying_fjindong.R;


public class ZhuCe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        TextView fan = (TextView) findViewById(R.id.zhucefan);
        fan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //沉浸式
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
    }

    public void zhu(View view) {
        EditText ztel = (EditText) findViewById(R.id.ztel);
        EditText zpass = (EditText) findViewById(R.id.zpass);
        String ztelzhi = ztel.getText().toString();
        String zpasszhi = zpass.getText().toString();
        if(ztelzhi.equals("")||zpasszhi.equals("")){
            Toast.makeText(ZhuCe.this,"用户和密码不能为空！",Toast.LENGTH_SHORT).show();
        }else{
            String url= MyAPI.zhuceJieKou(ztelzhi,zpasszhi);
            MyPresenter myPresenter = new MyPresenter();
            myPresenter.getContent(url, new MyJieKou() {
                @Override
                public void onChengGong(final String json) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Gson gson = new Gson();
                            MyUserZhuCe myBean1 = gson.fromJson(json, MyUserZhuCe.class);
                                if(myBean1.getCode().equals("0")){
                                    Toast.makeText(ZhuCe.this,myBean1.getMsg(),Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ZhuCe.this, DengLu.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    Toast.makeText(ZhuCe.this,myBean1.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                        }
                    });
                }

                @Override
                public void onShiBai(final String ss) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ZhuCe.this,ss,Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            });
        }
    }
}
