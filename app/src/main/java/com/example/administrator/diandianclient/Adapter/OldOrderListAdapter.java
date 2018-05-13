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

public class OldOrderListAdapter extends RecyclerView.Adapter<OldOrderListAdapter.ViewHolder> {
    private Context context;
    private List<Order> orders = new ArrayList<>();
    public OldOrderListAdapter(Context context, List<Order> orders){
        this.context = context;
        this.orders = orders;
    }
    public OldOrderListAdapter(List<Order> orders){
        this.orders = orders;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    private RecyclerAdapter.OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(RecyclerAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_oldorderlist, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String status = null;
        switch (orders.get(position).getOStatus()){
            case "0":
                status = "待送达";
                break;
            case "1":
                status = "已完成";
                break;
            case "2":
                status = "已取消";
                break;
        }
        holder.tv_name.setText("订单号："+orders.get(position).getONumber());
        holder.tv_number.setText("订单状态："+status);
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
        return orders == null ? 0 : orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_number;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.oldorderlist_item_name);
            tv_number = itemView.findViewById(R.id.oldorderlist_item_status);
        }
    }
}
