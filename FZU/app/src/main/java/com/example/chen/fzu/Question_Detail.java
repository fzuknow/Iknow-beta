package com.example.chen.fzu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by laixl on 2017/11/13.
 */

public class Question_Detail extends AppCompatActivity {

    private TextView textView;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question__detail);
        textView=(TextView)findViewById(R.id.detail_textview);
        listView=(ListView)findViewById(R.id.detail_listview);
    }
}
