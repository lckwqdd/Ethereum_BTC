package com.mirko.alsc.ui.wallet;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.alsc.net.api.AddressRegisterApi;
import com.alsc.net.bean.entity.AddressRegisterResultEntity;
import com.alsc.net.bean.request.AddressRegisterRequest;
import com.alsc.net.db.bean.BtcWallet;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.bean.response.BalanceBeanResponse;
import com.alsc.wallet.interact.FetchWalletInteract;
import com.alsc.wallet.utils.LogUtils;
import com.alsc.wallet.utils.ToastUtils;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.ExpandableItemAdapter;
import com.mirko.alsc.adapter.TokensAdapter;
import com.mirko.alsc.databinding.ActivityTotalAssetsBinding;
import com.mirko.alsc.entity.Level0Item;
import com.mirko.alsc.entity.Level1Item;
import com.mirko.alsc.ui.wallet.address.AddAddressActivity;
import com.mirko.alsc.utils.UrlRequstUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function5;
import io.reactivex.schedulers.Schedulers;

/**
 * 总资产
 */
public class TotalAssetsActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivityTotalAssetsBinding binding;
    private FetchWalletInteract fetchWalletInteract;
    private ETHWallet currentEthWallet;
    private boolean isCanSee;
    private ExpandableItemAdapter adapter;
    private List<ETHWallet> allEthWallets = new ArrayList<>();
    private List<BtcWallet> allBtcWallets = new ArrayList<>();


    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_total_assets);
        binding.ivAddConcact.setOnClickListener(this);
        binding.ivSee.setOnClickListener(this);
        binding.ivAddWallet.setOnClickListener(this);
        fetchWalletInteract = new FetchWalletInteract();
    }

    @Override
    public void initAttrs() {
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void loadData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchWalletInteract.findDefault().subscribe(this::onSuccess, this::onError);
        fetchWalletInteract.fetch().subscribe(this::allEthDatas, this::onError);
        fetchWalletInteract.fetchBTC().subscribe(this::allBtcDatas, this::onError);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    initRecycleDatas();
                });
            }
        }, 2000);
    }

    private void mergeMoney() {
        Observable<Float> btcObservable = createWalletSymbol("", "btc");
        Observable<Float> ethObservable = createWalletSymbol("", "eth");
        Observable<Float> busdtObservable = createWalletSymbol("", "busdt");
        Observable<Float> eusdtObservable = createWalletSymbol("", "eusdt");
        Observable<Float> alscObservable = createWalletSymbol("", "alsc");

        //总金额
        Observable.zip(btcObservable, ethObservable, busdtObservable, eusdtObservable, alscObservable, new Function5<Float, Float, Float, Float, Float, Float>() {

            @Override
            public Float apply(Float aFloat, Float aFloat2, Float aFloat3, Float aFloat4, Float aFloat5) throws Exception {
                return aFloat + aFloat2 + aFloat3 + aFloat4 + aFloat5;
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe((aFloat -> {
                    binding.tvAccount.setText("" + aFloat);
                }));
        //单金额显示
        btcObservable.subscribe((aFloat -> {

        }));
        ethObservable.subscribe((aFloat -> {

        }));
        busdtObservable.subscribe((aFloat -> {

        }));
        eusdtObservable.subscribe((aFloat -> {

        }));
        alscObservable.subscribe((aFloat -> {

        }));

    }

    private Observable<Float> createWalletSymbol(String address, String symbol) {
        return Observable.create((ObservableOnSubscribe<Float>) emitter -> {
            BalanceBeanResponse temp = UrlRequstUtils.getBalanceByAddress(this, new AddressRegisterRequest(address, symbol));
            emitter.onNext(temp.getAssets());
        }).subscribeOn(Schedulers.io());
    }

    private void onSuccess(ETHWallet ethWallet) {
        currentEthWallet = ethWallet;
    }

    private void allBtcDatas(List<BtcWallet> btcWallets) {
        LogUtils.d("allBtcDatas:" + btcWallets.size());
        allBtcWallets = btcWallets;
    }

    private void allEthDatas(List<ETHWallet> ethWallets) {
        LogUtils.d("allEthDatas:" + ethWallets.size());
        allEthWallets = ethWallets;
    }

    private void initRecycleDatas() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.totalAssetsRv.setLayoutManager(layoutManager);
        adapter = new ExpandableItemAdapter(generateData());
        binding.totalAssetsRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(((adapter1, view, position) -> {
            ToastUtils.showToast("position:"+position);
            switch (position) {
                case 1:
                    goTo(ETHTranslateAndCollectActivity.class);
                    break;

            }
        }));
    }

//    /**
//     * 通过注册地址和对应币种获取对应金额
//     *
//     * @param addressRegisterRequest
//     */
//    private void getCurrentUserInfo(AddressRegisterRequest addressRegisterRequest) {
//        HttpManager.getInstance().doHttpDeal(new AddressRegisterApi((new HttpOnNextListener<AddressRegisterResultEntity>() {
//            @Override
//            public void onNext(AddressRegisterResultEntity result) {
//                tempAddressRegisterResult = result;
//                binding.tvAccount.setText(result.getAssets());
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtils.d("通过注册地址异常:" + e.toString());
//                super.onError(e);
//            }
//
//        }), TotalAssetsActivity.this, addressRegisterRequest));
//    }

    private ArrayList<MultiItemEntity> generateData() {
        LogUtils.d("获取数据generateData");
        if (allEthWallets != null && allBtcWallets != null) {
            int lv0Count = 4;
            String[] nameLists = {"ALSC", "ETH", "BTC", "USDT"};
            String[] balanceLists = {"0", "0", "0", "0"};
            String[] valueLists = {"¥0.00", "=¥0.00", "=¥0.00", "=¥0.00"};
            int[] imgsLists = {R.mipmap.icon_currency, R.mipmap.icon_currency, R.mipmap.icon_currency, R.mipmap.icon_currency};


            ArrayList<MultiItemEntity> res = new ArrayList<>();
            Level1Item lv1;
            for (int i = 0; i < lv0Count; i++) {
                Level0Item item = new Level0Item(imgsLists[i], nameLists[i], balanceLists[i], valueLists[i]);
                if (i == 0) {
                    //ALSC
                    if (allEthWallets.size() > 0) {
                        for (int j = 0; j < allEthWallets.size(); j++) {
                            lv1 = new Level1Item("已有的ALSC地址", allEthWallets.get(j).getAddress(), "0", "0");
                            item.addSubItem(lv1);
                        }
                    }
                } else if (i == 1) {
                    //ETH
                    if (allEthWallets.size() > 0) {
                        for (int j = 0; j < allEthWallets.size(); j++) {
                            lv1 = new Level1Item("已有的ETH地址", allEthWallets.get(j).getAddress(), "0", "0");
                            item.addSubItem(lv1);
                        }
                    }
                } else if (i == 2) {
                    //BTC
                    if (allBtcWallets.size() > 0) {
                        for (int j = 0; j < allBtcWallets.size(); j++) {
                            lv1 = new Level1Item("已有的BTC地址", allBtcWallets.get(j).getAddress(), "0", "0");
                            item.addSubItem(lv1);
                        }
                    }
                } else if (i == 3) {
                    //USDT
                    if (allBtcWallets.size() > 0) {
                        for (int j = 0; j < allBtcWallets.size(); j++) {
                            lv1 = new Level1Item("已有的BTC地址", allBtcWallets.get(j).getAddress(), "0", "0");
                            item.addSubItem(lv1);
                        }
                    }
                }
                res.add(item);
            }
            return res;
        }
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add_concact:
                goTo(AddAddressActivity.class);
                break;
            case R.id.iv_add_wallet:
                goTo(AddWalletActivity.class);
                break;
            case R.id.iv_see:
                switchStyle(isCanSee);
                break;
        }
    }

    private void switchStyle(boolean isCanSee) {
    }

    private void onError(Throwable throwable) {
        LogUtils.d("取数据异常:" + throwable.toString());
    }
}

