package com.mirko.alsc.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mirko.alsc.R;
import com.mirko.alsc.bean.AlscCurrentData;

import java.util.List;

/**
 * Created by Mirko on 2019/12/24.
 * 币种详细交易
 */

public class AlasTransferAndCollectAdapter extends BaseQuickAdapter<AlscCurrentData, BaseViewHolder> {

    private static final String TAG = "CountingCourseAdapter";

    public AlasTransferAndCollectAdapter(int layoutResId, @Nullable List<AlscCurrentData> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, AlscCurrentData item) {
        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_time, item.getTime())
                .setText(R.id.tv_total_money, item.getMount())
                .setText(R.id.tv_income_count, item.getState());

    }
}
