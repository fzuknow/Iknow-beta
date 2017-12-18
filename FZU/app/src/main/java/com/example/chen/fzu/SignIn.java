package com.example.chen.fzu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Message;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;
import http.HttpUtil;
import http.NetUtil;



/**
 * Created by laixl on 2017/11/2.
 */

public class SignIn extends AppCompatActivity implements View.OnClickListener{
    TextView EditTelephone,EditPass,ConfirmPass,changeText;
    Button sign;
    String result;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message message) {
            result = (String) message.obj;
            JSONObject resultJson = JSONObject.parseObject(result);
            String finalResult = resultJson.getString("result");
            System.out.println("结果是：" + finalResult);
            if (finalResult.equals("注册成功！")) {
                //登录成功，转到首页
                Intent intent = new Intent(SignIn.this, Home_Page.class);
                startActivity(intent);
            } else{
                Toast.makeText(SignIn.this, "该用户已存在", Toast.LENGTH_SHORT).show();
//                System.out.println("用户名已存在");
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        //获取控件
        EditTelephone=(TextView) findViewById(R.id.telephone);
        EditPass=(TextView)findViewById(R.id.password);
        ConfirmPass=(TextView)findViewById(R.id.Confirm);
        changeText=(TextView)findViewById(R.id.gologin);
        sign=(Button)findViewById(R.id.sign);

        //监听
        changeText.setOnClickListener(this);
        sign.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        if(v.getId()==R.id.gologin){
            //已有账号，去登录
            Intent intent=new Intent(SignIn.this,Login.class);
            startActivity(intent);
        }else if(v.getId()==R.id.sign){
            //注册
            //获取电话号码
            String userTelephone = EditTelephone.getText().toString();
            // 获取用户密码
            String userPass = EditPass.getText().toString();
            //确认密码
            String confirmPass=ConfirmPass.getText().toString();
            final Map<String, Object> userInfo = new HashMap<String, Object>();
            userInfo.put("userTelephone", userTelephone);
            userInfo.put("userPass", userPass);
            final String user = "userTelephone=" + userTelephone + "&userPass=" + userPass;
            if(userPass.length()<6){
                Toast.makeText(SignIn.this,"密码长度过短，请重新输入",Toast.LENGTH_LONG).show();
            }else if(userPass.length()>16){
                Toast.makeText(SignIn.this,"密码长度过长，请重新输入",Toast.LENGTH_LONG).show();
            }else if(confirmPass.equals(userPass)==false){
                Toast.makeText(SignIn.this,"两次密码输入不一致",Toast.LENGTH_LONG).show();
            }else if(userTelephone.length()!=11){
                Toast.makeText(SignIn.this,"不存在该手机号码！",Toast.LENGTH_LONG).show();
            }else{
                new Thread() {
                    public void run() {
                        String response =HttpUtil.doPostRequest(NetUtil.PATH_USER_REGISTER, user);
                        Message message = Message.obtain();
                        message.obj = response;
//                        System.out.println("返回"+response);
                        mHanlder.sendMessage(message);
                    }
                }.start();
            }
        }
    }
}
