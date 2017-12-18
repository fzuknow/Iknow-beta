package com.example.chen.article;

/**
 * Created by laixl on 2017/11/6.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.chen.fzu.Home_Page;
import com.example.chen.fzu.Login;
import com.example.chen.fzu.R;

import java.util.HashMap;
import java.util.Map;

import http.HttpUtil;
import http.NetUtil;

public class ArticleDetail extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail);
    }
}