package com.example.chen.fzu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by laixl on 2017/11/25.
 */

public class Article_Detail extends AppCompatActivity {

    private TextView textView;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail);
        textView=(TextView)findViewById(R.id.Article_detail);
        textView.setText("jjjj");
//        listView=(ListView)findViewById(R.id.detail_listview);
    }
}
