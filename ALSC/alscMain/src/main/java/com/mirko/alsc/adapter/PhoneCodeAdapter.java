package com.mirko.alsc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.mirko.alsc.R;
import com.mirko.alsc.adapter.listener.RecycleViewItemClickListener;
import com.alsc.net.bean.PhoneCodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mirko on 2019/12/25.
 * 区号列表
 */

public class PhoneCodeAdapter extends RecyclerView.Adapter<PhoneCodeAdapter.ViewHolder> implements View.OnClickListener{


    private Context mContext;
    private RecycleViewItemClickListener recycleViewItemClickListener;
    private List<PhoneCodeInfo> datas = new ArrayList<>();

    public PhoneCodeAdapter(Context context, List<PhoneCodeInfo> datas){
        this.mContext = context;
        this.datas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_phone_code,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.view.setTag(position);
        holder.view.setOnClickListener(this);
        PhoneCodeInfo item = datas.get(position);
        holder.tvName.setText(item.getCountry());
        holder.tvCode.setText("+"+item.getCode());

        if(position == datas.size()-1){
            holder.viewLine.setVisibility(View.GONE);
        }else {
            holder.viewLine.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public View view;
        public View viewLine;
        public TextView tvName;
        public TextView tvCode;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvName = (TextView)view.findViewById(R.id.tv_name);
            tvCode = (TextView)view.findViewById(R.id.tv_code);
            viewLine = view.findViewById(R.id.view_line);
        }
    }

    public void setRecycleViewItemClickListener(RecycleViewItemClickListener recycleViewItemClickListener){
        this.recycleViewItemClickListener = recycleViewItemClickListener;
    }

    @Override
    public void onClick(View view) {
        if(recycleViewItemClickListener!=null ){
            recycleViewItemClickListener.OnItemOnclick(view,(int)view.getTag());
        }
    }
}
