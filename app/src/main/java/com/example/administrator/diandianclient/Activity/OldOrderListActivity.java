package com.example.administrator.diandianclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.administrator.diandianclient.Adapter.OldOrderListAdapter;
import com.example.administrator.diandianclient.Adapter.RecyclerAdapter;
import com.example.administrator.diandianclient.R;
import com.example.administrator.diandianclient.data.ListUtils;
import com.example.administrator.diandianclient.data.Order;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/2/12.
 */

public class OldOrderListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OldOrderListAdapter oldOrderListAdapter;
    private Context mContext;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    oldOrderListAdapter = new OldOrderListAdapter(mContext,ListUtils.orders);
                    oldOrderListAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Intent intent = new Intent();
                            intent.setClass(OldOrderListActivity.this,OldOrderActivity.class);
                            intent.putExtra("position",position);
                            startActivity(intent);
//                            Toasty.info(OldOrderListActivity.this,position+"",Toast.LENGTH_LONG).show();
                        }
                    });
                    oldOrderListAdapter.notifyDataSetChanged();
                    recyclerView.setAdapter(oldOrderListAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldorderlist);
        ListUtils.orders.clear();
        mContext = getApplicationContext();
        initView();
    }
    private void initView(){
        recyclerView = findViewById(R.id.id_oldorderlist_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        AVQuery avQuery = new AVQuery("Order");
        avQuery.selectKeys(Arrays.asList("UserID","OContent", "OTPrice","ONumber","OStatus","OSNumber"));
        avQuery.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                for (AVObject avObject : list) {
                    if(avObject.getString("UserID").equals(AVUser.getCurrentUser().getUsername())){

                        ListUtils.orders.add(new Order(avObject.getString("ONumber"),avObject.getString("OContent"),avObject.getString("OTPrice"),avObject.getString("OStatus"),avObject.getString("OSNumber")));
                    }
                }
                Message msg = handler.obtainMessage(0);
                handler.sendMessage(msg);
            }
        });

    }

}
