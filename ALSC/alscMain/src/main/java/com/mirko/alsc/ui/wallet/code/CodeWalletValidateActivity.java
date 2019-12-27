package com.mirko.alsc.ui.wallet.code;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.GridLayout;

import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.domain.VerifyMnemonicWordTag;
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.alsc.wallet.utils.WalletDaoUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.mirko.alsc.MainActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.VerifyBackupMnemonicWordsAdapter;
import com.mirko.alsc.adapter.VerifyBackupSelectedMnemonicWordsAdapter;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityCodeWalletBackupWordBinding;
import com.mirko.alsc.databinding.ActivityCodeWalletValidateBinding;
import com.mirko.alsc.ui.wallet.TotalAssetsActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 跨链钱包验证
 */
public class CodeWalletValidateActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityCodeWalletValidateBinding binding;
    private long walletId;
    private String walletMnemonic;
    private ArrayList<VerifyMnemonicWordTag> mnemonicWords = new ArrayList<>();
    private VerifyBackupMnemonicWordsAdapter verifyBackupMenmonicWordsAdapter;
    private VerifyBackupSelectedMnemonicWordsAdapter verifyBackupSelectedMnemonicWordsAdapter;
    private ArrayList<String>  selectedMnemonicWords = new ArrayList<>();
    private static final int  VERIFY_SUCCESS_RESULT = 2202;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_code_wallet_validate);
        binding.btnNext.setOnClickListener(this);
        binding.commonHeader.codeHeaderName.setText(getString(R.string.wv_validate));
        binding.commonHeader.codeHeaderTv1.setText(getString(R.string.wv_validate_sure));
        binding.commonHeader.codeHeaderTv2.setVisibility(View.GONE);
    }

    @Override
    public void initAttrs() {
        Intent intent = getIntent();
        walletId = intent.getLongExtra(Constants.walletId, -1);
        LogUtils.d("备份验证:"+walletId);
        walletMnemonic = intent.getStringExtra(Constants.walletMnemonic);

        //准备数据
        prepareDatas();
        selectedAadpterDatas();
        unSelectAadapterDatas();

    }
    private void unSelectAadapterDatas() {
        FlexboxLayoutManager layoutManager2 = new FlexboxLayoutManager(this);
        layoutManager2.setFlexWrap(FlexWrap.WRAP);
        layoutManager2.setAlignItems(AlignItems.STRETCH);
        binding.rvSelected.setLayoutManager(layoutManager2);
        verifyBackupSelectedMnemonicWordsAdapter = new VerifyBackupSelectedMnemonicWordsAdapter(R.layout.list_item_mnemoic_selected, selectedMnemonicWords);
        binding.rvSelected.setAdapter(verifyBackupSelectedMnemonicWordsAdapter);
    }

    private void selectedAadpterDatas() {
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        binding.rvMnemonic.setLayoutManager(layoutManager);
        verifyBackupMenmonicWordsAdapter = new VerifyBackupMnemonicWordsAdapter(R.layout.list_item_mnemoic, mnemonicWords);
        binding.rvMnemonic.setAdapter(verifyBackupMenmonicWordsAdapter);
    }

    private void prepareDatas() {
        String[] words = walletMnemonic.split("\\s+");
        for (int i = 0; i < words.length; i++) {
            VerifyMnemonicWordTag verifyMnemonicWordTag = new VerifyMnemonicWordTag();
            verifyMnemonicWordTag.setMnemonicWord(words[i]);
            mnemonicWords.add(verifyMnemonicWordTag);
        }
        // 乱序
        Collections.shuffle(mnemonicWords);
    }

    @Override
    public void loadData() {
        verifyBackupMenmonicWordsAdapter.setOnItemClickListener(((adapter, view, position) -> {
            String mnemonicWord = verifyBackupMenmonicWordsAdapter.getData().get(position).getMnemonicWord();
            if (verifyBackupMenmonicWordsAdapter.setSelection(position)) {
                verifyBackupSelectedMnemonicWordsAdapter.addData(mnemonicWord);
            }
        }));
        verifyBackupSelectedMnemonicWordsAdapter.setOnItemClickListener(((adapter, view, position) -> {
            List<VerifyMnemonicWordTag> datas = verifyBackupMenmonicWordsAdapter.getData();
            for (int i = 0; i < datas.size(); i++) {
                if (TextUtils.equals(datas.get(i).getMnemonicWord(), verifyBackupSelectedMnemonicWordsAdapter.getData().get(position))) {
                    verifyBackupMenmonicWordsAdapter.setUnselected(i);
                    break;
                }
            }
            verifyBackupSelectedMnemonicWordsAdapter.remove(position);
        }));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_next:
                verifyMnemonicWords();
                break;
        }
    }

    private void verifyMnemonicWords() {
        LogUtils.d("VerifyMnemonicBackUp", "Click!!");
        List<String> data = verifyBackupSelectedMnemonicWordsAdapter.getData();
        int size = data.size();
        if (size == 12) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < size; i++) {
                stringBuilder.append(data.get(i));
                if (i != size - 1) {
                    stringBuilder.append(" ");
                }
            }
            String verifyMnemonic = stringBuilder.toString();
            String trim = verifyMnemonic.trim();
            LogUtils.d("VerifyMnemonicBackUp", "verifyMnemonic:" + verifyMnemonic);
            LogUtils.d("VerifyMnemonicBackUp", "trim:" + trim);
            if (TextUtils.equals(trim, walletMnemonic)) {
                WalletDaoUtils.setIsBackup(walletId);
                BTCWalletDaoUtils.setIsBackup(walletId);
                goTo(TotalAssetsActivity.class);
                finish();
            } else {
                ToastUtils.showToast(R.string.verify_backup_failed);
            }
        } else {
            ToastUtils.showToast(R.string.verify_backup_failed);
        }
    }
}
