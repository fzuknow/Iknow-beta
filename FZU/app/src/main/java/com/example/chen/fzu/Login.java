package com.example.chen.fzu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.example.chen.entity.User;

import java.util.HashMap;
import java.util.Map;

import http.HttpUtil;
import http.NetUtil;

/**
 * Created by laixl on 2017/11/2.
 */
public class Login extends AppCompatActivity implements View.OnClickListener {

    // 声明控件对象
    public EditText TelephoneInput, PassInput;
    public static int userAcademyId,id,xp;
    public static  String userTelephone,userPass,userName,userSex,userBirth,
            userAcademy,userMajor,userEmail,userTele,userNo,userLv,userWealth,userXp,userId;
    // 声明显示返回数据库的控件对象
    private TextView telephone_Text;
    private TextView password_Text;
    private String result;
    ProgressDialog dialog;
    User user;
    private SharedPreferences sp;
    private CheckBox rem_pw;
    private Button login = null;

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message message) {
            result = (String) message.obj;
            JSONObject resultJson = JSON.parseObject(result);

            String finalResult = resultJson.getString("result");
            System.out.println(finalResult);
            user= JSON.parseObject(finalResult,User.class);

            ///将学生的基本信息取出，方便显示在屏幕上
            userName=user.getStudentName().toString();
            userTele=user.getTelephone().toString();
            userNo=user.getStudentNo().toString();
            userSex=user.getSex().toString();
            userAcademy=user.getAcademyId().toString();
            userMajor=user.getMajor().toString();
            userEmail=user.getEmail().toString();
            userBirth=user.getBirthday().toString();
            int lv=user.getLv();
            userLv=String.valueOf(lv);
            int wealth=user.getWealth();
            userWealth=String.valueOf(wealth);
            xp=user.getXp();
            userXp=String.valueOf(xp);
            id=user.getStudentId();
            userId=String.valueOf(id);
            if ((finalResult.equals("wrong")==false)&&(finalResult.equals("unexist")==false)) {
                //登录成功和记住密码框为选中状态才保存用户信息
                if(rem_pw.isChecked())
                {
                    //记住用户名、密码、
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("USER_NAME", userTelephone);
                    editor.putString("PASSWORD",userPass);
                    editor.commit();
                }
                //登录成功，转到首页
                dialog.dismiss();
                Intent intent = new Intent(Login.this, Home_Page.class);
                startActivity(intent);
            } else{
                dialog.dismiss();
                Toast.makeText(Login.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                System.out.println("用户名或密码错误");

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置显示的视图
        setContentView(R.layout.activity_login);
        // sendText=(TextView)findViewById(R.id.send_request);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        final Button loginButton = (Button) findViewById(R.id.login_button);
        TextView goto_signin = (TextView) findViewById(R.id.gosignin);
        goto_signin.setOnClickListener(this);
        loginButton.setOnClickListener(this);

        //获得实例对象
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        rem_pw = (CheckBox) findViewById(R.id.auto);
        init();
    }

    public void init(){
        // 通过 findViewById(id)方法获取用户名的控件对象
        TelephoneInput = (EditText) findViewById(R.id.telephone);
        // 通过 findViewById(id)方法获取用户密码的控件对象
        PassInput = (EditText) findViewById(R.id.password);
        rem_pw = (CheckBox) findViewById(R.id.auto);
        login = (Button) findViewById(R.id.login_button);
        if (sp.getBoolean("checkboxBoolean", false))
        {
            TelephoneInput.setText(sp.getString("TelephoneInput", null));
            PassInput.setText(sp.getString("PassInput", null));
            rem_pw.setChecked(true);

        }
        PassInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login_button) {

            //   获取电话号码
            userTelephone = TelephoneInput.getText().toString();
            // 获取用户密码
            userPass = PassInput.getText().toString();
            System.out.println("电话号码："+userTelephone);
            final Map<String, Object> userInfo = new HashMap<String, Object>();
            userInfo.put("userTelephone", userTelephone);
            userInfo.put("userPass", userPass);

            final String user = "userTelephone=" + userTelephone + "&userPass=" + userPass;


            if (TextUtils.isEmpty(userTelephone)) {
                Toast.makeText(Login.this, "用户名不能为空", Toast.LENGTH_LONG).show();
            } else {
                if (TextUtils.isEmpty(userPass)) {
                    Toast.makeText(Login.this, "密码不能为空", Toast.LENGTH_LONG).show();
                } else {
                    //隐藏密码
                    rem_pw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    //按钮被选中，下次进入时会显示账号和密码

                    boolean CheckBoxLogin = rem_pw.isChecked();
                    if (CheckBoxLogin) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("TelephoneInput", userTelephone);
                        editor.putString("PassInput", userPass);
                        editor.putBoolean("checkboxBoolean", true);
                        editor.commit();
                    }
                    ////按钮被选中，清空账号和密码，下次进入时会显示账号和密码
                    else {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("uname", null);
                        editor.putString("upswd", null);
                        editor.putBoolean("checkboxBoolean", false);
                        editor.commit();
                    }

                    dialog = new ProgressDialog(this);
                    dialog.setTitle("登录中");
                    dialog.setMessage("请耐心等待....");
                    dialog.show();
                    new Thread() {
                        public void run() {
                            String response = HttpUtil.doPostRequest(NetUtil.PATH_USER_LOGIN, user);
                            Message message = Message.obtain();
                            message.obj = response;
                            System.out.println("返回" + response);
                            mHanlder.sendMessage(message);
//                      Intent intent = new Intent(Login.this, Home_Page.class);
//                      startActivity(intent);
                        }
                    }.start();
                }
            }
        }
        if (v.getId() == R.id.gosignin) {
            Intent intent = new Intent(Login.this, SignIn.class);
            startActivity(intent);

        }

    }

}