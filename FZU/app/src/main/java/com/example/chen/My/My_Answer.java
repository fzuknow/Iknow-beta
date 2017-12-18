package com.example.chen.My;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.chen.fzu.Login;
import com.example.chen.fzu.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class My_Answer extends AppCompatActivity {

    ListView list;
    private String[] mName = {Myself.my_answer,"这只是个例子2"};
    private String[] mNum = {"1", "2"};
    private ArrayList<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__answer);
        getSupportActionBar().setTitle("我的回答");
        list = (ListView)findViewById(R.id.listview);
        for (int i = 0; i < mNum.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("name", mName[i]);
            item.put("num", mNum[i]);
            mData.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, mData, android.R.layout.simple_expandable_list_item_2,
                new String[]{"name", "num"}, new int[]{android.R.id.text1, android.R.id.text2});
        list.setAdapter(adapter);



//        TextView t=(TextView)findViewById(R.id.answer);
//        t.setText(Myself.my_answer);
    }
}
