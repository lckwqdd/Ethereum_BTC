package com.mirko.androidutil.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mirko.androidutil.R;
import com.mirko.androidutil.utils.android.LogUtils;


/**
 * Created by zeng on 2016/8/20.
 */
public class CustomeDialog {

    protected Context context;
    protected Dialog dialog;
    protected Window window;
    private View view;
    private LinearLayout buttonLayout;//底部button layout
    private FrameLayout flLine;//底部button layout
    private TextView tvContent;//中间文字TextView
    private TextView tvDes;//中间文字TextView
    private TextView tvLeft;//左边按钮TextView
    private TextView tvRight;//右边按钮TextView
    private View bottomDevider;//中间视图与底部视图横向分割线
    private View verticalDivider;//底部两个按钮中间的纵向分割线
    private OnClickListener leftListener;//左边按钮监听事件
    private OnClickListener rightListener;//右边按钮监听事件
    private boolean isAutoDismiss = true;//点击按钮后是否自动隐藏弹框

    public interface OnClickListener {
        void onClick(View v);
    }

    public CustomeDialog(Context context) {
        this.context = context;
        init(-1);
    }

    public CustomeDialog(Context context, View view) {
        this.context = context;
        this.view = view;
        init(-1);
    }

    public CustomeDialog(Context context, int resId) {
        this.context = context;
        this.view = View.inflate(context, resId, null);
        init(-1);
    }

    public CustomeDialog(Context context, View view, int theme) {
        this.context = context;
        this.view = view;
        init(theme);
    }

    private void init(int theme) {
        if (theme == -1) {
            dialog = new Dialog(context);
        } else {
            dialog = new Dialog(context, theme);
        }
        window = dialog.getWindow();
        requestFeature(window);
        addFlags(window);
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setDialogSize(0.9f, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(getDefaultView());
    }

    public void requestFeature(Window window) {
        window.requestFeature(Window.FEATURE_NO_TITLE);
    }


    public void addFlags(Window window) {
    }

    private View getDefaultView() {
        if (view == null) {
            view = View.inflate(context, R.layout.dialog_default, null);
            tvContent = (TextView) view.findViewById(R.id.tvContent);
            tvDes = (TextView) view.findViewById(R.id.tvDes);
            buttonLayout = (LinearLayout) view.findViewById(R.id.button_layout);
            tvLeft = (TextView) view.findViewById(R.id.tvLeft);
            tvRight = (TextView) view.findViewById(R.id.tvRight);
            flLine = (FrameLayout) view.findViewById(R.id.fl_line);
            bottomDevider = view.findViewById(R.id.bottom_divider_line);
            verticalDivider = view.findViewById(R.id.vertical_divider_line);
            tvLeft.setOnClickListener(clickListener);
            tvRight.setOnClickListener(clickListener);
        }
        return view;
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.tvLeft) {
                if (isAutoDismiss)
                    dismiss();
                if (leftListener != null) {
                    leftListener.onClick(v);
                }

            } else if (i == R.id.tvRight) {
                if (isAutoDismiss)
                    dismiss();
                if (rightListener != null) {
                    rightListener.onClick(v);
                }

            }
        }
    };

    public View getContentView() {
        return view;
    }

    /**
     * 设置点击框外面是否隐藏弹框
     * @param flag
     */
    public void setCanceledOnTouchOutside(boolean flag) {
        dialog.setCanceledOnTouchOutside(flag);
    }

    public void setCancelable(boolean cancelable) {
        dialog.setCancelable(cancelable);
    }

    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener){
        dialog.setOnKeyListener(onKeyListener);
    }

    public void setLeftBtnClickListener(OnClickListener litener) {
        leftListener = litener;
    }
    public void setLeftBtn(String text,OnClickListener litener){
        setLeftBtnText(text);
        setLeftBtnClickListener(litener);
    }

    public void setRightBtnClickListener(OnClickListener litener) {
        rightListener = litener;
    }
    public void setRightBtn(String text,OnClickListener litener){
        setRightBtnText(text);
        setRightBtnClickListener(litener);
    }
    public void setContent(String text) {
        if (tvContent != null) tvContent.setText(text);
    }

    public void setContent(int text) {
        if (tvContent != null) tvContent.setText(context.getResources().getText(text));
    }

    public void setContentColor(int color) {
        if (tvContent != null) tvContent.setTextColor(color);
    }

    /**
     * 只显示一个按钮
     * @param btnText 按钮名称
     * @param listener 按钮点击事件
     */
    public void showOnlyOneBtn(String btnText, OnClickListener listener) {
        leftListener = listener;
        tvLeft.setText(btnText);
        tvLeft.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.GONE);
        buttonLayout.setVisibility(View.VISIBLE);
        bottomDevider.setVisibility(View.VISIBLE);
        flLine.setVisibility(View.GONE);
//        verticalDivider.setVisibility(View.GONE);
    }

    /**
     * 显示两个按钮
     * 只有调用showOnlyOneBtn或hideTowBtn 之后才需要带用该方法
     */
    public void showTowBtn() {
        buttonLayout.setVisibility(View.VISIBLE);
        tvLeft.setVisibility(View.VISIBLE);
        tvRight.setVisibility(View.VISIBLE);
        bottomDevider.setVisibility(View.VISIBLE);
        verticalDivider.setVisibility(View.VISIBLE);
    }

    /**
     * 显示两个按钮
     */
    public void hideTowBtn() {
        buttonLayout.setVisibility(View.GONE);
        tvLeft.setVisibility(View.GONE);
        tvRight.setVisibility(View.GONE);
        bottomDevider.setVisibility(View.GONE);
        verticalDivider.setVisibility(View.GONE);
    }

    public void setAutoDismiss(boolean dismiss) {
        isAutoDismiss = dismiss;
    }
    public void setContentTypeface(Typeface tf) {
        if (tvContent != null) tvContent.setTypeface(tf);
    }

    public void setContentSize(int unit, float size) {
        if (tvContent != null) tvContent.setTextSize(unit, size);
    }

    public void setLeftBtnText(int text) {
        if (tvLeft != null) tvLeft.setText(context.getResources().getText(text));
    }
    public void setTvDes(int text) {
        if (tvDes != null) {
            tvDes.setVisibility(View.VISIBLE);
            tvDes.setText(context.getResources().getText(text));
        }
    }

    public void setLeftBtnText(String text) {
        if (tvLeft != null) tvLeft.setText(text);
    }

    public void setLeftBtnTextColor(int color) {
        if (tvLeft != null) tvLeft.setTextColor(color);
    }

    public void setLeftBtnTextColor(int normal, int pressed) {
        if (tvLeft != null) tvLeft.setTextColor(getColorStateList(normal, pressed));
    }

    public void setLeftBtnTextSize(int unit, float size) {
        if (tvLeft != null) tvLeft.setTextSize(unit, size);
    }

    public void setRightBtnText(String text) {
        if (tvRight != null) tvRight.setText(text);
    }

    public void setRightBtnText(int text) {
        if (tvRight != null) tvRight.setText(context.getResources().getText(text));
    }

    public void setRightBtnTextColor(int color) {
        if (tvRight != null) tvRight.setTextColor(color);
    }

    public void setRightBtnTextColor(int normal, int pressed) {
        if (tvRight != null) tvRight.setTextColor(getColorStateList(normal, pressed));
    }

    public void setRightBtnTextSize(int unit, float size) {
        if (tvRight != null) tvRight.setTextSize(unit, size);
    }

    private ColorStateList getColorStateList(int normal, int pressed) {
        int[][] states = new int[][]{{android.R.attr.state_pressed}, {}};
        return new ColorStateList(states, new int[]{pressed, normal});
    }

    public void show() {
        if(context != null){
            try{
                dialog.show();
            }catch (Exception e){
                LogUtils.d("test","**+++ 崩溃了："+e.toString());
            }
        }
    }

    public boolean isShowing() {
        return dialog.isShowing();
    }

    public void setGravity(int g) {
        dialog.getWindow().setGravity(g);
    }

    public void setAnimation(int rid) {
        dialog.getWindow().setWindowAnimations(rid);
    }

    public void setPadding(int left, int top, int right, int bottom) {
        dialog.getWindow().getDecorView().setPadding(left, top, right, bottom);
    }

    /**
     * 设置偏移
     */
    public void setOffset(int x, int y) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.x = x;
        lp.y = y;
        dialog.getWindow().setAttributes(lp);
    }

    /**
     * 设置对话框宽占屏幕的比例
     *
     * @param wPercent 占屏幕宽的百分比。取值0f~1f、LayoutParams.WRAP_CONTENT、LayoutParams.MATCH_PARENT
     * @param hPercent 占屏幕高的百分比。取值0f~1f、LayoutParams.WRAP_CONTENT、LayoutParams.MATCH_PARENT
     */
    public void setDialogSize(float wPercent, float hPercent) {
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;
        if (wPercent == ViewGroup.LayoutParams.MATCH_PARENT || wPercent == ViewGroup.LayoutParams.WRAP_CONTENT) {
            screenWidth = 1;
        }
        if (hPercent == ViewGroup.LayoutParams.MATCH_PARENT || hPercent == ViewGroup.LayoutParams.WRAP_CONTENT) {
            screenHeight = 1;
        }
        lp.width = (int) (wPercent * screenWidth);
        lp.height = (int) (hPercent * screenHeight);
        dialog.getWindow().setAttributes(lp);
    }

    public void dismiss() {
        if(context !=null){
            try{
                dialog.dismiss();
            }catch (Exception e){
                LogUtils.d("test","**+++ 崩溃了："+e.toString());
            }
        }
    }

    public void setOnDismissListener(DialogInterface.OnDismissListener listener) {
        dialog.setOnDismissListener(listener);
    }

    public Window getWindow() {
        return window;
    }
}
