package com.example.chen.fzu;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.*;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import http.HttpUtil;
import http.NetUtil;


public class Ask_Question extends Fragment implements View.OnClickListener {


    private Spinner spinner, spinner1;
    private List<String> data_list, data_list1;
    private ArrayAdapter<String> arr_adapter, arr_adapter1;
    private Button button;
    TextView queContent;
    Latest_Question fragment;
    String result;
    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message message) {
            result = (String) message.obj;
            JSONObject resultJson = JSONObject.parseObject(result);
            String finalResult = resultJson.getString("result");
            System.out.println("结果是：" + finalResult);



            if (finalResult.equals("成功插入问题！")) {
                //插入成功

                Toast.makeText(getActivity(), "问题插入成功", Toast.LENGTH_SHORT).show();
            } else{

                Toast.makeText(getActivity(), "问题插入失败", Toast.LENGTH_SHORT).show();
//                System.out.println("用户名已存在");
            }
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ask__question, null);

        spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner1 = (Spinner) view.findViewById(R.id.spinner1);
        button = (Button) view.findViewById(R.id.button);
        queContent=(TextView)view.findViewById(R.id.edittext);
        button.setOnClickListener(this);
        //数据
        data_list = new ArrayList<String>();
        data_list.add("生活");
        data_list.add("健身");
        data_list.add("美食");
        data_list.add("学习");
        data_list.add("旅行");
        data_list.add("其他");

        //适配器
        arr_adapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);

        //数据
        data_list1 = new ArrayList<String>();
        data_list1.add("￥0");
        data_list1.add("￥5");
        data_list1.add("￥10");
        data_list1.add("￥15");
        data_list1.add("￥20");


        //适配器
        arr_adapter1 = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, data_list1);
        //设置样式
        arr_adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner1.setAdapter(arr_adapter1);

        return view;
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button) {

            String content=queContent.getText().toString();
            final Map<String, Object> userInfo = new HashMap<String, Object>();
            userInfo.put("userId", Login.userId);
            final String user = "userId=" + Login.userId+"&content="+content;
            System.out.println("userId"+Login.userId);
                new Thread() {
                    public void run() {
                        String response = HttpUtil.doPostRequest(NetUtil.PATH_USER_INSERTQUE, user);
                        android.os.Message message = android.os.Message.obtain();
                        message.obj = response;
                       System.out.println("返回"+response);
                        mHanlder.sendMessage(message);
                    }
                }.start();
            if(fragment==null)
            {
                fragment=new Latest_Question();
            }
//            Intent intent=new Intent(Ask_Question.this,Latest_Question.class);
//            startActivity(intent);

            }
        }
}



