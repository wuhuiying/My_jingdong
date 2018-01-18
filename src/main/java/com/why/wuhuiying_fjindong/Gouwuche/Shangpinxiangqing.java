package com.why.wuhuiying_fjindong.Gouwuche;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stx.xhb.xbanner.XBanner;
import com.why.wuhuiying_fjindong.ApiUtil.API;
import com.why.wuhuiying_fjindong.Fangda.FangdaImageActivity;
import com.why.wuhuiying_fjindong.Gouwuche.bean.MyShangPingBean;
import com.why.wuhuiying_fjindong.Gouwuche.prester.MyShangPinPrester;
import com.why.wuhuiying_fjindong.Gouwuche.view.IMShangpinView;
import com.why.wuhuiying_fjindong.R;


import java.util.ArrayList;
import java.util.List;

public class Shangpinxiangqing extends AppCompatActivity implements IMShangpinView {
    private TextView shangjianame;
    //    private ImageView shangjiaimag;
//    private ImageView shangpinimag;
    private TextView shangpinprice;
    private TextView shangpintitle;
    private Button addCart;

    private Button getCart;
    private SharedPreferences whypreferences;
    private MyShangPinPrester myShangPinPrester;

    private String pid;
    private List<String> images;
    private XBanner xbanner;
    private TextView barginprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shangping_xiang_qing);
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //获取控件
        findView();
        //接收搜索页面，和首页为你推荐传过来的值
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");


        //定义中间者
        myShangPinPrester = new MyShangPinPrester(this);
        myShangPinPrester.getPShangpinData(API.DETAL_API + pid);

        addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Shangpinxiangqing.this, "您的商品已经加入购物车", Toast.LENGTH_SHORT).show();
            }
        });

        xbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, int position) {
                Intent in=new Intent(Shangpinxiangqing.this, FangdaImageActivity.class);
                startActivity(in);
            }
        });


//        whypreferences = getSharedPreferences("why", Context.MODE_PRIVATE);
//        final boolean kai = whypreferences.getBoolean("kai", false);
//        Bundle bundle = getIntent().getExtras();
//        MyGouWuBean.DataBean databean = (MyGouWuBean.DataBean) bundle.getSerializable("databean");
//        final int pid = databean.getPid();
//        String shangping = MyAPI.shangping(pid + "");
//        //定义中间者
//        myShangPinPrester = new MyShangPinPrester(this);
//        myShangPinPrester.getPShangpinData(shangping);
//        addCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               if(kai){
//                   String uid = whypreferences.getString("uid", "");
//                   String s = MyAPI.addCar(uid, pid);
////                   //定义一个加入购物车的中间层
////                   myJiaruPrester = new MyJiaruPrester(this);
////                  myJiaruPrester.getJiaruPrester(s);
//                   Toast.makeText(Shangpinxiangqing.this,"您的商品已经添加到购物车",Toast.LENGTH_SHORT).show();
//
//
//               }
//            }
//        });
//
//    }
    }

    private void findView() {
        shangjianame = (TextView) findViewById(R.id.shangjianame);
        xbanner = findViewById(R.id.xbanner);
//        shangpinimag = (ImageView) findViewById(R.id.shangpinimag);
        shangpinprice = (TextView) findViewById(R.id.shangpinprice);
        barginprice = findViewById(R.id.barginprice);
        shangpintitle = (TextView) findViewById(R.id.shangpintitle);
        addCart = (Button) findViewById(R.id.addCart);
        getCart = (Button) findViewById(R.id.getCart);
    }

    @Override
    public void Onsuccess(final MyShangPingBean myShangPingBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                images = new ArrayList<>();
                String imageslist = myShangPingBean.getData().getImages();
                Log.e("WHY____=+++=+", imageslist.toString());
                String[] split = imageslist.split("\\|");
                //图片循环添加集合
                for (String string : split) {
                    images.add(string);
                }
                xbanner.setData(images, null);
                xbanner.setPoinstPosition(XBanner.BOTTOM);
                xbanner.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(Shangpinxiangqing.this).load(images.get(position)).into((ImageView) view);
                    }
                });
                shangpintitle.setText(myShangPingBean.getData().getTitle());
                shangpinprice.setText("原价" + myShangPingBean.getData().getBargainPrice());

                barginprice.setText("现在" + myShangPingBean.getData().getPrice());
                shangpinprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线

            }
        });
    }

//    @Override
//    public void Onsuccess(MyaddCart myaddCart) {
//
//    }
}
