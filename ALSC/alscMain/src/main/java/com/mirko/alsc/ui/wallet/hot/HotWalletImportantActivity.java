package com.mirko.alsc.ui.wallet.hot;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.alsc.utils.base.AlscBaseActivity;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.HomePagerAdapter;
import com.mirko.alsc.databinding.ActivityHotWalletImportBinding;
import com.mirko.alsc.ui.entity.TabEntity;
import com.mirko.alsc.ui.fragment.HomeFragment;
import com.mirko.alsc.views.ViewPagerScroller;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuQuan on 2019/12/22.
 * 热钱包:导入钱包
 */
public class HotWalletImportantActivity extends AlscBaseActivity implements View.OnClickListener {

    /**
     * 首页底部标题和图标
     */
    private String mTitles[] = new String[4];
    private String mTitleSel[] = new String[4];

    private int[] mTitleIds = {
            R.string.wh_all_important_money,
            R.string.wh_single_important_money
    };
    private int[] mTitleSlectIds = {
            R.string.wh_all_important_money,
            R.string.wh_single_important_money
    };
    private int[] mIconUnselectIds = {
            R.mipmap.home_study,
            R.mipmap.home_think
    };
    private int[] mIconSelectIds = {
            R.mipmap.home_study_sel,
            R.mipmap.home_think_sel
    };
    ActivityHotWalletImportBinding binding;
    private List<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private HomePagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_hot_wallet_import);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(HotWalletImportantActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        /**添加Fragment*/
        mFragments.add(HomeFragment.getInstance());
        mFragments.add(HomeFragment.getInstance());

        /**设置底部状态栏的相关数据*/
        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitleSlectIds.length; i++) {
            mTitles[i] = getString(mTitleIds[i]);
            mTitleSel[i] = getString(mTitleSlectIds[i]);
            mTabEntities.add(new TabEntity(mTitleSel[i], mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        binding.hotWalletImportTab.setTabData(mTabEntities);
        binding.hotWalletImportTab.setOnTabSelectListener(new OnTabSelectListener() {  //底部导航条点击切换监听
            @Override
            public void onTabSelect(int position) {
                binding.hotWalletImportVp.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(0);
        scroller.initViewPagerScroll(binding.hotWalletImportVp);
        adapter = new HomePagerAdapter(getSupportFragmentManager(), mFragments, mTitles);
        binding.hotWalletImportVp.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}
