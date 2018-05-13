package com.example.administrator.diandianclient.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {
    private Context context;
    private List<Cart> carts = new ArrayList<>();
    public CarAdapter(Context context, List<Cart> carts){
        this.context = context;
        this.carts = carts;
    }
    public CarAdapter(List<Cart> carts){
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
    private CarAdapter.OnItemClickListener onItemClickListener;
    private CarAdapter.OnItemChildAddClickListener onItemChildAddClickListener;
    private CarAdapter.OnItemChildLessClickListener onItemChildLessClickListener;
    public void setOnItemClickListener(CarAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public void setOnItemChildAddClickListener(CarAdapter.OnItemChildAddClickListener listener) {
        this.onItemChildAddClickListener = listener;
    }
    public void setOnItemChildLessClickListener(OnItemChildLessClickListener listener) {
        this.onItemChildLessClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Glide.with(context).load(carts.get(position).getImageURL()).into(holder.iv);
        holder.tv_name.setText(carts.get(position).getName());
        holder.tv_price.setText(String.valueOf(carts.get(position).getPrice())+" 元/份");
        holder.tv_number.setText(String.valueOf(carts.get(position).getNumber()));
        holder.ib_add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(onItemChildAddClickListener!=null){
                    int pos = holder.getLayoutPosition();
                    onItemChildAddClickListener.onItemChildAddClick(holder.ib_add,pos);
                    holder.tv_number.setText(String.valueOf(Integer.valueOf(holder.tv_number.getText().toString())+1));
                }
            }
        });
        holder.ib_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemChildLessClickListener!=null){
                    int pos = holder.getLayoutPosition();
                    onItemChildLessClickListener.onItemChildLessClick(holder.ib_add,pos);
                    holder.tv_number.setText(String.valueOf(Integer.valueOf(holder.tv_number.getText().toString())-1));
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return carts == null ? 0 : carts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv_name,tv_price,tv_number;
        private ImageButton ib_add,ib_less;
        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.cart_item_iv);
            tv_name = itemView.findViewById(R.id.cart_item_name);
            tv_price = itemView.findViewById(R.id.cart_item_price);
            tv_number = itemView.findViewById(R.id.cart_item_number);
            ib_add = itemView.findViewById(R.id.cart_item_add);
            ib_less = itemView.findViewById(R.id.cart_item_less);
        }
    }
}
