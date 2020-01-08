package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alsc.net.db.bean.ETHWallet;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.entity.AddWalletBean;
import com.alsc.wallet.interact.FetchWalletInteract;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.AddWalletAdapter;
import com.mirko.alsc.adapter.TokensAdapter;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityAddWalletBinding;
import com.mirko.alsc.databinding.ActivityTotalAssetsBinding;
import com.mirko.alsc.ui.wallet.address.AddAddressActivity;
import com.mirko.alsc.ui.wallet.code.CodeWalletCreateActivity;
import com.mirko.alsc.ui.wallet.code.CodeWalletImportActivity;
import com.mirko.androidutil.view.CustomeDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 添加钱包界面
 */
public class AddWalletActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityAddWalletBinding binding;
    private AddWalletAdapter adapter;
    private CustomeDialog getCodeDialog;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_wallet);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.total_assets_add));
    }

    @Override
    public void initAttrs() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.addWalletRv.setLayoutManager(layoutManager);
        adapter = new AddWalletAdapter(R.layout.list_item_add_wallet, genrateData());
        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            ToastUtils.showToast("position:" + position);
            showCreateDialog(position);
        }));
        binding.addWalletRv.setAdapter(adapter);
    }

    private void showCreateDialog(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_create_wallet, null);
        getCodeDialog = new CustomeDialog(this, view);
        getCodeDialog.setCanceledOnTouchOutside(false);
        getCodeDialog.setGravity(Gravity.BOTTOM);
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getCodeDialog.getWindow().getAttributes();
        lp.width = (int) (display.getWidth()); //设置宽度
        getCodeDialog.getWindow().setAttributes(lp);

        TextView walletCreate = view.findViewById(R.id.wallet_create);
        TextView walletImport = view.findViewById(R.id.wallet_import);
        TextView walletCancle = view.findViewById(R.id.wallet_cancle);

        walletCreate.setOnClickListener((v -> {
            goTo(CodeWalletCreateActivity.class);
            getCodeDialog.dismiss();
        }));
        walletImport.setOnClickListener((v -> {
            //跳去冷钱包界面
            Intent intent = new Intent(AddWalletActivity.this, CodeWalletImportActivity.class);
            intent.putExtra(Constants.pointPosition, position);
            startActivity(intent);
            finish();
            getCodeDialog.dismiss();
        }));
        walletCancle.setOnClickListener((v -> {
            getCodeDialog.dismiss();
        }));
        getCodeDialog.show();
    }

    private List<AddWalletBean> genrateData() {
        List<AddWalletBean> addWalletBeanList = new ArrayList<>();

        addWalletBeanList.add(new AddWalletBean(R.mipmap.icon_currency, getString(R.string.total_assets_alsc), getString(R.string.total_assets_asc)));
        addWalletBeanList.add(new AddWalletBean(R.mipmap.icon_currency, getString(R.string.total_assets_btc), getString(R.string.total_assets_bit)));
        addWalletBeanList.add(new AddWalletBean(R.mipmap.icon_currency, getString(R.string.total_assets_eth), getString(R.string.total_assets_eth)));
        addWalletBeanList.add(new AddWalletBean(R.mipmap.icon_currency, getString(R.string.total_assets_usdt), getString(R.string.total_assets_tusd)));
        return addWalletBeanList;
    }

    @Override
    public void loadData() {
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }
}
