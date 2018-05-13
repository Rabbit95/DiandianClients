package com.example.administrator.diandianclient.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.diandianclient.R;
import com.example.administrator.diandianclient.data.ListUtils;

/**
 * Created by Administrator on 2018/1/9.
 */

public class MyInfoActivity extends AppCompatActivity {
    private EditText name,phoneNumber;
    private ImageView back;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ListUtils.user.setUserNmae(AVUser.getCurrentUser().getString("name"));
        ListUtils.user.setPhoneNumber(AVUser.getCurrentUser().getString("phoneNumber"));
        initView();
    }
    public void initView(){
        name = findViewById(R.id.id_info_name);
        phoneNumber = findViewById(R.id.id_info_phoneNumber);
        name.setText(ListUtils.user.getUserNmae());
        phoneNumber.setText(ListUtils.user.getPhoneNumber());
        back = findViewById(R.id.id_info_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(TextUtils.isEmpty(name.getText())) && !(TextUtils.isEmpty(phoneNumber.getText()))){
                    AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            AVUser.getCurrentUser().put("name", name.getText());
                            AVUser.getCurrentUser().put("phoneNumber", phoneNumber.getText());
                            AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
                                    ListUtils.user.setUserNmae(name.getText().toString());
                                    ListUtils.user.setPhoneNumber(phoneNumber.getText().toString());
                                    Log.d("user",ListUtils.user.getUserNmae()+ListUtils.user.getPhoneNumber());
                                    if(e != null) {
                                        Log.d("infoe", e.toString());
                                    }
                                }
                            });
                            finish();
                        }
                    });
                }
            }
        });

    }

}
