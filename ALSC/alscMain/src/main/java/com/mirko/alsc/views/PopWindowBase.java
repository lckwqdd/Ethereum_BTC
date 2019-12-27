package com.mirko.alsc.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 2016-4-5.
 */
public abstract class PopWindowBase extends PopupWindow{

    public  View    conentView;
    public  Context mContext;
    public  WindowManager.LayoutParams params;

    public PopWindowBase(int id, final Activity context){

        mContext = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(id,null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        conentView.measure(0, 0);
        this.setContentView(conentView);
        this.setWidth(conentView.getMeasuredWidth());
        this.setHeight(conentView.getMeasuredHeight());
//        this.setAnimationStyle(R.style.popWindow_animation);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
//        setAnimationStyle(R.style.popWindow_animation);
        params=context.getWindow().getAttributes();
        params.alpha=0.7f;
        context.getWindow().setAttributes(params);
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        this.setFocusable(true);
        this.setOutsideTouchable(false);
//        this.update();
//        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
//        this.setBackgroundDrawable(new BitmapDrawable());
        // 监听菜单的关闭事件
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                context.getWindow().setAttributes(params);
            }
        });
    }

    public abstract void showPopWindow(View view);

    public int getPopWidth(){
        if (conentView != null){
            return this.getWidth();
        }
        return 0;
    }
}
