package com.example.chen.fzu;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class Setting extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ActionBar actionbar=getSupportActionBar();
        if(actionbar!=null){
            actionbar.hide();
        }
        TextView title=(TextView)findViewById(R.id.titlebar_title_tv);
        title.setText("设置");
        initView();
        //返回按钮监听,返回到主页
        ImageButton back=(ImageButton)findViewById(R.id.backImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Setting.this,Home_Page.class);
                startActivity(intent);
            }
        });
    }

    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private ImageButton imbutton1;
    private ImageButton imbutton2;
    private ImageButton imbutton3;
    private ImageButton imbutton4;

    private void initView() {
        //找到四个按钮
        button_1 = (Button) findViewById(R.id.account_information);
        button_2 = (Button) findViewById(R.id.Safe);
        button_3 = (Button) findViewById(R.id.noticeSet);
        button_4 = (Button) findViewById(R.id.Exit);
        imbutton1=(ImageButton)findViewById(R.id.inforImageButton);
        imbutton2=(ImageButton)findViewById(R.id.safeImageButton);
        imbutton3=(ImageButton)findViewById(R.id.noticeImageButton);
        imbutton4=(ImageButton) findViewById(R.id.exitImageButton);
        //设置按钮点击监听
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);
        imbutton1.setOnClickListener(this);
        imbutton2.setOnClickListener(this);
        imbutton3.setOnClickListener(this);
        imbutton4.setOnClickListener(this);

    }

    //点击事件处理
    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.inforImageButton||v.getId()==R.id.account_information) {
           Intent intent = new Intent(Setting.this, Account_Information.class);
           startActivity(intent);
       }else if(v.getId()==R.id.safeImageButton||v.getId()==R.id.Safe) {
           Intent intent1 = new Intent(Setting.this, Privacy_Security.class);
           startActivity(intent1);
       }else if(v.getId()==R.id.noticeImageButton||v.getId()==R.id.noticeSet) {
           Intent intent2 = new Intent(Setting.this, Natification_Management.class);
           startActivity(intent2);
       }else if(v.getId()==R.id.exitImageButton||v.getId()==R.id.Exit) {
           Intent intent3 = new Intent(Setting.this, Login.class);
           startActivity(intent3);
       }
    }
}
