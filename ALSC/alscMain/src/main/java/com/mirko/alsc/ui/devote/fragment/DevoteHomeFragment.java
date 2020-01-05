package com.mirko.alsc.ui.devote.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alsc.net.api.HomeMsgApi;
import com.alsc.net.bean.CapitalData;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.BaseFragment;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.FragmentDevoteHomeBinding;
import com.mirko.alsc.ui.devote.DevoteCjjdActivity;
import com.mirko.alsc.ui.devote.DevoteCygxActivity;
import com.mirko.alsc.ui.devote.DevoteFsDetailActivity;
import com.mirko.alsc.ui.devote.DevoteGxDetailActivity;
import com.mirko.alsc.ui.devote.DevoteJcDetailActivity;
import com.mirko.alsc.ui.devote.DevoteXyDetailActivity;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by Mirko on 2019/12/21.
 */

public class DevoteHomeFragment extends BaseFragment implements View.OnClickListener {

    private final static String TAG = "HomeFragment";

    FragmentDevoteHomeBinding binding;
    private View mView;
    private CapitalData capitalData = new CapitalData();


    public static DevoteHomeFragment getInstance() {
        DevoteHomeFragment sf = new DevoteHomeFragment();
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_devote_home, null);
        binding = FragmentDevoteHomeBinding.bind(mView);

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
        binding.setClickListener(this);
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        loadHomeData();
    }


    /**
     * 加载首页数据
     */

    private void loadHomeData() {

        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new HomeMsgApi((new HttpOnNextListener<HomeMsgResultEntity>() {
            @Override
            public void onNext(HomeMsgResultEntity result) {
                if (result != null) {
                    if (result.getUser_info() != null) {
                        capitalData.setContri(result.getContri());
                        capitalData.setFenx_total(result.getFenx_total());
                        capitalData.setMax_sinvestment(result.getMax_sinvestment());
                        capitalData.setShare_total(result.getShare_total());
                        capitalData.setSinvestment(result.getSinvestment());
                        capitalData.setSurplusRep(result.getSurplusRep());
                        capitalData.setTotalJackpot(result.getTotalJackpot());
                        CacheManager.CapitalData.set(capitalData);
                        updateView(capitalData);
                    }
                }
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


        }), (RxAppCompatActivity) getActivity(), token));
    }

    private void updateView(CapitalData capitalData) {
        binding.tvQqjc.setText(capitalData.getTotalJackpot() + "");
        binding.tvCjjd.setText(capitalData.getMax_sinvestment() + "");
        binding.tvGxz.setText(capitalData.getContri() + "");
        binding.tvXye.setText(capitalData.getSurplusRep() + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ll_go_jackpot:
                startActivity(new Intent(getActivity(), DevoteJcDetailActivity.class));
                break;

            case R.id.rl_go_cjjd:
                startActivity(new Intent(getActivity(), DevoteCjjdActivity.class));
                break;

            case R.id.ll_cygx_right:
                startActivity(new Intent(getActivity(), DevoteCygxActivity.class));
                break;

            case R.id.rl_gx_detail:
            case R.id.rl_gx_detail1:
                startActivity(new Intent(getActivity(), DevoteGxDetailActivity.class));
                break;

            case R.id.rl_fx_detail:
                startActivity(new Intent(getActivity(), DevoteFsDetailActivity.class));
                break;

            case R.id.ll_go_xy:
                startActivity(new Intent(getActivity(), DevoteXyDetailActivity.class));
                break;

        }
    }
}
