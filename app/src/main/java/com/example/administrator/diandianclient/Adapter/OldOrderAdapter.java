package com.example.administrator.diandianclient.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.diandianclient.R;
import com.example.administrator.diandianclient.data.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/10.
 */

public class OldOrderAdapter extends RecyclerView.Adapter<OldOrderAdapter.ViewHolder> {
    private Context context;
    private String NAN[] = new String[20];
    private List<Order> orders = new ArrayList<>();
    public OldOrderAdapter(Context context, List<Order> orders){
        this.context = context;
        this.orders = orders;
    }
    public OldOrderAdapter(List<Order> orders,String[] nan){
        this.orders = orders;
        this.NAN = nan;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oldorder, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_name.setText(NAN[position+position]);
        holder.tv_number.setText("X"+NAN[position+position+1]);
    }

    @Override
    public int getItemCount() {
        return NAN == null ? 0 : NAN.length/2;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_number;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.oldorder_item_name);
            tv_number = itemView.findViewById(R.id.oldorder_item_number);
        }
    }
}
