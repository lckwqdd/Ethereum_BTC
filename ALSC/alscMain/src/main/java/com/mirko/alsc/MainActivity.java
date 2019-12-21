package com.mirko.alsc;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.FrameLayout;

import com.alsc.find.base.AlscBaseActivity;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mirko.alsc.adapter.HomePagerAdapter;
import com.mirko.alsc.ui.entity.TabEntity;
import com.mirko.alsc.ui.fragment.HomeFragment;
import com.mirko.alsc.views.NoScrollViewPager;
import com.mirko.alsc.views.ViewPagerScroller;
import com.mirko.alsc.R;
import com.mirko.androidutil.utils.android.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AlscBaseActivity {

    /**相关控件的定义*/

    private NoScrollViewPager viewPagerHome;
    private CommonTabLayout tlTitle;
    private FrameLayout flFloating;

    /**相关对象*/
    private Context mContext;
    private HomePagerAdapter adapter;

    private LocationManager locationManager;//位置服务
    private Location location;
    private String provider;//位置提供器
    private int loginGo;//1表示从登录跳转

    private List<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private Handler mHandler = new Handler();
    private Handler handler;

    /**首页底部标题和图标*/
    private String mTitles[] = new String[4];
    private String mTitleSel[] = new String[4];

    private int[] mTitleIds = {
            R.string.home_bar_study_sel,
            R.string.home_bar_think_sel,
            R.string.home_bar_find_sel,
            R.string.home_bar_my_sel
    };
    private int[] mTitleSlectIds = {
            R.string.home_bar_study_sel,
            R.string.home_bar_think_sel,
            R.string.home_bar_find_sel,
            R.string.home_bar_my_sel
    };
    private int[] mIconUnselectIds = {
            R.mipmap.home_study,
            R.mipmap.home_think,
            R.mipmap.home_find,
            R.mipmap.home_my_account,
    };
    private int[] mIconSelectIds = {
            R.mipmap.home_study_sel,
            R.mipmap.home_think_sel,
            R.mipmap.home_find_sel,
            R.mipmap.home_my_account_sel,
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.act_home);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mContext = MainActivity.this;
        tlTitle = (CommonTabLayout) findViewById(R.id.tl_title);
        viewPagerHome = (NoScrollViewPager)findViewById(R.id.view_pager_home);
        flFloating = (FrameLayout) findViewById(R.id.fl_floating);
    }

    @Override
    public void initAttrs() {

    }

    @Override
    public void loadData() {

        /**添加Fragment*/
        mFragments.add(HomeFragment.getInstance());
        mFragments.add(HomeFragment.getInstance());
        mFragments.add(HomeFragment.getInstance());
        mFragments.add(HomeFragment.getInstance());

        /**设置底部状态栏的相关数据*/
        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitleIds.length; i++) {
            mTitles[i] = mContext.getString(mTitleIds[i]);
            mTitleSel[i] = mContext.getString(mTitleSlectIds[i]);
            mTabEntities.add(new TabEntity(mTitleSel[i], mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tlTitle.setTabData(mTabEntities);
        tlTitle.setTextSelectColor(getResources().getColor(R.color.dedication_highlight_color));
        tlTitle.setTextUnselectColor(getResources().getColor(R.color.home_tab_unselect));
        tlTitle.setOnTabSelectListener(new OnTabSelectListener() {  //底部导航条点击切换监听
            @Override
            public void onTabSelect(int position) {
                viewPagerHome.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        ViewPagerScroller scroller = new ViewPagerScroller(this);
        scroller.setScrollDuration(0);
        scroller.initViewPagerScroll(viewPagerHome);
        adapter = new HomePagerAdapter(getSupportFragmentManager(),mFragments,mTitles);
        viewPagerHome.setAdapter(adapter);
        viewPagerHome.setNoScroll(true); //设置是否滑动
    }
}
