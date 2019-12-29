package com.mirko.alsc.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsc.net.bean.NoticeResult;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mirko.alsc.R;
import com.mirko.alsc.views.images.AsyncImageView;
import com.mirko.androidutil.utils.android.DisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mirko on 2017/6/27.
 * 群公告列表
 */

public class NoticeListAdapter extends BaseArrayAdapter<NoticeResult> {

    public Context mContext;
    private List<NoticeResult> infos = new ArrayList<>();
    private float imageWidth;
    private int screenWidth;

    public NoticeListAdapter(Context context, List<NoticeResult> infos) {
        super(context, R.layout.list_notice_system, infos, false);
        this.mContext = context;
        this.infos = infos;
        imageWidth = mContext.getResources().getDimension(R.dimen.DIMEN_38PX);
        screenWidth = DisplayUtil.getInstance(context).getWidth();
    }

    @Override
    protected void addItemData(View convertView, final NoticeResult item, final int position) {

        ViewHolder holder = new ViewHolder(convertView);
        holder.tvNoticTitle.setText(item.getTitle());
        holder.tvNoticeTime.setText(item.getAdd_time());
        holder.tvNoticDes.setText(item.getSynopsis());
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
        public ImageView ivDot;
        public TextView tvNoticTitle; //
        public TextView tvNoticDes; //
        public TextView tvNoticeTime;  //
        public TextView tvNoticeDetail;  //

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ivDot = (ImageView) view.findViewById(R.id.iv_dot);
            tvNoticTitle = (TextView)view.findViewById(R.id.tv_notic_title);
            tvNoticDes = (TextView)view.findViewById(R.id.tv_notic_des);
            tvNoticeTime = (TextView)view.findViewById(R.id.tv_notic_time);
        }
    }
}
