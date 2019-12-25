package com.mirko.alsc.adapter;

import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mirko.alsc.R;
import java.util.List;

/**
 * 备注已选中的助记词
 */
public class VerifyBackupSelectedMnemonicWordsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public VerifyBackupSelectedMnemonicWordsAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String string) {
        helper.setText(R.id.tv_mnemonic_selected_word, string);
    }
}
