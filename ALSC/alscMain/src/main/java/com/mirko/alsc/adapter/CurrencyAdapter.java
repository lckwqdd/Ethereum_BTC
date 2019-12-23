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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mirko on 2017/4/12.
 * 币种列表
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> implements View.OnClickListener{

    private static final String TAG  = "CountingCourseAdapter";

    private Context mContext;
    private RecycleViewItemClickListener recycleViewItemClickListener;
    private float imageWidth;
    private List<CurrencyData> datas = new ArrayList<>();

    public CurrencyAdapter(Context context, List<CurrencyData> datas){
        this.mContext = context;
        this.datas = datas;
        imageWidth = mContext.getResources().getDimension(R.dimen.DIMEN_38PX);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_currency,parent,false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.view.setTag(position);
        holder.view.setOnClickListener(this);
        CurrencyData item = datas.get(position);
        holder.tvCurrencyName.setText(item.getName());
        holder.tvCurrencyName.setClickable(false);
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
        public TextView tvCurrencyName;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            tvCurrencyName = (TextView)view.findViewById(R.id.tv_currency_name);
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
