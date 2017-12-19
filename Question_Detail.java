package com.example.chen.fzu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Time;

/**
 * Created by laixl on 2017/11/13.
 */

public class Question_Detail extends AppCompatActivity {

    private TextView textView,NameText,TimeText;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);
        textView=(TextView)findViewById(R.id.detail_textview);
        NameText=(TextView)findViewById(R.id.name);
        TimeText=(TextView)findViewById(R.id.time);

        listView=(ListView)findViewById(R.id.detail_listview);
        textView.setText(Latest_Question.content);
        NameText.setText(Latest_Question.name);
        TimeText.setText(Latest_Question.time);
    }
}
