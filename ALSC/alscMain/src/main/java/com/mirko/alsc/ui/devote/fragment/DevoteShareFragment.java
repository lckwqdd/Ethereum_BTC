package com.mirko.alsc.ui.devote.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alsc.utils.base.BaseFragment;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.FragmentDevoteShanDuiBinding;
import com.mirko.alsc.databinding.FragmentDevoteShareBinding;
import com.mirko.androidutil.utils.android.LogUtils;

/**
 * Created by Mirko on 2019/12/21.
 * 分享
 */

public class DevoteShareFragment extends BaseFragment {

    private final static String TAG = "DevoteShareFragment";

    FragmentDevoteShareBinding binding;
    private View mView;


    public static DevoteShareFragment getInstance() {
        DevoteShareFragment sf = new DevoteShareFragment();
        return sf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_devote_share, null);
        binding = FragmentDevoteShareBinding.bind(mView);

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

    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {

    }


}
