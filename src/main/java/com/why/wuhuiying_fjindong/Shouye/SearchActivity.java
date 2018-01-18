package com.why.wuhuiying_fjindong.Shouye;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.why.wuhuiying_fjindong.ApiUtil.API;
import com.why.wuhuiying_fjindong.Gouwuche.Shangpinxiangqing;
import com.why.wuhuiying_fjindong.R;
import com.why.wuhuiying_fjindong.Shouye.adapter.MySearchAdapter.SearchAdapter;
import com.why.wuhuiying_fjindong.Shouye.adapter.MySearchAdapter.SearchAdapter1;
import com.why.wuhuiying_fjindong.Shouye.bean.SearchBean;
import com.why.wuhuiying_fjindong.Shouye.prester.MySearchPrester;
import com.why.wuhuiying_fjindong.Shouye.view.IMSearchView;

import java.net.URLEncoder;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements IMSearchView {
    private EditText sou;
    private RecyclerView recyclerView;
    private CheckBox checkBox;

    boolean flag = false;
    private MySearchPrester mySearchPrester;
    private List<SearchBean.DataBean> data;


    private SearchAdapter searchAdapter;
    private TextView tv_sousuo;

    private TextView fanhui;
    private String keywords;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlayout);
        //沉浸式
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        findView();
        //建立一个中间者
        mySearchPrester = new MySearchPrester(this);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");

        tv_sousuo.setText(name);
        keywords = tv_sousuo.getText().toString();
        if (keywords == null) {
            Toast.makeText(SearchActivity.this, "请输入搜索的内容", Toast.LENGTH_SHORT).show();
        } else {
            mySearchPrester.getSearchPrester(API.GUANJIAN_API, keywords);
        }


    }

    private void findView() {

        recyclerView = findViewById(R.id.recycler_view);
        tv_sousuo = findViewById(R.id.tv_sousuo);
        checkBox = findViewById(R.id.checkbox);
        fanhui = findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    @Override
    public void Onsuccess(final SearchBean searchBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                data = searchBean.getData();
                Toast.makeText(SearchActivity.this, searchBean.getMsg(), Toast.LENGTH_SHORT).show();
                //设置适配器
                setAdapter(data);
                checkBox.setChecked(flag);
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (flag) {
                            //设置适配器

                            setAdapter(data);
                            checkBox.setChecked(false);
                            flag = checkBox.isChecked();
                        } else {
                            SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this, data);
                            recyclerView.setAdapter(searchAdapter);
                            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                            checkBox.setChecked(true);
                            flag = checkBox.isChecked();

                            searchAdapter.setOnItemClilkListener(new SearchAdapter.OnitemclickListener() {
                                @Override
                                public void OnItemClilkListener(View view, int postion) {
                                    Toast.makeText(SearchActivity.this, "点击" + postion, Toast.LENGTH_SHORT).show();


                                }
                            });
                        }
                    }
                });
            }
        });
    }

    private void setAdapter(List<SearchBean.DataBean> data) {
        SearchAdapter1 searchAdapter1 = new SearchAdapter1(SearchActivity.this, data);
        recyclerView.setAdapter(searchAdapter1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //用自定义的方法给适配器设置点击事件
        searchAdapter1.setOnItemClilkListener(new SearchAdapter1.Onitemclick() {
            @Override
            public void OnItemClilkListener(View view, int postion) {
                Toast.makeText(SearchActivity.this, "点击" + postion, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
