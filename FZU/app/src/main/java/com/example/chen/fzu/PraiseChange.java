package com.example.chen.fzu;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chen.entity.Question;

import java.util.HashMap;
import java.util.Map;

import http.NetUtil;

import static com.example.chen.fzu.Latest_Question.*;
import static com.example.chen.fzu.Login.userId;

/**
 * Created by laixl on 2017/12/20.
 */

public class PraiseChange extends AppCompatActivity {



    public void Change()
    {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("questionId",que[clickPosition][2]);
        userInfo.put("praiseNum",n);
        userInfo.put("studentId",userId);
        final String user = "questionId="+que[clickPosition][2]+"&studentId="+userId+"&praiseNum="+n;
        System.out.println(user.toString());
        new Thread() {
            public void run() {
                String response = http.HttpUtil.doPostRequest(NetUtil.PATH_USER_QUESTION, user);
                System.out.println("111");
                android.os.Message message = android.os.Message.obtain();
                System.out.println("222");
                message.obj = response;
                System.out.println("问题返回" + response);
            }
        }.start();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Change();
    }
}
