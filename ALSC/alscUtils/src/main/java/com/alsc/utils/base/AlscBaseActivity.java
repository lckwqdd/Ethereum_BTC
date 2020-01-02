package com.alsc.utils.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.utils.R;
import com.mirko.androidutil.view.CustomeDialog;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

/**
 * Created by Mirko on 2016/11/30.
 *
 */

public abstract class AlscBaseActivity extends RxAppCompatActivity {

    public static ActivityTack tack = ActivityTack.getInstanse();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tack.addActivity(this);
        initViews(savedInstanceState);
        initAttrs();
        loadData();
        setStatusBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
//        setStatusBar();
    }


    /**
     * 设置状态栏透明
     */
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.color_theme_bg));
    }

    //定义三个抽象方法，用来初始化相关定义，加载数据
    public abstract void initViews(Bundle savedInstanceState);
    public abstract void initAttrs();
    public abstract void loadData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tack.removeActivity(this);
    }

    /**
     * 获取最后可获取焦点的Activity
     */
    public static Activity getTopActivity() {
        return tack.getActivityList().size() == 0 ? null : tack.getActivityList().get(tack.getActivityList().size()-1);
    }

    /**
     * 获取最前Activity
     */
    public static Activity getBottomActivity() {
        return tack.getActivityList().size() == 0 ? null : tack.getActivityList().get(0);
    }

    /**
     * 判断一个Activity 是否存在
     *
     */
    public static boolean isActivityExist(Activity activity) {
        boolean res;
        if (activity == null) {
            res = false;
        } else {
            if (activity.isFinishing() || activity.isDestroyed()) {
                res = false;
            } else {
                res = true;
            }
        }

        return res;
    }


    public void showCoustomeDialog(int resId){

        final CustomeDialog myDialog = new CustomeDialog(AlscBaseActivity.this);
        myDialog.setContent(getString(resId));
        myDialog.showOnlyOneBtn(getString(R.string.sure), new CustomeDialog.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        myDialog.setLeftBtnTextColor(getResources().getColor(R.color.yellow));
        myDialog.show();
    }

    protected void goTo(Class<?> mClass) {
        startActivity(new Intent(this, mClass));
    }

}
