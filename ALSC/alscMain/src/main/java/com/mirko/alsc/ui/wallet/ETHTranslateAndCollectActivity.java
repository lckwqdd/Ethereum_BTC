package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alsc.net.db.bean.ETHWallet;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.bean.request.TransfersRequest;
import com.alsc.wallet.bean.response.TransferResponse;
import com.alsc.wallet.interact.FetchWalletInteract;
import com.alsc.wallet.utils.LogUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.AlasTransferAndCollectAdapter;
import com.mirko.alsc.bean.AlscCurrentData;
import com.mirko.alsc.bean.CurrencyData;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivityEthTransferAndCollectBinding;
import com.mirko.alsc.ui.entity.TabWalletEntity;
import com.mirko.alsc.utils.UrlRequstUtils;
import com.mirko.androidutil.utils.ThreadUtils;
import com.mirko.androidutil.utils.android.ToastUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by WuQuan on 2019/12/29.
 * 币种详细页面
 */
public class ETHTranslateAndCollectActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "AlscTranslateAndCollectActivity";
    ActivityEthTransferAndCollectBinding binding;
    private List<AlscCurrentData> currencyDatas;
    private AlasTransferAndCollectAdapter adapter;
    private String address;
    private FetchWalletInteract fetchWalletInteract;
    private int mTitleIds[] = new int[]{
            R.string.capital_transfer,
            R.string.capital_receiveables,
            R.string.capital_all,
            R.string.capital_exchange
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_eth_transfer_and_collect);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        binding.llTransfer.setOnClickListener(this);
        binding.llReceivables.setOnClickListener(this);
        fetchWalletInteract = new FetchWalletInteract();
        fetchWalletInteract.findDefault().subscribe(this::onSuccess, this::onError);

        super.onCreate(savedInstanceState);
    }

    private void onSuccess(ETHWallet ethWallet) {
        binding.address.setText(ethWallet.getAddress());
    }

    private void transfer() {
    }

    private void onError(Throwable throwable) {
        LogUtils.d("取数据异常:" + throwable.toString());
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);
        binding.commonHeader.tvHeaderMiddle.setText(getString(R.string.total_assets_eth));

        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        //设置tab的标题、选中图标、未选中图标
        for (int i = 0; i < mTitleIds.length; i++) {
            mTabEntities.add(new TabWalletEntity(getString(mTitleIds[i])));
        }
        binding.ctCapitalTitle.setTabData(mTabEntities);

        currencyDatas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            AlscCurrentData currencyData = new AlscCurrentData();
            currencyData.setName("BTC");
            currencyDatas.add(currencyData);
        }
        adapter = new AlasTransferAndCollectAdapter(R.layout.list_tranfer_and_collect_detail, currencyDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ETHTranslateAndCollectActivity.this);
        binding.rvCurrencyDetail.setLayoutManager(layoutManager);
        binding.rvCurrencyDetail.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            ToastUtils.showShort("position=" + position);
            switch (position){
                case  1:
                    Intent intent = new Intent(this, ETHSymbolDetailActivity.class);
                    intent.putExtra(Constants.TRANSFER_POSIITION, position);
                    startActivity(intent);
                    break;
            }
        }));
        binding.ctCapitalTitle.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                LogUtils.d(TAG, "当前选中：" + position);
                switch (position) {
                    case 0:
                        tranferRecord(Constants.TEST_ACCOUNT_SEND);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;

                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    /**
     * 转账记录
     */
    private void tranferRecord(String address) {
        ThreadUtils.executeBySingle(new ThreadUtils.Task<TransferResponse>() {
            @Override
            public TransferResponse doInBackground() throws Throwable {
                TransferResponse transferResponse = UrlRequstUtils.CodeTransfer(ETHTranslateAndCollectActivity.this, new TransfersRequest(address, Constants.SYMBOL_ETH,
                        2, 1, 5));
                LogUtils.d("转账记录:" + GsonUtils.toJson(transferResponse));
                return transferResponse;
            }

            @Override
            public void onSuccess(TransferResponse result) {
                if (result != null) {
                    TransferResponse.DataBean data = result.getData();
                    if (data != null) {
                        currencyDatas.clear();
                        for (int i = 0; i < data.getList().size(); i++) {
                            TransferResponse.DataBean.ListBean item = data.getList().get(i);
                            currencyDatas.add(new AlscCurrentData(item.getHash(), item.getAdd_time(), item.getAmount(), getState(item.getStatus())));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFail(Throwable t) {

            }
        });

    }

    private String getState(int type) {
        String str = "";
        if (type == 1) {
            str = getString(R.string.meke_sure);
        } else if (type == 2) {
            str = getString(R.string.has_sure);
        } else if (type == 3) {
            str = getString(R.string.tranlation_fali);
        }
        return str;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(ETHTranslateAndCollectActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();
                break;
            case R.id.ll_transfer:
                goTo(ETHTransferActivity.class);
                break;
            case R.id.ll_receivables:
                goTo(ETHCollectActivity.class);
                break;
            case R.id.iv_header_left:
                onBackPressed();
                break;
        }
    }
}
