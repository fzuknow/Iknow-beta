package com.example.chen.fzu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Hot_Topic extends Fragment {
    private ListView listview;
   // private CharSequence mTitle;
    private List<Map<String, Object>> list = new ArrayList<>();
    private ProgressDialog dialog;
    //指定网页网址
    public String url = "https://xjh.haitou.cc/wh/uni-1/after/hold/page-1";
    //private SearchView searchView;
    public int clickPosition = -1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //注意View对象的重复使用，以便节省资源
        View view = inflater.inflate(R.layout.fragment_hot__topic,container, false);
        //// 显示通知的ListView
        listview = (ListView) view.findViewById(R.id.listview);
        list = new ArrayList<>();
     //   mTitle = getTitle();
       // new Thread(runnable).start();
        //初始化窗口
     //   initWindow();
        return view;
    }
    private void show() {
       // View v;
        if(list.isEmpty()) {

        } else {
            SimpleAdapter adapter = new SimpleAdapter(getActivity(),list, R.layout.fragment_hot__topic,
                    new String[]{"articletitle", "adivision","articletime"},
                    new int[]{R.id.articletitle,R.id.adivision,R.id.articletime});
            listview.setAdapter(adapter);
        }
        dialog.dismiss();  // 关闭窗口
    }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Connection conn = Jsoup.connect(url);
            // 修改http包中的header,伪装成浏览器进行抓取
            conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
            Document doc = null;
            try {
                doc = conn.get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 获取教务处首页div=tongzwj元素下的所有元素
            Elements elements = doc.select("ul.Tlist");//ul还是ul类包含的Tlist？div Id？
            for(Element element : elements) {
                String ArticleTitle = element.getElementsByTag("_blank").text();//获取链接？？
                String Adivision = element.select("span.fill").first().text();
                String ArtileTime = element.getElementsByClass("span.date").text();

                Map<String, Object> map = new HashMap<>();
                map.put("articletitle", ArticleTitle);
                map.put("adivision", Adivision);
                map.put("articletime", ArtileTime);
                list.add(map);
            }
            // 执行完毕后给handler发送一个空消息
            handler.sendEmptyMessage(0);
        }
    };


    Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            super.handleMessage(msg);
            // 收到消息后执行handler
            show();
        }
    };


}
