package com.mirko.alsc.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.alsc.wallet.domain.VerifyMnemonicWordTag;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.mirko.alsc.R;
import java.util.Collections;
import java.util.List;


/**
 * 助记词适配器
 */
public class VerifyBackupMnemonicWordsAdapter extends BaseQuickAdapter<VerifyMnemonicWordTag, BaseViewHolder> {

    public VerifyBackupMnemonicWordsAdapter(int layoutResId, @Nullable List<VerifyMnemonicWordTag> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VerifyMnemonicWordTag verifyMnemonicWordTag) {
        LinearLayout llTag = helper.getView(R.id.lly_tag);
        if (verifyMnemonicWordTag.isSelected()) {
            llTag.setBackgroundResource(R.drawable.bg_blue_middle_radius);
            helper.setTextColor(R.id.tv_mnemonic_word,mContext.getResources().getColor(R.color.snow));
        } else {
            llTag.setBackgroundResource(R.drawable.bg_black_divder);
            helper.setTextColor(R.id.tv_mnemonic_word,mContext.getResources().getColor(R.color.snow));
        }
        helper.setText(R.id.tv_mnemonic_word, verifyMnemonicWordTag.getMnemonicWord());
    }

    public boolean setSelection(int position) {

        VerifyMnemonicWordTag verifyMnemonicWordTag = getData().get(position);
        if (verifyMnemonicWordTag.isSelected()) {
            return false;
        }
        verifyMnemonicWordTag.setSelected(true);
        Collections.shuffle(getData());
        notifyDataSetChanged();
        return true;
    }

    public boolean setUnselected(int position) {

        VerifyMnemonicWordTag verifyMnemonicWordTag = getData().get(position);
        if (!verifyMnemonicWordTag.isSelected()) {
            return false;
        }
        verifyMnemonicWordTag.setSelected(false);
        Collections.shuffle(getData());
        notifyDataSetChanged();
        return true;
    }

}
