package com.example.administrator.diandianclient.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.WebView;

import com.example.administrator.diandianclient.R;

/**
 * Created by Administrator on 2018/4/29.
 */

public class WEBViewActivity extends Activity {
    private WebView webView;
    private String [] WebUrl = {"https://m.4008823823.com.cn/kfcmwos/index.htm"
            ,"http://m.kfc.com.cn/superapp/index.html","https://order.kfc.com.cn/mwos/?portalSource=BrandWap"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        int position = getIntent().getIntExtra("position",0);
        Log.d("ps",position+"");
        String urls = WebUrl[position];
        webView = findViewById(R.id.wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(urls);
    }
}
