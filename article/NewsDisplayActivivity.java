package com.example.chen.fzu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Administrator on 2017/12/22/022.
 */

public class NewsDisplayActivivity extends AppCompatActivity {
    private String articleUrl;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail);
        articleUrl = getIntent().getStringExtra("article_Url");
        WebView webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(articleUrl);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }

}
