package com.mirko.alsc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsc.net.api.HomeMsgApi;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.bean.entity.NoticeResultEntity;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.utils.view.ItemGroup;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.mirko.alsc.adapter.HomePagerAdapter;
import com.mirko.alsc.ui.capital.CapitalFragment;
import com.mirko.alsc.ui.entity.TabEntity;
import com.mirko.alsc.ui.fragment.HomeFragment;
import com.mirko.alsc.ui.slide.AboutActivity;
import com.mirko.alsc.ui.slide.InviteFriendsActivity;
import com.mirko.alsc.ui.slide.LanguageSwithcingActivity;
import com.mirko.alsc.ui.slide.SecurityPayPasswordActivity;
import com.mirko.alsc.ui.slide.SecuritySettingActivity;
import com.mirko.alsc.ui.slide.SwitchingAccountActivity;
import com.mirko.alsc.ui.slide.SystemNoticeActivity;
import com.mirko.alsc.ui.slide.UserInfoSettingActivity;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.alsc.utils.Constant;
import com.mirko.alsc.views.NoScrollViewPager;
import com.mirko.alsc.views.ViewPagerScroller;
import com.mirko.alsc.views.images.AsyncImageView;
import com.mirko.androidutil.utils.android.LogUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AlscBaseActivity {

    /**相关控件的定义*/

    private NoScrollViewPager viewPagerHome;
    private CommonTabLayout tlTitle;
    private FrameLayout flFloating;
    private DrawerLayout drawerLayout;
    private ItemGroup itemChat;
    private ItemGroup itemSecurity;
    private ItemGroup itemLanguage;
    private ItemGroup itemAbout;
    private ItemGroup itemInviteFriends;
    private ItemGroup itemSystemNotice;
    private ItemGroup itemSwitchingAccount;
    private ImageView ivUserInfoSetting;
    private TextView tvName;
    private TextView tvUid;
    private TextView tvLevel;
    private AsyncImageView imagLogo;

    /**相关对象*/
    private Context mContext;
    private HomePagerAdapter adapter;

    private List<Fragment> mFragments = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private UserInfoResult userInfo = new UserInfoResult();

    /**首页底部标题和图标*/
    private String mTitles[] = new String[4];
    private String mTitleSel[] = new String[4];

    private int[] mTitleIds = {
            R.string.home_bar_chat,
            R.string.home_bar_friends,
            R.string.home_bar_find,
            R.string.home_bar_money
    };
    private int[] mTitleSlectIds = {
            R.string.home_bar_chat,
            R.string.home_bar_friends,
            R.string.home_bar_find,
            R.string.home_bar_money
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
        setContentView(R.layout.activity_home);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mContext = MainActivity.this;
        tlTitle = (CommonTabLayout) findViewById(R.id.tl_title);
        viewPagerHome = (NoScrollViewPager)findViewById(R.id.view_pager_home);
        flFloating = (FrameLayout) findViewById(R.id.fl_floating);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ivUserInfoSetting = (ImageView) findViewById(R.id.iv_user_info_setting);
        itemChat = (ItemGroup) findViewById(R.id.menu_item_chat);
        itemSecurity = (ItemGroup) findViewById(R.id.menu_item_security);
        itemLanguage = (ItemGroup) findViewById(R.id.menu_item_language_switching);
        itemAbout = (ItemGroup) findViewById(R.id.menu_item_about);
        itemInviteFriends = (ItemGroup) findViewById(R.id.menu_item_invite_friends);
        itemSystemNotice = (ItemGroup) findViewById(R.id.menu_item_system);
        itemSwitchingAccount = (ItemGroup) findViewById(R.id.menu_item_switching_accounts);
        tvName =  findViewById(R.id.tv_name);
        tvUid =  findViewById(R.id.tv_uid);
        tvLevel =  findViewById(R.id.tv_level);
        imagLogo =  findViewById(R.id.async_image_head);

//        drawerLayout.openDrawer(Gravity.LEFT);
    }

    @Override
    public void initAttrs() {

        //个人信息设置
        ivUserInfoSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserInfoSettingActivity.class));
            }
        });
        //聊天设置
        itemChat.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        //安全设置
        itemSecurity.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SecuritySettingActivity.class);
                intent.putExtra(Constant.EXTRA_KEY_USER_INFO,userInfo);
                startActivity(intent);
            }
        });
        //语言切换
        itemLanguage.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LanguageSwithcingActivity.class));

            }
        });
        //关于ALSC
        itemAbout.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));

            }
        });
        //邀请好友
        itemInviteFriends.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InviteFriendsActivity.class);
                intent.putExtra(Constant.EXTRA_KEY_USER_INFO, userInfo);
                startActivity(intent);

            }
        });
        //系统公告
        itemSystemNotice.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SystemNoticeActivity.class));

            }
        });
        //切换账户
        itemSwitchingAccount.setItemOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SwitchingAccountActivity.class));

            }
        });
    }

    @Override
    public void loadData() {

        loadFragmentData();
        loadHomeData();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /**
     * 加载fragment数据
     */
    private void loadFragmentData(){

        /**添加Fragment*/
        mFragments.add(HomeFragment.getInstance());
        mFragments.add(HomeFragment.getInstance());
        mFragments.add(HomeFragment.getInstance());
        mFragments.add(CapitalFragment.getInstance());

        /**设置底部状态栏的相关数据*/
        mTabEntities = new ArrayList<>();
        for (int i = 0; i < mTitleIds.length; i++) {
            mTitles[i] = mContext.getString(mTitleIds[i]);
            mTitleSel[i] = mContext.getString(mTitleSlectIds[i]);
            mTabEntities.add(new TabEntity(mTitleSel[i], mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        tlTitle.setTabData(mTabEntities);
        tlTitle.setTextSelectColor(getResources().getColor(R.color.home_tab_select));
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


    /**
     * 加载首页数据
     */

    private void loadHomeData() {

        String token = getTokenCache();
        HttpManager.getInstance().doHttpDeal(new HomeMsgApi((new HttpOnNextListener<HomeMsgResultEntity>() {
            @Override
            public void onNext(HomeMsgResultEntity result) {
                if (result != null) {
                    if (result.getUser_info() != null){
                        userInfo = result.getUser_info();
                        CacheManager.UserInfoResult.set(result.getUser_info());
                        updateView(userInfo);
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


        }), MainActivity.this, token));
    }

    /**
     * 更新View
     * @param userInfo
     */
    private void updateView(UserInfoResult userInfo){
        tvName.setText(userInfo.getUname());
        tvUid.setText("ID " + userInfo.getUid());
        tvLevel.setText("级别 " + userInfo.getLev());
        imagLogo.loadUrlHeadRound(userInfo.getAvatar(),getResources().getDimension(R.dimen.DIMEN_36PX));
    }

    /**
     * 获取token缓存
     * @return
     */
    private String getTokenCache(){
        if(CacheManager.LoginToken.get()!=null){
            return CacheManager.LoginToken.get();
        }
        return null;
    }



}
