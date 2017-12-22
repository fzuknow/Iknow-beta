package com.example.chen.fzu;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;

import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.chen.entity.Question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import http.NetUtil;

import static com.example.chen.fzu.Login.userId;
import static com.example.chen.fzu.Question_Answer.list;

//import static com.example.chen.fzu.Question_Answer.que;


public class WealthValue_Question extends Fragment {
    String result;
    Question question;
    public static String content,time,name;
    public static int quelength,n;
    public static String que[][]=new String[10000][7];
    boolean flag[]=new boolean[100];
    // Fragment管理对象
    private FragmentManager manager;
    private FragmentTransaction ft;

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(android.os.Message message) {

            result = (String) message.obj;
            JSONArray ResultJson = JSONObject.parseArray(result);
            quelength=ResultJson.size();
            for (int i = 0; i < ResultJson.size(); i++) {

                JSONObject results = ResultJson.getJSONObject(i);
                String finalResult = results.getString("result");
                question = JSON.parseObject(finalResult, Question.class);
                System.out.println("结果是：" + finalResult);
                que[i][0] = question.getContent();//第几条问题的内容
                que[i][1]=  question.getReleaseDate();
                // que[i][1]= "2017-12-16";
                que[i][2]=question.getStudent().getStudentName().toString();

                que[i][3]=String.valueOf(question.getCommentNum());
                que[i][4]=String.valueOf(question.getPraiseNum());
                que[i][5]=String.valueOf(question.getId());
//                System.out.println(question.getIsReward());
//                if(question.getIsReward()==true) {
//                    System.out.println(question.getIsReward());
//                    que[i][6]="true";
//                    System.out.println(que[i][6]);
//                }else{
//                    System.out.println(question.getIsReward());
//                    que[i][6]="false";
//                    System.out.println(que[i][6]);
//                }


                System.out.println("内容：" + que[i][2]);
            }
            for (int i = 0; i <quelength; i++) {
                Map<String,Object> map = new HashMap<String,Object>();
//                if(que[i][6]=="true") {
                    map.put("nicheng", que[i][2]);
                    map.put("date", que[i][1]);
                    map.put("content", que[i][0]);
                    map.put("praiseNum", que[i][4]);
                    map.put("commentNum", que[i][3]);
                    mlist.add(map);
//                }
            }
            //listView = (ListView) super.findViewById(R.id.listView);
            MyAdapter listItem=new MyAdapter();
            listview.setAdapter(listItem);

            System.out.println("gggg");
        }
    };
    public void QuestionRequest()
    {
        final Map<String, Object> userInfo = new HashMap<String, Object>();
        userInfo.put("userask", "askquestion");
        userInfo.put("questionId","");
        userInfo.put("praiseNum","");
        final String user = "userask=askquestion";
        new Thread() {
            public void run() {
                String response = http.HttpUtil.doPostRequest(NetUtil.PATH_USER_QUESTION, user);
                System.out.println("111");
                android.os.Message message = android.os.Message.obtain();
                System.out.println("222");
                message.obj = response;
                System.out.println("问题返回" + response);
                System.out.println("ppp");
                mHanlder.sendMessage(message);
                System.out.println("lll");
            }
        }.start();

    }

    private ListView listview;
    private ArrayList<String> list;
    private ArrayList<Map<String,Object>> mlist;
    private SearchView searchView;
    public static int clickPosition = -1;
    private TextView ReleaseDate;
    int t=1;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        QuestionRequest();
        View view = inflater.inflate(R.layout.fragment_wealth_value__question, null);

        searchView = (SearchView) view.findViewById(R.id.searchView);

        listview = (ListView) view.findViewById(R.id.listview);

        mlist = new ArrayList<Map<String,Object>>();
        ReleaseDate=(TextView)view.findViewById(R.id.date);
        list = new ArrayList<>();
        adapter = new MyAdapter();
        listview.setAdapter(adapter);

        List<Question> data=new ArrayList<Question>();
        for (int i = 0; i < quelength; i++) {
            flag[i]=false;
            data.add(question);
        }
        listview.setTextFilterEnabled(true);
        //为该SearchView组件设置事件监听器
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    //      listview.setFilterText(newText);
                } else {
                    listview.clearTextFilter();
                }
                return false;
            }
        });

        return view;
    }

    public static  MyAdapter adapter;

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {

            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final MyViewHolder vh;

            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item, null);
                vh = new MyViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (MyViewHolder) convertView.getTag();
            }

            vh.title.setText((String)mlist.get(position).get("nicheng"));
            vh.data.setText((String)mlist.get(position).get("date"));
            vh.tv_test.setText((String)mlist.get(position).get("content"));
            vh.CommentNum.setText((String)mlist.get(position).get("commentNum"));
            vh.PraiseNum.setText((String)mlist.get(position).get("praiseNum"));
            vh.Comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),Comment.class);
                    startActivity(intent);
                }
            });

            //刷新adapter的时候，getview重新执行，此时对在点击中标记的position做处理
            if (clickPosition == position) {//当条目为刚才点击的条目时
                if (vh.praise.isSelected()) {//点赞的图标被点击的时候
                    clickPosition = -1;
                    vh.praise.setSelected(false);
                }else
                {
                    vh.praise.setSelected(true);
                }

            } else {
                vh.praise.setSelected(false);
            }

            //点击item进入问题详情界面
            vh.tv_test.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickPosition = position;
                    Map<String,Object> k=mlist.get(position);
                    content=k.get("content").toString();
                    System.out.println(k);
                    time=k.get("date").toString();
                    name=k.get("nicheng").toString();
                    System.out.println("问题编号"+que[position][5]);
                    // new Question_Detail();

                    Intent intent =new Intent(getActivity(),Question_Detail.class);
                    startActivity(intent);

                }
            });
            vh.praise.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // int po=(Integer) v.getTag();

                    clickPosition = position;//记录点击的position
                    System.out.println("位置"+clickPosition);
                    notifyDataSetChanged();
                    Map<String,Object> k=mlist.get(position);

                    if (flag[position] == true) {
                        System.out.println("点赞不变红");
                        vh.praise.setImageResource(R.drawable.praisew);
                        flag[position]=false;

                        System.out.println("false  false");
                        System.out.println("该位置点赞数"+mlist.get(position).get("praiseNum"));

                        n=Integer.parseInt(k.get("praiseNum").toString());
                        n+=1;
                        que[position][4]=String.valueOf(n);
                        System.out.println("que[position][4]"+que[position][4]);
                        System.out.println("取消点赞"+(n));
                    } else {
                        //显示的是方块，为什么？？？？
                        vh.praise.setImageResource(R.drawable.praiser);
                        System.out.println("点赞变红");
                        flag[position]=true;
                        System.out.println("该位置点赞数"+mlist.get(position));

                        n=Integer.parseInt(k.get("praiseNum").toString());

                        n--;
                        que[position][4]=String.valueOf(n);
                        System.out.println("que[position][4]"+que[position][4]);
                        System.out.println("点赞"+(n));
                    }
                    AnimationTools.scale(vh.praise);
                    new PraiseChange();
//                    MyAdapter listItem=new MyAdapter();
//                    listview.setAdapter(listItem);
                    final Map<String, Object> userInfo = new HashMap<String, Object>();
                    userInfo.put("studentId",userId);
                    userInfo.put("questionId",que[clickPosition][5]);

                    userInfo.put("IsPraise",flag[position]);

                    final String user = "studentId="+userId+"&questionId="+que[clickPosition][5]+"&IsPraise="+flag[position];

                    new Thread() {
                        public void run() {
                            String response = http.HttpUtil.doPostRequest(NetUtil.PATH_USER_UpdatePra, user);
                            System.out.println("111");
                            android.os.Message message = android.os.Message.obtain();
                            System.out.println("222");
                            message.obj = response;
                            System.out.println("问题返回" + response);
                            System.out.println("lll"+user.toString());
                        }
                    }.start();
                }
//                }{
//                    public boolean onTouch(View v, MotionEvent event) {
//
//                        if(event.getAction() == MotionEvent.ACTION_DOWN||event.getAction() == MotionEvent.ACTION_UP){
//                            if(t%2!=0) {
//                                //重新设置按下时的背景图片
//                                ((ImageButton) v).setImageDrawable(getResources().getDrawable(R.drawable.praiser));
//
//                            }
////                            if(t%2==0){
////                                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.praise));
////
////                            }
//                        }
//                        t++;
//                        return false;
//                    }
            });
//            vh.hide_1.setOnClickListener(this);
//            vh.hide_2.setOnClickListener(this);
//            vh.hide_3.setOnClickListener(this);
//            vh.hide_4.setOnClickListener(this);
//            vh.hide_5.setOnClickListener(this);
//            vh.selectorImg.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //Toast.makeText(getActivity(), "被点了", Toast.LENGTH_SHORT).show();
//                    clickPosition = position;//记录点击的position
//                    notifyDataSetChanged();//刷新adapter重新填充条目。在重新填充的过程中，被记录的position会做展开或隐藏的动作，具体的判断看上面代码
//                    //在此处需要明确的一点是，当adapter执行刷新操作时，整个getview方法会重新执行，也就是条目重新做一次初始化被填充数据。
//                    //所以标记position，不会对条目产生影响，执行刷新后 ，条目重新填充当，填充至所标记的position时，我们对他处理，达到展开和隐藏的目的。
//                    //明确这一点后，每次点击代码执行逻辑就是 onclick（）---》getview（）
//                }
//            });


            return convertView;
        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case R.id.hide_1:
//                    // Toast.makeText(getActivity(), "点赞", Toast.LENGTH_SHORT).show();
//                    break;
//                case R.id.hide_2:
//                    // Toast.makeText(getActivity(), "评论", Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//                    // builder.setIcon(R.drawable.ic_launcher);
//                    // builder.setTitle("回答问题");
//                    //    通过LayoutInflater来加载一个xml的布局文件作为一个View对象
//                    View view = LayoutInflater.from(getActivity()).inflate(R.layout.answer_dialog, null);
//                    //    设置我们自己定义的布局文件作为弹出框的Content
//                    builder.setView(view);
//
//                    final EditText username = (EditText) view.findViewById(R.id.username);
//
//                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            String a = username.getText().toString().trim();
//
//                            //    将输入的用户名和密码打印出来
//                            //Toast.makeText(getActivity(), "输入了: " + a, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//                    builder.show();
//
//                    break;
//                case R.id.hide_3:
//                    Toast.makeText(getActivity(), "查看详情", Toast.LENGTH_SHORT).show();
////                    Intent intent = new Intent(getActivity(), Question_Detail.class);
////                    startActivity(intent);
//                    break;
////                case R.id.hide_4:
////                    Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
////                    break;
////                case R.id.hide_5:
////                    Toast.makeText(getActivity(), "属性", Toast.LENGTH_SHORT).show();
////                    break;
//            }


//        }


        class MyViewHolder {
            View itemView;
            TextView data;
            TextView title;
            TextView tv_test;
            TextView PraiseNum,CommentNum;
//            TextView hide_3, hide_4, hide_5;
            ImageView selectorImg;
            LinearLayout ll_hide;
            ImageButton praise,Comment;

            public MyViewHolder(View itemView) {
                this.itemView = itemView;
                tv_test = (TextView) itemView.findViewById(R.id.tv_test);
                praise=(ImageButton)itemView.findViewById(R.id.praiseImage);
                selectorImg = (ImageView) itemView.findViewById(R.id.checkbox);
                Comment=(ImageButton) itemView.findViewById(R.id.CommentImage);
                title=(TextView)itemView.findViewById(R.id.title);
                data=(TextView)itemView.findViewById(R.id.date);
                CommentNum=(TextView)itemView.findViewById(R.id.Comment);
                PraiseNum=(TextView)itemView.findViewById(R.id.Praise);
////                hide_1 = (TextView) itemView.findViewById(R.id.hide_1);
////                hide_2 = (TextView) itemView.findViewById(R.id.hide_2);
//                hide_3 = (TextView) itemView.findViewById(R.id.hide_3);
//                hide_4 = (TextView) itemView.findViewById(R.id.hide_3);
//                hide_5 = (TextView) itemView.findViewById(R.id.hide_3);
//                ll_hide = (LinearLayout) itemView.findViewById(R.id.ll_hide);
            }
        }
    }

}