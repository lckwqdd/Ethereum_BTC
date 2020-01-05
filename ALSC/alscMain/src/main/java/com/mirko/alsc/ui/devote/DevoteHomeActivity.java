package com.mirko.alsc.ui.devote;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.alsc.net.api.DevoteCheckPayPwdApi;
import com.alsc.net.api.DevoteCreatApi;
import com.alsc.net.api.DevoteExchangeApi;
import com.alsc.net.api.DevoteRealPriceApi;
import com.alsc.net.api.DevoteSuperApi;
import com.alsc.net.api.HomeMsgApi;
import com.alsc.net.bean.CapitalData;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.bean.entity.RealPriceResultEntity;
import com.alsc.net.bean.request.DevoteExchangeRequest;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mirko.alsc.R;
import com.mirko.alsc.adapter.HomePagerAdapter;
import com.mirko.alsc.databinding.ActivityDevoteHomeBinding;
import com.mirko.alsc.ui.capital.CapitalFragment;
import com.mirko.alsc.ui.devote.fragment.DevoteHomeFragment;
import com.mirko.alsc.ui.devote.fragment.DevoteShanDuiFragment;
import com.mirko.alsc.ui.devote.fragment.DevoteShareFragment;
import com.mirko.alsc.ui.entity.TabEntity;
import com.mirko.alsc.ui.fragment.HomeFragment;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.alsc.views.ViewPagerScroller;
import com.mirko.androidutil.encryption.MD5Utils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mirko on 2019/12/28.
 * 贡献首页
 */
public class DevoteHomeActivity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "DevoteHomeActivity";
    ActivityDevoteHomeBinding binding;

    /**相关对象*/
    private Context mContext;
    private HomePagerAdapter adapter;

    private List<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private UserInfoResult userInfo = new UserInfoResult();

    /**首页底部标题和图标*/
    private String mTitles[] = new String[3];
    private String mTitleSel[] = new String[3];

//    private int[] mTitleIds = {
//            R.string.home_bar_chat,
//            R.string.home_bar_find,
//            R.string.home_bar_money
//    };
//    private int[] mTitleSlectIds = {
//            R.string.home_bar_chat,
//            R.string.home_bar_find,
//            R.string.home_bar_money
//    };
    private int[] mIconUnselectIds = {
            R.mipmap.home_study,
            R.mipmap.home_find,
            R.mipmap.home_my_account,
    };
    private int[] mIconSelectIds = {
            R.mipmap.home_study_sel,
            R.mipmap.home_find_sel,
            R.mipmap.home_my_account_sel,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_devote_home);
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        updateView();
        binding.setClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(DevoteHomeActivity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        loadFragmentData();
    }

    /**
     * 加载fragment数据
     */
    private void loadFragmentData(){

        /**添加Fragment*/
        mFragments.add(DevoteHomeFragment.getInstance());
        mFragments.add(DevoteShareFragment.getInstance());
        mFragments.add(DevoteShanDuiFragment.getInstance());

        /**设置底部状态栏的相关数据*/
        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mIconSelectIds.length; i++) {
//            mTitles[i] = mContext.getString(mTitleIds[i]);
//            mTitleSel[i] = mContext.getString(mTitleSlectIds[i]);
            mTabEntities.add(new TabEntity(mTitleSel[i], mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        binding.tlTitle.setTabData(mTabEntities);
        binding.tlTitle.setTextSelectColor(getResources().getColor(R.color.home_tab_select));
        binding.tlTitle.setTextUnselectColor(getResources().getColor(R.color.home_tab_unselect));
        binding.tlTitle.setOnTabSelectListener(new OnTabSelectListener() {  //底部导航条点击切换监听
            @Override
            public void onTabSelect(int position) {
                binding.viewPagerHome.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(0);
        scroller.initViewPagerScroll(binding.viewPagerHome);
        adapter = new HomePagerAdapter(getSupportFragmentManager(),mFragments,mTitles);
        binding.viewPagerHome.setAdapter(adapter);
        binding.viewPagerHome.setNoScroll(true); //设置是否滑动

    }

    /**
     * 参与贡献
     */

    private void devoteCreatApi(float usdt) {
        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new DevoteCreatApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                ToastHelper.alert(DevoteHomeActivity.this, "参与贡献成功");
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


        }), DevoteHomeActivity.this, token, usdt));
    }


    private void updateView() {
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();

            case R.id.btn_devote_creat:

            case R.id.btn_exchange:
            case R.id.btn_super:

        }
    }
}
