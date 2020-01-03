package com.mirko.alsc.adapter;

import android.support.annotation.Nullable;

import com.alsc.wallet.entity.AddAddressBean;
import com.alsc.wallet.entity.AddWalletBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mirko.alsc.R;

import java.util.List;

public class AddAddressAdapter extends BaseQuickAdapter<AddAddressBean, BaseViewHolder> {

    public AddAddressAdapter(int layoutResId, @Nullable List<AddAddressBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, AddAddressBean addAddressBean) {
        helper.setText(R.id.symbolName, addAddressBean.getName())
                .setText(R.id.symbolAddress, addAddressBean.getAddress())
                .setText(R.id.symbolNote, addAddressBean.getNote());
    }
}
