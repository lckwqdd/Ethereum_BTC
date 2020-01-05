package com.mirko.alsc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mirko.alsc.R;
import com.mirko.alsc.adapter.listener.RecycleViewItemClickListener;
import com.mirko.alsc.bean.CurrencyData;
import com.mirko.alsc.bean.DevoteData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mirko on 2019/12/24.
 * 币种详细交易
 */

public class DevoteDetailAdapter extends RecyclerView.Adapter<DevoteDetailAdapter.ViewHolder> implements View.OnClickListener{

    private static final String TAG  = "DevoteDetailAdapter";

    private Context mContext;
    private RecycleViewItemClickListener recycleViewItemClickListener;
    private float imageWidth;
    private List<DevoteData> datas = new ArrayList<>();

    public DevoteDetailAdapter(Context context, List<DevoteData> datas){
        this.mContext = context;
        this.datas = datas;
        imageWidth = mContext.getResources().getDimension(R.dimen.DIMEN_38PX);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_devote_detail,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.view.setTag(position);
        holder.view.setOnClickListener(this);
        DevoteData item = datas.get(position);
        holder.tvTime.setText(item.getNumber_one());
        holder.tvKc.setText(item.getNumber_two()+"");
        holder.tvSpeed.setText(item.getNumber_three()+"");
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
        public TextView tvTime;
        public TextView tvKc;
        public TextView tvSpeed;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvTime = (TextView)view.findViewById(R.id.tv_time);
            tvKc = (TextView)view.findViewById(R.id.tv_kc);
            tvSpeed = (TextView)view.findViewById(R.id.tv_speed);
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
