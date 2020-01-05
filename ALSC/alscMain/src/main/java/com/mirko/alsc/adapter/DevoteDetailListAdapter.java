package com.mirko.alsc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsc.net.bean.NoticeResult;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mirko.alsc.R;
import com.mirko.alsc.bean.DevoteData;
import com.mirko.androidutil.utils.android.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 */

public class DevoteDetailListAdapter extends BaseArrayAdapter<DevoteData> {

    public Context mContext;
    private List<DevoteData> infos = new ArrayList<>();
    private float imageWidth;
    private int screenWidth;

    public DevoteDetailListAdapter(Context context, List<DevoteData> infos) {
        super(context, R.layout.list_devote_detail, infos, false);
        this.mContext = context;
        this.infos = infos;
        imageWidth = mContext.getResources().getDimension(R.dimen.DIMEN_38PX);
        screenWidth = DisplayUtil.getInstance(context).getWidth();
    }

    @Override
    protected void addItemData(View convertView, final DevoteData item, final int position) {

        ViewHolder holder = new ViewHolder(convertView);
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


    public class ViewHolder extends BaseViewHolder {

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
}
