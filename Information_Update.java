package com.example.chen.fzu;

import android.content.Intent;
import android.os.*;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

import http.HttpUtil;
import http.NetUtil;

/**
 * Created by laixl on 2017/11/25.
 */

public class Information_Update  extends AppCompatActivity {

    String result;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {
            result = (String) message.obj;
            JSONObject resultJson = JSONObject.parseObject(result);
            String finalResult = resultJson.getString("result");
            System.out.println("结果是：" + finalResult);
            if (finalResult.equals("个人信息修改成功")) {
                //修改成功
                Intent intent = new Intent(Information_Update.this, Account_Information.class);
                startActivity(intent);
            } else{
                Toast.makeText(Information_Update.this, "信息修改失败！！", Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_informtion_update);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }

        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("信息修改");
        //获取控件
        final EditText InputName = (EditText) findViewById(R.id.InputName);
        final EditText InputSex = (EditText) findViewById(R.id.InputSex);
        final EditText InputBirth = (EditText) findViewById(R.id.InputBirth);
        EditText InputNo = (EditText) findViewById(R.id.InputNo);
        EditText InputAcademy = (EditText) findViewById(R.id.InputAcademy);
        EditText InputMajor = (EditText) findViewById(R.id.InputMajor);
        EditText InputEmail = (EditText) findViewById(R.id.InputEmail);
        TextView InputTele = (TextView) findViewById(R.id.InputTelephone);

        //向控件写入数据，显示在屏幕上
        InputName.setText(Login.userName);
        InputSex.setText(Login.userSex);
        InputBirth.setText(Login.userBirth);
        InputNo.setText(Login.userNo);
        InputAcademy.setText(Login.userAcademy);
        InputMajor.setText(Login.userMajor);
        InputEmail.setText(Login.userEmail);
        InputTele.setText(Login.userTele);

        //返回按钮监听,返回到账号信息页面
        ImageButton back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Information_Update.this,Account_Information.class);
                startActivity(intent);
            }
        });
        //修改按钮控件监听
        Button sure=(Button)findViewById(R.id.modifySure);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Map<String, Object> userInfo = new HashMap<String, Object>();
                String name=InputName.getText().toString();
                String sex=InputSex.getText().toString();
                String birth=InputBirth.getText().toString();

                userInfo.put("userId", Login.userId);
                userInfo.put("name", name);
                final String user = "userId="+Login.userId+"&name="+name;
                System.out.println(user);
                    new Thread() {
                        public void run() {
                            String response = HttpUtil.doPostRequest(NetUtil.PATH_USER_Updateinfor, user);
                            android.os.Message message = android.os.Message.obtain();
                            message.obj = response;
                            System.out.println("返回"+response);
                            mHanlder.sendMessage(message);
                        }
                    }.start();
                }

        });
    }


}
