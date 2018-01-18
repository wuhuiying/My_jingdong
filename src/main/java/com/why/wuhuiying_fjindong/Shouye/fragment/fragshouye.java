package com.why.wuhuiying_fjindong.Shouye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.dash.zxinglibrary.activity.CaptureActivity;
import com.dash.zxinglibrary.activity.CodeUtils;
import com.stx.xhb.xbanner.XBanner;
import com.why.wuhuiying_fjindong.ApiUtil.API;
import com.why.wuhuiying_fjindong.Gouwuche.Shangpinxiangqing;
import com.why.wuhuiying_fjindong.MainActivity;
import com.why.wuhuiying_fjindong.R;
import com.why.wuhuiying_fjindong.Shouye.CustomCaptrueActivity;
import com.why.wuhuiying_fjindong.Shouye.Resou.ResouActivity;
import com.why.wuhuiying_fjindong.Shouye.adapter.MYTuijian.MyTuijianAdpter;
import com.why.wuhuiying_fjindong.Shouye.adapter.MyJiugonggeAapter.MyListAdpter;
import com.why.wuhuiying_fjindong.Shouye.adapter.MyMiaoshaAdpter.MiaoshaAdpter;
import com.why.wuhuiying_fjindong.Shouye.bean.JiuGoGeBean;
import com.why.wuhuiying_fjindong.Shouye.bean.LuoBoBean;
import com.why.wuhuiying_fjindong.Shouye.prester.MyJiugonggePrester;
import com.why.wuhuiying_fjindong.Shouye.prester.MyShouPrester;
import com.why.wuhuiying_fjindong.Shouye.view.IMJiuView;
import com.why.wuhuiying_fjindong.Shouye.view.IMShouVIew;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 小慧莹 on 2018/1/8.
 */

public class fragshouye extends Fragment implements IMShouVIew, IMJiuView {


    //秒杀相关
    private long mHour = 02;
    private long mMin = 22;
    private long mSecond = 22;
    private boolean isRun = true;
    private TextView tvHour;
    private TextView tvMinute;
    private TextView tvSecond;

    private static final int REQUEST_CODE = 2;
    private RecyclerView rlv_miaosha;
    private RecyclerView rlv_tuijian;
    private ViewFlipper vf;
    private TextView sousuo;
    private XBanner xBanner;
    private MyShouPrester myShouPrester;

    private List<LuoBoBean.MiaoshaBean.ListBeanX> miaoshalist;
    private List<LuoBoBean.TuijianBean.ListBean> tuijianlist;
    private List<JiuGoGeBean.DataBean> jiugonggelist;
    private RecyclerView jiu;
    private List<String> imgesUrl;
    private MyJiugonggePrester myJiugonggePrester;
    private ImageView erweima_iv;

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                computeTime();
                if (mHour < 10) {
                    tvHour.setText("0" + mHour + "");
                } else {
                    tvHour.setText("0" + mHour + "");
                }
                if (mMin < 10) {
                    tvMinute.setText("0" + mMin + "");
                } else {
                    tvMinute.setText(mMin + "");
                }
                if (mSecond < 10) {
                    tvSecond.setText("0" + mSecond + "");
                } else {
                    tvSecond.setText(mSecond + "");
                }
            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye_layout, container, false);

        xBanner = view.findViewById(R.id.xbanner);
        jiu = view.findViewById(R.id.rec_jiu);
        rlv_miaosha = view.findViewById(R.id.rlv_miaosha);
        rlv_tuijian = view.findViewById(R.id.rlv_tuijian);
        vf = view.findViewById(R.id.vf);
        sousuo = view.findViewById(R.id.tv_sousuo);
        erweima_iv = view.findViewById(R.id.erweima);
        tvMinute = view.findViewById(R.id.tv_minute);
        tvHour = view.findViewById(R.id.tv_hour);
        tvSecond = view.findViewById(R.id.tv_second);
        startRun();
        //给二维码控件设置点击事件
        erweima_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "正在打开二维码", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 2);

            }
        });
        //建立中间层
        myShouPrester = new MyShouPrester(this);
        myJiugonggePrester = new MyJiugonggePrester(this);

        myJiugonggePrester.getJiuPresterData(API.SHOPPING_API);
        myShouPrester.getShouPresterData(API.Lunbo_API);
        //跳转到热搜页面
        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ResouActivity.class);
                startActivity(intent);
            }
        });


        return view;
    }

    /**
     * 秒杀倒计时
     * 开启倒计时
     */
    private void startRun() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (isRun) {
                    try {
                        Thread.sleep(1000); // sleep 1000ms
                        Message message = Message.obtain();
                        message.what = 1;
                        handler.sendMessage(message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
            }
        }
    }


    @Override
    public void Onsuccess(final LuoBoBean luoBoBean) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //获取秒杀的适配器
                miaoshalist = luoBoBean.getMiaosha().getList();
                MiaoshaAdpter miaoshaAdpter = new MiaoshaAdpter(getActivity(), miaoshalist);
                rlv_miaosha.setAdapter(miaoshaAdpter);
                rlv_miaosha.setLayoutManager(new LinearLayoutManager(getActivity(), OrientationHelper.HORIZONTAL, false));
                //获取推荐的适配器
                tuijianlist = luoBoBean.getTuijian().getList();
                MyTuijianAdpter myTuijianAdpter = new MyTuijianAdpter(getActivity(), tuijianlist);
                rlv_tuijian.setAdapter(myTuijianAdpter);
                rlv_tuijian.setLayoutManager(new GridLayoutManager(getActivity(), 2, OrientationHelper.VERTICAL, false));
                /**
                 *给推荐适配器设置点击事件，传值
                 */
                myTuijianAdpter.setOnItemclickListner(new MyTuijianAdpter.OnItemclickListner() {
                    @Override
                    public void OnItemclick(int position) {
                        Intent intent = new Intent(getActivity(), Shangpinxiangqing.class);
                        intent.putExtra("pid", luoBoBean.getTuijian().getList().get(position).getPid() + "");
                        Log.e("WHY____++++", String.valueOf(luoBoBean.getTuijian().getList().get(position).getPid()));
                        startActivity(intent);
                    }
                });
                //轮播
                List<LuoBoBean.DataBean> data = luoBoBean.getData();

                imgesUrl = new ArrayList<>();
                for (LuoBoBean.DataBean dataBean : data) {
                    String icon1 = dataBean.getIcon();
                    imgesUrl.add(icon1);
                }
                xBanner.setData(imgesUrl, null);
                xBanner.setPoinstPosition(XBanner.RIGHT);
                xBanner.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, View view, int position) {
                        Glide.with(getActivity()).load(imgesUrl.get(position)).into((ImageView) view);
                    }
                });
                //秒杀
                miaoshalist = luoBoBean.getMiaosha().getList();

                //推荐
                tuijianlist = luoBoBean.getTuijian().getList();

            }
        });

    }


    //跑马灯

    private void PaoMaD() {
        vf.addView(View.inflate(getActivity(), R.layout.frag_sy_gg, null));
        vf.addView(View.inflate(getActivity(), R.layout.frag_sy_gg2, null));
        vf.addView(View.inflate(getActivity(), R.layout.frag_sy_gg3, null));
    }


    @Override
    public void Onsuccess(final JiuGoGeBean jiuGoGeBean) {
        jiugonggelist = jiuGoGeBean.getData();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MyListAdpter myListAdpter = new MyListAdpter(getActivity(), jiugonggelist);
                jiu.setAdapter(myListAdpter);
                jiu.setLayoutManager(new GridLayoutManager(getActivity(), 2, OrientationHelper.HORIZONTAL, false));
                //九宫格
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                //拿到传递回来的数据
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }

                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    //解析得到的结果
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }

    }
}

