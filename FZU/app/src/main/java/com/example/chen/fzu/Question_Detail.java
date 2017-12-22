package com.example.chen.fzu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chen.entity.Question;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

import http.NetUtil;

import static com.example.chen.fzu.Latest_Question.clickPosition;

/**
 * Created by laixl on 2017/11/13.
 */

public class Question_Detail extends AppCompatActivity {
    String result;
    Question question;
    public static int quelength;
    public static String que[][]=new String[10000][6];
    private TextView textView,NameText,TimeText;
    private ListView listView;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            quelength=ResultJson.size();
            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                question = JSON.parseObject(finalResult, Question.class);
                System.out.println("结果是：" + finalResult);
                que[i][0] = question.getContent();//第几条问题的内容
                que[i][1]=  question.getReleaseDate();
                // que[i][1]= "2017-12-16";
                que[i][2]=String.valueOf(question.getStudentId());
                que[i][3]=String.valueOf(question.getCommentNum());
                que[i][4]=String.valueOf(question.getPraiseNum());
                System.out.println("内容：" + que[i][0]);
            }
            // ReleaseDate.setText("20150515");
//            for (int i = 0; i <quelength; i++) {
//                list.add(que[i][0]);
//
//                System.out.println("ooo"+que[i][0]);
//                System.out.println("]]]]]]555");
//            }
            for (int i = 0; i <quelength; i++) {
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("nicheng", que[i][2]);
                map.put("date", que[i][1]);
                map.put("content", que[i][0]);
                map.put("praiseNum",que[i][4]);
                map.put("commentNum",que[i][3]);
//                mlist.add(map);
            }
            //listView = (ListView) super.findViewById(R.id.listView);
//            Latest_Question.MyAdapter listItem=new Latest_Question.MyAdapter();
//            listview.setAdapter(listItem);

            System.out.println("gggg");
        }
    };
    public void Comment()
    {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "comment");
        userInfo.put("questionId",que[clickPosition][2]);
        final String user = "userask=comment"+"&questionId="+que[clickPosition][2];
        new Thread() {
            public void run() {
                String response = http.HttpUtil.doPostRequest(NetUtil.PATH_USER_QUESTION, user);
                System.out.println("111");
                android.os.Message message = android.os.Message.obtain();
                System.out.println("222");
                message.obj = response;
                System.out.println("评论返回" + response);
                System.out.println("ppp");
                mHanlder.sendMessage(message);
                System.out.println("lll");
            }
        }.start();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Comment();
        setContentView(R.layout.activity_question_detail);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("问题详情");

        textView=(TextView)findViewById(R.id.detail_textview);
        NameText=(TextView)findViewById(R.id.name);
        TimeText=(TextView)findViewById(R.id.time);

        listView=(ListView)findViewById(R.id.detail_listview);
        textView.setText(Latest_Question.content);
        NameText.setText(Latest_Question.name);
        TimeText.setText(Latest_Question.time);
    }
}
