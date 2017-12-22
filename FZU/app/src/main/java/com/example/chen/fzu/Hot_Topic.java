package com.example.chen.fzu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Hot_Topic extends Fragment {
    private ListView listview;
    private List<News> newsList;
    //private SearchView searchView;
    private Handler mHanlder;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意View对象的重复使用，以便节省资源
        View view = inflater.inflate(R.layout.article_listview, container, false);
        //// 显示通知的ListView
        newsList = new ArrayList<>();
        listview = (ListView) view.findViewById(R.id.news_lv);
        getNews();
        mHanlder=new Handler() {
            @Override
            public void handleMessage(Message msg) {

                if (msg.what == 1) {
                    System.out.println("handler");
                    NewsAdapter adapter = new NewsAdapter(getActivity(), newsList);
                    System.out.println("handler2");
                    listview.setAdapter(adapter);
                    System.out.println("handler3");
                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            System.out.println("handler4");
                            News news = newsList.get(position);
                            System.out.println("handler5");
                            Intent intent;
                            System.out.println("handler6");
                            intent = new Intent(getActivity(), NewsDisplayActivivity.class);
                            System.out.println("handler7");
                            intent.putExtra("article_Url", news.getArticleUrl());
                            System.out.println("handler8");
                            startActivity(intent);
                        }
                    });
                }
            }
        };
        return view;
    }
    private void getNews()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                //获取数据
                    org.jsoup.nodes.Document doc =Jsoup.connect("http://jwch.fzu.edu.cn/").get();
                   // System.out.println("111");
                    Elements titleLinks = doc.select("div#tongzwj.r").select("li");//解析标题的地址和名称
                   // System.out.println("222");
                    String title2=titleLinks.text();
                  //  System.out.println("000"+title2);
                    Elements divisionLinks=doc.select("span.fi11");//部门
                    String division2=divisionLinks.text();
                   // System.out.println("333"+division2);
                    Elements timeLinks=doc.select("span.date");//时间
                    String time2=timeLinks.text();
                   // System.out.println("444"+time2);
                    Log.e("title",Integer.toString(titleLinks.size()));
                  //  System.out.println("555");
                    for(int j=0;j<timeLinks.size();j++)
                    {  System.out.println(j+" ");
                     String title =titleLinks.get(j).select("a").text();
                    String url = titleLinks.get(j).select("a").attr("href");
                        String articletime=timeLinks.get(j).text();
                        String division=divisionLinks.get(j).text();
                        System.out.println(title+url+articletime+division);
                        News news=new News(title,url,division,articletime);
                        newsList.add(news);
                    }
                    System.out.println("777");
                    Message msg = new Message();
                    msg.what = 1;
                    mHanlder.sendMessage(msg);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

}).start();
    }


    }
