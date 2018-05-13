package com.example.administrator.diandianclient.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.administrator.diandianclient.R;

/**
 * Created by Administrator on 2018/1/9.
 */

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {
    private String[] labels ={"姓名","电话"};

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.label.setText(labels[position]);
        holder.word.setText(holder.word.getText());
    }

    @Override
    public int getItemCount() {
        return labels.length;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView label;
        private EditText word;
        public ViewHolder(View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.item_info_label);
            word = itemView.findViewById(R.id.item_info_word);
        }
    }
}
