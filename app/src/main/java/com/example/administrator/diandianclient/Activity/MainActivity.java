package com.example.administrator.diandianclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.diandianclient.Adapter.SectionAdapter;
import com.example.administrator.diandianclient.data.MySection;
import com.example.administrator.diandianclient.R;
import com.example.administrator.diandianclient.data.Video;
import com.example.administrator.diandianclient.data.Cart;
import com.example.administrator.diandianclient.data.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import es.dmoral.toasty.Toasty;


public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List<MySection> mData;
    private ImageView iv_my,iv_cart;
    private BGABanner bgaBanner;
    private SectionAdapter sectionAdapter;
    private WebView webView;
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    if(mData == null){
                        Toasty.error(MainActivity.this, "请检查网络连接", Toast.LENGTH_LONG).show();
                    }else{
                        bgaBanner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
                            @Override
                            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                                Glide.with(MainActivity.this)
                                        .load(model)
                                        .placeholder(R.drawable.holder)
                                        .error(R.drawable.holder)
                                        .centerCrop()
                                        .dontAnimate()
                                        .into(itemView);
                            }
                        });
                        bgaBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
                            @Override
                            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
//                                Toast.makeText(banner.getContext(), "点击了" + position, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                intent.putExtra("position",position);
                                intent.setClass(MainActivity.this,WEBViewActivity.class);
                                startActivity(intent);
//                                if(position==1){
//                                    webView.loadUrl("https://www.4008823823.com.cn/kfcios/Html/index.html?utm_campaign=KFC_Online_Ordering&utm_source=Official&utm_medium=HP&utm_content=ri");
//                                }
//                                switch (position){
//                                    case 0:webView.loadUrl("https://www.4008823823.com.cn/kfcios/Html/index.html?utm_campaign=KFC_Online_Ordering&utm_source=Official&utm_medium=HP&utm_content=ri");break;
//                                    case 1:webView.loadUrl("http://www.kfc.com.cn/kfccda/minisite/superapp/index.html");break;
//                                    case 2:webView.loadUrl("http://www.kfc.com.cn/kfccda/minisite/kfcmaydayholiday/");break;
//                                }

                            }
                        });
                        bgaBanner.setData(Arrays.asList("http://lc-tadkvyab.cn-n1.lcfile.com/f888abb4aeaf3502c7c8.jpg", "http://lc-tadkvyab.cn-n1.lcfile.com/69e4e34c9e5fb0399217.jpg", "http://lc-tadkvyab.cn-n1.lcfile.com/58a40910d26f7fe86238.jpg"), Arrays.asList("", "", ""));
                        mRecyclerView = findViewById(R.id.id_recyclerView);
                        mRecyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
                        sectionAdapter = new SectionAdapter(R.layout.item_section_content,R.layout.def_section_head,mData);
                        sectionAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                                MySection mySection = mData.get(position);
                                if (mySection.isHeader) {
                                    Toast.makeText(MainActivity.this, mySection.header + position, Toast.LENGTH_LONG).show();
                                }else {
//                                    Toast.makeText(MainActivity.this,ListUtils.size()+"",Toast.LENGTH_LONG).show();
                                    if(ListUtils.carts.size()==0){
                                        ListUtils.carts.add(new Cart(mySection.t.getImageURL(), mySection.t.getName(), mySection.t.getPrice(), 1,mySection.t.getId()));
//                                        Toast.makeText(MainActivity.this,carts.get(0).getName()+""+carts.get(0).getNumber(),Toast.LENGTH_LONG).show();
                                    }else{
                                        boolean flag = true;
                                        for (int i = 0; i < ListUtils.size(); i++) {
//                                            Toast.makeText(MainActivity.this,ListUtils.getID(i)+"",Toast.LENGTH_LONG).show();
                                            if(ListUtils.getID(i)==mySection.t.getId()){
                                                ListUtils.carts.get(i).setNumber(ListUtils.carts.get(i).getNumber()+1);
//                                                Toast.makeText(MainActivity.this,"1",Toast.LENGTH_LONG).show();
                                                flag = false;
                                            }
                                        }
                                        if(flag){
                                            ListUtils.carts.add(new Cart(mySection.t.getImageURL(), mySection.t.getName(), mySection.t.getPrice(), 1,mySection.t.getId()));
                                        }
                                    }
                                }
                            }
                        });
                        sectionAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                            @Override
                            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                                Toast.makeText(MainActivity.this, "onItemChildClick" + position, Toast.LENGTH_LONG).show();
                            }
                        });
                        mRecyclerView.setAdapter(sectionAdapter);
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initdata();
            }
        });
        initview();
    }
    private void initview() {
        iv_my = findViewById(R.id.id_home_my);
        iv_cart = findViewById(R.id.id_home_cart);
        bgaBanner = findViewById(R.id.banner_guide_content);
        iv_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyActivity();
            }
        });
        iv_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCartActivity();
            }
        });
        webView = findViewById(R.id.id_home_wv);
    }
    private void initdata() {
        AVQuery queryID = new AVQuery("DishesID");
        queryID.selectKeys(Arrays.asList("DishesID", "IDName"));
        queryID.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(final List<AVObject> queryIDlist, AVException e) {
                AVQuery query = new AVQuery("Dishes");
                query.selectKeys(Arrays.asList("DishesID", "Name","Price","ImageURL","ID"));
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        mData = new ArrayList<>();
                        for (AVObject avObject : queryIDlist) {
                            mData.add(new MySection(true, avObject.getString("IDName"), true));
                            for (AVObject object : list) {
                                if((avObject.get("DishesID").toString()).equals(object.getString("DishesID").toString())){
                                    mData.add(new MySection(new Video(object.getString("ImageURL"), object.getString("Name"),object.getString("Price"),object.get("ID").toString())));
                                    Message msg = handler.obtainMessage(0,mData);
                                    handler.sendMessage(msg);
                                }
                            }
                        }
                    }
                });
            }
        });
    }
    private void startMyActivity(){
        AVUser currentUser = AVUser.getCurrentUser();
        if (currentUser != null) {
            ListUtils.user.setUserNmae(AVUser.getCurrentUser().getString("name"));
            ListUtils.user.setPhoneNumber(AVUser.getCurrentUser().getString("phoneNumber"));
            Log.d("uinfo", ListUtils.user.getUserNmae()+ListUtils.user.getPhoneNumber());
            //用户缓存不为空，跳转到个人中心
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,MyActivity.class);
            startActivity(intent);
        } else {
            //用户缓存为空，跳转到登陆
            Intent intent = new Intent();
            intent.setClass(MainActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    }
    private void startCartActivity(){
        Intent intent = new Intent();
        intent.setClass(MainActivity.this,CartActivity.class);
        startActivity(intent);

    }

}
