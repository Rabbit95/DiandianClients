package com.example.administrator.diandianclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.administrator.diandianclient.R;

/**
 * Created by Administrator on 2017/12/10.
 */

public class PaySuccessActivity extends AppCompatActivity {
    private TextView payNb;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playsuccess);
        initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(PaySuccessActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        },10000);
    }
    void initView(){
        payNb = findViewById(R.id.id_pays_nb);
        payNb.setText("¥ "+String.valueOf(getIntent().getDoubleExtra("total",0.00)));
    }
}
