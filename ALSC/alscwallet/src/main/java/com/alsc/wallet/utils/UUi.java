package com.alsc.wallet.utils;

import android.app.Activity;
import android.util.SparseArray;
import android.view.View;
import com.mirko.androidutil.utils.android.Utils;

/**
 * UI类工具
 * Created by shidawei on 16/8/4.
 */
public class UUi {

    /**
     * 获取view
     * @param activity
     * @param mViews
     * @param id
     * @param <T>
     * @return
     */
    @Deprecated
    public static  <T extends View> T getView(Activity activity, SparseArray<View> mViews, int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) activity.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }


    public static  <T extends View> T getView(View mView, SparseArray<View> mViews, int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) mView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }


    /**
     * 给多个view添加点击事件
     * @param listener
     * @param views
     */
    @Deprecated
    public static void setOnClickListener(View.OnClickListener listener, View... views) {
        if (views == null) {
            return;
        }
        for (View view : views) {
            view.setOnClickListener(listener);
        }

    }

    /**
     * dip 转 px
     * @param dipValue
     * @return
     */
    public static int dip2px(float dipValue) {
        float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5F);
    }

    /**
     * px 转 dp
     * @param pxValue
     * @return
     */
    public static int px2dip(float pxValue) {
        float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int)(pxValue / scale + 0.5F);
    }

    /**
     * px转sp
     * @param pxValue
     * @return
     */
    public static int px2sp(float pxValue) {
        float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int)(pxValue / fontScale + 0.5F);
    }

    /**
     * sp转px
     * @param spValue
     * @return
     */
    public static int sp2px(float spValue) {
        float fontScale = Utils.getApp().getResources().getDisplayMetrics().scaledDensity;
        return (int)(spValue * fontScale + 0.5F);
    }

}
