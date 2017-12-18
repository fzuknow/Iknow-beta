package com.example.chen.fzu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by laixl on 2017/11/9.
 */

public class Wealth extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wealth);
        TextView InputWealth=(TextView)findViewById(R.id.wealth);
        TextView InputXp=(TextView)findViewById(R.id.xp);
        TextView Inputlv=(TextView)findViewById(R.id.lv);
        TextView InputWealthContent=(TextView)findViewById(R.id.WealthContent);
        TextView InputLvContent=(TextView)findViewById(R.id.LvContent);
        ProgressBar mprogress=(ProgressBar)findViewById(R.id.progress_horizontal);
        InputWealth.setText(Login.userWealth);
        InputXp.setText(Login.userXp);
        Inputlv.setText("Lv."+Login.userLv);
        mprogress.setProgress(Login.xp);
        InputWealthContent.setText("财富值说明："+"\n"+"财富值可由签到以及回答悬赏问题获得，可用于悬赏问题的提问，让你的问题更快被解答。");
        InputLvContent.setText("等级说明："+"\n"+"经验值满100后等级+1"+"\n"+"lv0：0-100"+"\n"+"lv1:101-200"+"\n"+"lv2:201-300"+"\n"+"lv3:301-400"+"\n"+"...");
    }
}
