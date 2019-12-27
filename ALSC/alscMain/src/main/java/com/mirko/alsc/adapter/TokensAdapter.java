package com.mirko.alsc.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import com.alsc.wallet.entity.Token;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mirko.alsc.R;
import java.util.List;

public class TokensAdapter extends BaseQuickAdapter<Token, BaseViewHolder> {

    public TokensAdapter(int layoutResId, @Nullable List<Token> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, Token token) {
        Log.d(TAG, "convert: helper:" + helper + ", token:" + token);
        helper.setText(R.id.symbol, token.tokenInfo.symbol);
        helper.setText(R.id.balance, token.balance);
        helper.setText(R.id.tv_property_cny, token.value);

    }

    public void setTokens(List<Token> tokens) {
        setNewData(tokens);
    }
}
