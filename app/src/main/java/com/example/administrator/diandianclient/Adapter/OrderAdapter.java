package com.example.administrator.diandianclient.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.diandianclient.R;
import com.example.administrator.diandianclient.data.Cart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/10.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private Context context;
    private List<Cart> carts = new ArrayList<>();
    public OrderAdapter(Context context, List<Cart> carts){
        this.context = context;
        this.carts = carts;
    }
    public OrderAdapter(List<Cart> carts){
        this.carts = carts;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public interface OnItemChildAddClickListener {
        void onItemChildAddClick(View view, int position);
    }
    public interface OnItemChildLessClickListener {
        void onItemChildLessClick(View view, int position);
    }
    private OrderAdapter.OnItemClickListener onItemClickListener;
    private OrderAdapter.OnItemChildAddClickListener onItemChildAddClickListener;
    private OrderAdapter.OnItemChildLessClickListener onItemChildLessClickListener;
    public void setOnItemClickListener(OrderAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public void setOnItemChildAddClickListener(OrderAdapter.OnItemChildAddClickListener listener) {
        this.onItemChildAddClickListener = listener;
    }
    public void setOnItemChildLessClickListener(OnItemChildLessClickListener listener) {
        this.onItemChildLessClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Glide.with(context).load(carts.get(position).getImageURL()).into(holder.iv);
        holder.tv_name.setText(carts.get(position).getName());
        holder.tv_price.setText(String.valueOf(carts.get(position).getPrice())+" 元/份");
        holder.tv_number.setText("X"+String.valueOf(carts.get(position).getNumber()));
        holder.tv_tprice.setText(String.valueOf(carts.get(position).getNumber()*carts.get(position).getPrice())+"元");
    }

    @Override
    public int getItemCount() {
        return carts == null ? 0 : carts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv_name,tv_price,tv_number,tv_tprice;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.order_item_iv);
            tv_name = itemView.findViewById(R.id.order_item_name);
            tv_price = itemView.findViewById(R.id.order_item_price);
            tv_number = itemView.findViewById(R.id.order_item_number);
            tv_tprice = itemView.findViewById(R.id.order_item_tprice);
        }
    }
}
