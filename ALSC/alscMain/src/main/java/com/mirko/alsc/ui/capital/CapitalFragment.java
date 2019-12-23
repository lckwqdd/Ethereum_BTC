package com.mirko.alsc.ui.capital;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alsc.utils.base.BaseFragment;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.FragmentHomeBinding;
import com.mirko.androidutil.utils.android.LogUtils;

import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Mirko on 2019/12/23.
 */

public class CapitalFragment extends BaseFragment {

    private final static String TAG = "HomeFragment";

    FragmentHomeBinding binding;
    private View mView;
    private View headView;
    private View footView;
    private TextView tvFooterMsg;//底部显示的信息


    private PtrFrameLayout ptrFrame;

    private int reLoad = 0;

    private boolean mIsRefreshing = false;

    public static CapitalFragment getInstance() {
        CapitalFragment sf = new CapitalFragment();
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_capital, null);
        binding = FragmentHomeBinding.bind(mView);
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
        mIsRefreshing = false;
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


    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {

    }


    /**
     * 跳转到文章发布页面
     */
    private void goToPublishArticle(){

    }



}
