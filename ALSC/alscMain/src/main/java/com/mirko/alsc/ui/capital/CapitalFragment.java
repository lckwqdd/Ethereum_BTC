package com.mirko.alsc.ui.capital;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alsc.net.api.WCapitalInfoApi;
import com.alsc.net.api.WTransferRecordApi;
import com.alsc.net.bean.entity.CapitalResultEntity;
import com.alsc.net.bean.entity.TransferRecordeEntity;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.BaseFragment;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.CurrencyAdapter;
import com.mirko.alsc.adapter.listener.RecycleViewItemClickListener;
import com.mirko.alsc.bean.CurrencyData;
import com.mirko.alsc.databinding.FragmentCapitalBinding;
import com.mirko.alsc.ui.wallet.TotalAssetsActivity;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.alsc.utils.UrlRequstUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mirko on 2019/12/23.
 */

public class CapitalFragment extends BaseFragment {

    private final static String TAG = "CapitalFragment";

    FragmentCapitalBinding binding;
    private View mView;
    private Context mContext;
    private CurrencyAdapter currencyAdapter;
    private List<CurrencyData> currencyDatas;


    public static CapitalFragment getInstance() {
        CapitalFragment sf = new CapitalFragment();
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_capital, null);
        binding = FragmentCapitalBinding.bind(mView);
        mContext = getActivity();
        return mView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "onCreate：");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

        currencyDatas = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            CurrencyData currencyData = new CurrencyData();
            currencyData.setName("BTC");
            currencyDatas.add(currencyData);
        }
        currencyAdapter = new CurrencyAdapter(mContext, currencyDatas);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        binding.rvCurrency.setLayoutManager(layoutManager);
        binding.rvCurrency.setAdapter(currencyAdapter);
        currencyAdapter.notifyDataSetChanged();
        currencyAdapter.setRecycleViewItemClickListener(new RecycleViewItemClickListener() {
            @Override
            public void OnItemOnclick(View view, int position) {
                startActivity(new Intent(mContext,CurrencyDetailActivity.class));
//                startActivity(new Intent(mContext, TotalAssetsActivity.class));
            }
        });

    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        getCapitalInfo();
        UrlRequstUtils.getUtxo1(getActivity(),"1MUz4VMYui5qY1mxUiG8BQ1Luv6tqkvaiL");
    }

    /**
     * 资产信息
     */
    private void getCapitalInfo() {

        HttpManager.getInstance().doHttpDeal(new WCapitalInfoApi((new HttpOnNextListener<CapitalResultEntity>() {
            @Override
            public void onNext(CapitalResultEntity result) {
                LogUtils.d(TAG, "获取成功");
                binding.tvIncomeCount.setText(result.getTotal_income()+"");
                binding.tvCapitalCount.setText(result.getTotal_alsc()+"");
                binding.tvExchangeCount.setText(result.getTotal_ach()+"");
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }


        }), (RxAppCompatActivity) getActivity(), ComUtils.getTokenCache()));
    }



}
