package com.mirko.alsc.ui.capital;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alsc.utils.base.BaseFragment;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.CurrencyAdapter;
import com.mirko.alsc.adapter.listener.RecycleViewItemClickListener;
import com.mirko.alsc.bean.CurrencyData;
import com.mirko.alsc.databinding.FragmentCapitalBinding;
import com.mirko.androidutil.utils.android.LogUtils;

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
//        int height = StatusBarUtil.getStatusBarHeight(getActivity()) + UiUtils.dip2px(56);
//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height);
//        binding.flTitle.setPadding(0,StatusBarUtil.getStatusBarHeight(getActivity()),0,0);
//        binding.flTitle.setLayoutParams(layoutParams);
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
            }
        });

    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {

    }



}
