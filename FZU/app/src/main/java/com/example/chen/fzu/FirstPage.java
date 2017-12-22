package com.example.chen.fzu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.astuetz.PagerSlidingTabStrip;
import com.example.chen.entity.Article;
import com.example.chen.entity.Question;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import http.NetUtil;

public class FirstPage extends Fragment {
    /**
     * PagerSlidingTabStrip的实例
     */
    private PagerSlidingTabStrip tabs;

    /**
     * 获取当前屏幕的密度
     */
    private DisplayMetrics dm;

    private RealTime oneFragment;
    private Hot_Topic twoFragment;
    private MyAcademy threeFragment;

    /*
     *
     */


    String result;
    Article article;
    public static int ArticleLength;
    public static String Article[]=new String[10000];
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {
            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            ArticleLength=ResultJson.size();
            for (int i = 0; i < ResultJson.size(); i++) {
                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                article = JSON.parseObject(finalResult, Article.class);
                System.out.println("结果是：" + finalResult);
                Article[i] = article.getContent();

                System.out.println("内容：" + Article[i]);
            }
            System.out.println("gggg");
        }
    };
    public void ArticleRequest()
    {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "");
        // userInfo.put("userPass", userPass);


        final String user = "userask=";
        new Thread() {
            public void run() {
                String response = http.HttpUtil.doPostRequest(NetUtil.PATH_USER_Article, user);
                System.out.println("111");
                android.os.Message message = android.os.Message.obtain();
                System.out.println("222");
                message.obj = response;
//
                System.out.println("文章返回" + response);
                System.out.println("ppp");
                mHanlder.sendMessage(message);
                System.out.println("lll");
            }
        }.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment_first_page, null);
        setOverflowShowingAlways();
        dm = getResources().getDisplayMetrics();
        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(0);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
        tabs.setViewPager(pager);
        setTabsValue();
        return view;
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, dm));
        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(Color.parseColor("#d83737"));//#d83737   #d83737(绿)

        tabs.setTabBackground(0);

    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private final String[] titles = {"热门", "实时","我的学院"};

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (oneFragment == null) {
                        oneFragment = new RealTime();
                    }
                    return oneFragment;
                case 1:
                    if (twoFragment == null) {
                        ArticleRequest();
                        twoFragment = new Hot_Topic();
                    }
                    return twoFragment;
                case 2:
                    if (threeFragment == null) {
                        threeFragment = new MyAcademy();
                    }
                    return threeFragment;
                default:
                    return null;
            }
        }

    }

    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(getParentFragment().getActivity());
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
