package com.mirko.alsc.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.alsc.wallet.entity.AddWalletBean;
import com.alsc.wallet.entity.Token;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mirko.alsc.R;

import java.util.List;

public class AddWalletAdapter extends BaseQuickAdapter<AddWalletBean, BaseViewHolder> {

    public AddWalletAdapter(int layoutResId, @Nullable List<AddWalletBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, AddWalletBean addWalletBean) {
        helper.setImageResource(R.id.item_currenty,addWalletBean.getSymbol())
                .setText(R.id.symbol,addWalletBean.getSymbolName())
                .setText(R.id.symbol_describe,addWalletBean.getSymbolDescrice());
    }
}
