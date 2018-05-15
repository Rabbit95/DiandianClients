package com.example.administrator.diandianclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.diandianclient.Adapter.CarAdapter;
import com.example.administrator.diandianclient.R;
import com.example.administrator.diandianclient.data.ListUtils;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2017/12/27.
 */

public class CartActivity extends AppCompatActivity {
    private ImageView back;
    private RecyclerView recyclerView;
    private CarAdapter crAdapter;
    private Context mContext;
    private Button settlement;
    private TextView total_tv;
    private double total = 0;
//    private List<Cart> carts = MainActivity.carts;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        mContext = getApplicationContext();
        initView();
    }
    public void initView(){
        total_tv = findViewById(R.id.id_cart_total);
        settlement = findViewById(R.id.id_cart_settlement);
        if(ListUtils.carts.size()!=0) {
            settlement.setEnabled(true);
        }
        settlement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ListUtils.name == null || ListUtils.phone == null){
                    Toasty.error(CartActivity.this,"信息不完善，请在个人中心完善信息", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("total",total);
                    intent.setClass(CartActivity.this,OrderActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        setTotal();
        back = findViewById(R.id.id_cart_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.id_cart_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        crAdapter = new CarAdapter(mContext, ListUtils.carts);
        crAdapter.setOnItemClickListener(new CarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(CartActivity.this,"click item",Toast.LENGTH_LONG).show();
            }
        });
        crAdapter.setOnItemChildAddClickListener(new CarAdapter.OnItemChildAddClickListener() {
            @Override
            public void onItemChildAddClick(View view, int position) {
//                Toast.makeText(CartActivity.this,"click add"+position,Toast.LENGTH_LONG).show();
                ListUtils.carts.get(position).setNumber(ListUtils.carts.get(position).getNumber()+1);
                setTotal();
            }
        });
        crAdapter.setOnItemChildLessClickListener(new CarAdapter.OnItemChildLessClickListener() {
            @Override
            public void onItemChildLessClick(View view, int position) {
//                Toast.makeText(CartActivity.this,"click less"+position,Toast.LENGTH_LONG).show();
                ListUtils.carts.get(position).setNumber(ListUtils.carts.get(position).getNumber()-1);
                if (ListUtils.carts.get(position).getNumber() <= 0){
                    ListUtils.carts.remove(position);
                    crAdapter.notifyDataSetChanged();
                }
                setTotal();
            }
        });
        recyclerView.setAdapter(crAdapter);
    }
    public void setTotal(){
        total=0;
        for (int i = 0; i < ListUtils.carts.size(); i++) {
            total+=(ListUtils.carts.get(i).getPrice()*ListUtils.carts.get(i).getNumber());
        }
        total_tv.setText(String.valueOf(total)+"元");
    }

}
