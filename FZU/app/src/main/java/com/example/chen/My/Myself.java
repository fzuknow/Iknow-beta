package com.example.chen.My;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.*;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chen.entity.Answer;
import com.example.chen.entity.Question;
import com.example.chen.fzu.Login;
import com.example.chen.fzu.R;

import java.util.HashMap;
import java.util.Map;
import com.example.chen.entity.Question;
import http.HttpUtil;
import http.NetUtil;

public class Myself extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myself);
        getSupportActionBar().setTitle("我的");
        initView();

    }


    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    int types;


    private void initView(){
        //找到四个按钮
        button_1 = (Button) findViewById(R.id.my_ask);
        button_2 = (Button) findViewById(R.id.my_answer);
        button_3 = (Button) findViewById(R.id.my_praise);
        button_4 = (Button) findViewById(R.id.my_look);

        //设置按钮点击监听
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
    }
    private String result;
    ProgressDialog dialog;
    Answer answer;
    Question question;
    public static int quelength;
    public static String my_answer;
    public static String my_ask[]=new String[10000];
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message message) {
            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            String finalResult="";
//            String finalResult = ResultJson.getString("result");
//            System.out.println(finalResult);

            if(types==0){
                //我的提问
                quelength=ResultJson.size();
                for (int i = 0; i < ResultJson.size(); i++) {
                    JSONObject results = ResultJson.getJSONObject(i);
                    finalResult = results.getString("result");
                    question = JSON.parseObject(finalResult, Question.class);
                    System.out.println("结果是：" + finalResult);
                    my_ask[i]= question.getContent().toString();
                }
               // question= JSON.parseObject(finalResult,Question.class);

            }else if(types==1){
                //我的回答
//                answer= JSON.parseObject(finalResult,Answer.class);
//                my_answer=answer.getContent().toString();
            }

            if ((finalResult.equals("wrong")==false)&&(finalResult.equals("unexist")==false)) {
                //数据获取成功，页面跳转
                if(types==0){
                    //我的提问
                    Intent intent = new Intent(Myself.this, My_Ask.class);
                    startActivity(intent);
                }
                if(types==1)
                {
                    //我的回答
                    Intent intent = new Intent(Myself.this, My_Answer.class);
                    startActivity(intent);
                }else if(types==2){
                    //我的赞
                    Intent intent2 = new Intent(Myself.this, My_Praise.class);
                    startActivity(intent2);
                }

            } else{

                Toast.makeText(Myself.this, "数据获取失败，请稍后重试！", Toast.LENGTH_SHORT).show();
                System.out.println("数据获取失败，请稍后重试！");

            }

        }
    };

    //点击事件处理
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.my_ask) {
            types=0;
            //获取学生ID
            String userID = Login.userId;
            System.out.println("学生编号："+userID);
            final Map<String, Object> userInfo = new HashMap<String, Object>();
            userInfo.put("userId", userID);
            final String user = "userId=" + userID;

            new Thread() {
                public void run() {
                    String response = HttpUtil.doPostRequest(NetUtil.PATH_USER_MyQuestion, user);
                    android.os.Message message = android.os.Message.obtain();
                    message.obj = response;
                    System.out.println("返回"+response);
                    mHanlder.sendMessage(message);
                }
            }.start();
        }else if(v.getId()==R.id.my_answer) {
            types=1;
           //获取学生ID
            String userID = Login.userId;
            System.out.println("学生编号："+userID);
            final Map<String, Object> userInfo = new HashMap<String, Object>();
            userInfo.put("userId", userID);
            final String user = "userId=" + userID;

            new Thread() {
                public void run() {
                    String response = HttpUtil.doPostRequest(NetUtil.PATH_USER_MyAnswer, user);
                    android.os.Message message = android.os.Message.obtain();
                    message.obj = response;
                    System.out.println("返回"+response);
                    mHanlder.sendMessage(message);
                }
            }.start();
        }else if(v.getId()==R.id.my_praise){
            types=2;
            //获取学生ID
            String userID = Login.userId;
            System.out.println("学生编号："+userID);
            final Map<String, Object> userInfo = new HashMap<String, Object>();
            userInfo.put("userId", userID);
            final String user = "userId=" + userID;

            new Thread() {
                public void run() {
                    String response = HttpUtil.doPostRequest(NetUtil.PATH_USER_MyQuestion, user);
                    android.os.Message message = android.os.Message.obtain();
                    message.obj = response;
                    System.out.println("返回"+response);
                    mHanlder.sendMessage(message);
                }
            }.start();

        }else if(v.getId()==R.id.my_look){
            types=3;
            //浏览记录
                Intent intent3 = new Intent(Myself.this, My_Look.class);
                startActivity(intent3);
        }

    }
}
