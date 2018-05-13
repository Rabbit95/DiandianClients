package com.example.administrator.diandianclient.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.diandianclient.Adapter.OldOrderAdapter;
import com.example.administrator.diandianclient.R;
import com.example.administrator.diandianclient.data.ListUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2018/2/12.
 */

public class OldOrderActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private RecyclerView recyclerView;
    private OldOrderAdapter oldOrderAdapter;
    private int position;
    private TextView OTPrice,ONumber,name,phone,OSNumber;
    private Button cancel;
    private EditText editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldorder);
        position = getIntent().getIntExtra("position",-1);
        initView();
    }
    public void initView(){
        editText = findViewById(R.id.id_oldorder_eva);
        linearLayout = findViewById(R.id.id_evaluation_ll);
        OSNumber = findViewById(R.id.id_salt);
        cancel = findViewById(R.id.id_oldorder_cancel);
        switch (ListUtils.orders.get(position).getOStatus()){
            case "0":
                linearLayout.setVisibility(View.GONE);
                cancel.setEnabled(true);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AVQuery avQuery = new AVQuery("Order");
                        avQuery.whereContains("ONumber",ListUtils.orders.get(position).getONumber());
                        avQuery.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                for (AVObject avObject : list) {
                                    avObject.put("OStatus","2");
                                    avObject.saveInBackground();
                                    Toasty.info(OldOrderActivity.this,"订单已取消", Toast.LENGTH_LONG).show();
                                    finish();
                                }
                            }
                        });
                    }
                });

                break;
            case "1":
                cancel.setText("订单评价");
                cancel.setEnabled(false);
                AVQuery avQuery = new AVQuery("Order");
                avQuery.whereContains("ONumber",ListUtils.orders.get(position).getONumber());
                avQuery.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        for (AVObject avObject : list) {
                            if(!TextUtils.isEmpty(avObject.getString("evaluation"))){
                                editText.setText(avObject.getString("evaluation"));
                                cancel.setText("订单已评价");
                            }else{
                                editText.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                    }
                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        if(editText.getText().length() > 0){
                                            cancel.setEnabled(true);
                                            cancel.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    AVQuery avQuery = new AVQuery("Order");
                                                    avQuery.whereContains("ONumber",ListUtils.orders.get(position).getONumber());
                                                    avQuery.findInBackground(new FindCallback<AVObject>() {
                                                        @Override
                                                        public void done(List<AVObject> list, AVException e) {
                                                            for (AVObject avObject : list) {
                                                                avObject.put("evaluation",editText.getText());
                                                                avObject.saveInBackground();
                                                                Toasty.info(OldOrderActivity.this,"评价成功", Toast.LENGTH_LONG).show();
                                                                finish();
                                                            }
                                                        }
                                                    });
                                                }
                                            });
                                        }else{
                                            cancel.setEnabled(false);
                                        }
                                    }
                                    @Override
                                    public void afterTextChanged(Editable editable) {
                                    }
                                });
                            }
                        }
                    }
                });
                break;
            case "2":
                linearLayout.setVisibility(View.GONE);
                cancel.setEnabled(false);
                break;
        }
        name = findViewById(R.id.id_oldorder_name);
        phone = findViewById(R.id.id_oldorder_phone);
        name.setText(ListUtils.user.getUserNmae());
        phone.setText(ListUtils.user.getPhoneNumber());
        OSNumber.setText(ListUtils.orders.get(position).getOSNumber());
        OTPrice = findViewById(R.id.id_oldorder_Tprice);
        ONumber = findViewById(R.id.id_oldorder_ONumber);
        ONumber.setText("订单号："+ListUtils.orders.get(position).getONumber());
        OTPrice.setText(ListUtils.orders.get(position).getOTPrice()+"元");
        String NAN[] = ListUtils.orders.get(position).getOContent().split(":|,");
        recyclerView = findViewById(R.id.id_oldorder_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        oldOrderAdapter = new OldOrderAdapter(ListUtils.orders,NAN);
        recyclerView.setAdapter(oldOrderAdapter);
    }
}
