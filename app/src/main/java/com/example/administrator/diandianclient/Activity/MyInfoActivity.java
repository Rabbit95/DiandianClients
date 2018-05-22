package com.example.administrator.diandianclient.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private Button button;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        initView();
        if(AVUser.getCurrentUser().getString("name")!=null){
            name.setText(AVUser.getCurrentUser().getString("name"));
            phoneNumber.setText(AVUser.getCurrentUser().getString("phoneNumber"));
        }else {
            name.setText(ListUtils.name);
            phoneNumber.setText(ListUtils.phone);
        }
    }
    public void initView(){
        button = findViewById(R.id.id_info_determine);
        name = findViewById(R.id.id_info_name);
        phoneNumber = findViewById(R.id.id_info_phoneNumber);

        name.setText(ListUtils.name);
        phoneNumber.setText(ListUtils.phone);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!(TextUtils.isEmpty(name.getText())) && !(TextUtils.isEmpty(phoneNumber.getText()))){
                    AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            AVUser.getCurrentUser().put("name", name.getText());
                            AVUser.getCurrentUser().put("phoneNumber", phoneNumber.getText());
                            ListUtils.name=name.getText().toString();
                            Log.d("user",ListUtils.name+"");
                            ListUtils.phone=phoneNumber.getText().toString();
                            AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
                                @Override
                                public void done(AVException e) {
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
        back = findViewById(R.id.id_info_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });

    }

}
