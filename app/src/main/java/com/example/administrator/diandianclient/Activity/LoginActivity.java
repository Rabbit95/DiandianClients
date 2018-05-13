package com.example.administrator.diandianclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.administrator.diandianclient.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/12/27.
 */

public class LoginActivity extends AppCompatActivity {
    private TextView name,pswd;
    private Button login,registered,forget;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView(){
        name = findViewById(R.id.id_login_name);
        pswd = findViewById(R.id.id_login_pswd);
        login = findViewById(R.id.id_login_login);
        registered = findViewById(R.id.id_login_registered);
        forget = findViewById(R.id.id_login_forget);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(TextUtils.isEmpty(name.getText()))&&!(TextUtils.isEmpty(pswd.getText()))) {
                    login(name.getText().toString(),pswd.getText().toString());
                }else{
                    Toasty.error(LoginActivity.this,"用户名或密码不得为空",Toast.LENGTH_LONG).show();
                }
            }
        });
        registered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisteredActivity.class);
                startActivity(intent);
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,ForgetActivity.class);
                startActivity(intent);
            }
        });
    }
    public void login(String name, String pswd){
        AVUser.logInInBackground(name, pswd, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(e == null) {
                    Toasty.info(LoginActivity.this, "登陆成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this,MyActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    switch (e.getCode()){
                        case 210:
                            Toasty.error(LoginActivity.this, "用户名密码不匹配", Toast.LENGTH_LONG).show();break;
                        case 211:
                            Toasty.error(LoginActivity.this, "用户不存在", Toast.LENGTH_LONG).show();break;
                        case 200:
                            Toasty.error(LoginActivity.this, "用户名或密码不的为空", Toast.LENGTH_LONG).show();break;
                        case 201:
                            Toasty.error(LoginActivity.this, "用户名或密码不的为空", Toast.LENGTH_LONG).show();break;
                        default:
                            Toasty.error(LoginActivity.this, "未知错误", Toast.LENGTH_LONG).show();break;
                    }
                }
            }
        });
    }
}
