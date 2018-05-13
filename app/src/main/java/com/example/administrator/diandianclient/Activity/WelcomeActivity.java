package com.example.administrator.diandianclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.avos.avoscloud.AVOSCloud;
import com.example.administrator.diandianclient.R;

/**
 * Created by Administrator on 2017/12/10.
 */

public class WelcomeActivity extends AppCompatActivity {;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //选择节点为中国
        AVOSCloud.useAVCloudCN();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "TADkVyABvXnuFKUvQdKAHE06-gzGzoHsz", "Ond1vLfmL9pEUhJLFsvaIxNH");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },2000);
    }
}
