package com.example.administrator.diandianclient.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVACL;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVRole;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.example.administrator.diandianclient.Adapter.OrderAdapter;
import com.example.administrator.diandianclient.R;
import com.example.administrator.diandianclient.data.ListUtils;

import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

/**
 * Created by Administrator on 2018/1/7.
 */

public class OrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView totalView,nameView,phoneView,saltView;
    private RadioGroup radioGroup;
    private Button payButton;
    private ImageView back;
    private OrderAdapter orderAdapter;
    private Context mContext;
    private double total = 0;
    private String format = null;
    private String time = null;
    private int OSNumber;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        mContext = getApplicationContext();
        total= getIntent().getDoubleExtra("total",0.00);
        new Thread(new Runnable() {
            @Override
            public void run() {
                getNetTime();
            }
        }).start();
        OSNumber = (int)(Math.random()*50+1);
        initView();
    }
    private void initView(){
        saltView = findViewById(R.id.id_order_salt);
        saltView.setText("V"+OSNumber);
        back = findViewById(R.id.id_order_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        radioGroup = findViewById(R.id.id_order_rg);
        payButton = findViewById(R.id.id_order_pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.id_order_zbox:
//                        Toasty.info(OrderActivity.this,AVUser.getCurrentUser().getString("name")+AVUser.getCurrentUser().getString("phoneNumber"), Toast.LENGTH_LONG).show();
                        final AVObject object = new AVObject("Order");
                        object.put("ONumber", format+(int)(Math.random()*1000+1));
                        object.put("UserID", AVUser.getCurrentUser().getUsername());
                        object.put("UserName",ListUtils.user.getUserNmae());
                        object.put("phoneNumber",ListUtils.user.getPhoneNumber());
                        String OContent = new String();
                        for (int i = 0; i < ListUtils.carts.size(); i++) {
                            OContent+=ListUtils.carts.get(i).getName()+":"+ListUtils.carts.get(i).getNumber()+",";
                        }
                        object.put("OContent",OContent);
                        object.put("OTPrice", String.valueOf(total));
                        object.put("OStatus", "0");
                        object.put("OSNumber", "V"+OSNumber);
                        object.put("OTime",time);

                        AVQuery<AVRole> roleQuery=new AVQuery<AVRole>("_Role");
                        // 假设上一步创建的 Administrator 角色的 objectId 为 55fc0eb700b039e44440016c
                        roleQuery.getInBackground("5a92993bac502e0032cc74ce", new GetCallback<AVRole>() {

                            @Override
                            public void done(AVRole avRole, AVException e) {
                                if(e != null){
                                    Log.d("e",e.toString());
                                }
                                //新建一个 ACL 实例
                                AVACL acl = new AVACL();
                                acl.setPublicReadAccess(true);// 设置公开的「读」权限，任何人都可阅读
                                acl.setRoleWriteAccess(avRole, true);// 为 Administrator 「写」权限
                                acl.setWriteAccess(AVUser.getCurrentUser(), true);// 为当前用户赋予「写」权限

                                // 以上代码的效果就是：只有 Post 作者（当前用户）和拥有 Administrator 角色的用户可以修改这条 Post，而所有人都可以读取这条 Post
                                object.setACL(acl);
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        Toasty.info(OrderActivity.this,"支付成功，等待送达",Toast.LENGTH_LONG).show();
                                        ListUtils.carts.clear();
                                        Intent intent = new Intent();
                                        intent.setClass(OrderActivity.this,PaySuccessActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                            }
                        });
                        break;
                    case R.id.id_order_wbox:
                        Toasty.error(OrderActivity.this,"支付失败，重新支付", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
        nameView = findViewById(R.id.id_order_name);
        nameView.setText(ListUtils.user.getUserNmae());
        phoneView = findViewById(R.id.id_order_phone);
        phoneView.setText(ListUtils.user.getPhoneNumber());
        totalView = findViewById(R.id.id_order_Tprice);
        totalView.setText(String.valueOf(total)+"元");
        recyclerView = findViewById(R.id.id_order_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderAdapter = new OrderAdapter(mContext,ListUtils.carts);
        recyclerView.setAdapter(orderAdapter);
    }
    private void getNetTime() {
        URL url = null;//取得资源对象
        try {
            url = new URL("http://www.baidu.com");
            URLConnection uc = url.openConnection();//生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            format = formatter.format(calendar.getTime());

            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(ld);
            time = formatter.format(calendar.getTime());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
