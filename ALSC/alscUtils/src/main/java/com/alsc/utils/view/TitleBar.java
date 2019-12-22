package com.alsc.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alsc.utils.R;


/**
 * 标题栏
 */
public class TitleBar extends FrameLayout {

    ImageView ivTitleLeft;
    ImageView ivTitleRight;
    TextView tvTitle;
    TextView tvTitleRight;
    View viewLine;
    boolean isShowRightDrawable = false;
    boolean isShowLeftDrawable = false;
    private String titleRight;
    private String title;
    private boolean isShowTextRight = false;
    private boolean isShowTextLeft;
    private TextView tvTitleLeft;
    private Drawable rightDrawable;
    private Drawable leftDrawable;

    public TitleBar(Context context) {
        this(context, null);
    }

    public TitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        leftDrawable = typedArray.getDrawable(R.styleable.TitleBar_tbLeftDrawable);
        rightDrawable = typedArray.getDrawable(R.styleable.TitleBar_tbRightDrawable);
        isShowRightDrawable = typedArray.getBoolean(R.styleable.TitleBar_showRightDrawable,false);
        isShowLeftDrawable = typedArray.getBoolean(R.styleable.TitleBar_showLeftDrawable,false);
        isShowTextRight = typedArray.getBoolean(R.styleable.TitleBar_showTextRight,false);
        isShowTextLeft = typedArray.getBoolean(R.styleable.TitleBar_showTextLeft,false);

        String title = typedArray.getString(R.styleable.TitleBar_tbTitle);
        String titleRight = typedArray.getString(R.styleable.TitleBar_tbTitleRight);
        String titleLeft = typedArray.getString(R.styleable.TitleBar_tbTitleLeft);
        int titleColor = typedArray.getColor(R.styleable.TitleBar_tbTitleColor, getResources().getColor(R.color.color_text_white));

        View view = LayoutInflater.from(context).inflate(R.layout.view_title_bar,null);
        ivTitleLeft = view.findViewById(R.id.iv_title_left);
        ivTitleRight = view.findViewById(R.id.iv_title_right);
        tvTitle = view.findViewById(R.id.tvTitle);
        tvTitleRight = view.findViewById(R.id.tv_title_right);
        viewLine = view.findViewById(R.id.view_line);
        tvTitleLeft = view.findViewById(R.id.tv_title_left);
        tvTitleLeft.setText(titleLeft);
        this.addView(view);
        if(isShowTextLeft){
            tvTitleLeft.setVisibility(VISIBLE);
        }else{
            tvTitleLeft.setVisibility(GONE);
        }
        setShowLeftDrawable(isShowLeftDrawable);
        setShowRightDrawable(isShowRightDrawable);
        setShowTextRight(isShowTextRight);
        tvTitleRight.setText(titleRight);

        tvTitle.setText(title);
        tvTitle.setTextColor(titleColor);
        typedArray.recycle();
    }

    public void setShowRightDrawable(boolean showRightDrawable) {
        isShowRightDrawable = showRightDrawable;
        if (isShowRightDrawable && rightDrawable != null) {
            ivTitleRight.setVisibility(VISIBLE);
            ivTitleRight.setImageDrawable(rightDrawable);
        }else {
            ivTitleRight.setVisibility(GONE);
        }
    }

    public void setShowLeftDrawable(boolean showLeftDrawable) {
        isShowLeftDrawable = showLeftDrawable;
        if (isShowLeftDrawable) {
            ivTitleLeft.setVisibility(VISIBLE);
            if (leftDrawable != null){
                ivTitleLeft.setImageDrawable(leftDrawable);
            }
        }else {
            ivTitleLeft.setVisibility(GONE);
        }
    }

    public void setTitleRightEnable(boolean isEnable) {
        tvTitleRight.setEnabled(isEnable);
    }

    public void setTitleLeftEnable(boolean isEnable) {
        tvTitleLeft.setEnabled(isEnable);
    }

    public void setOnLeftClickListener(OnClickListener listener) {
        if (listener != null) {
            ivTitleLeft.setOnClickListener(listener);
        }
    }
    public void setOnRightClickListener(OnClickListener listener) {
        if (listener != null) {
            ivTitleRight.setOnClickListener(listener);
        }
    }

    public TextView getTvTitleRight() {
        return tvTitleRight;
    }

    public void setShowTextRight(boolean showtextright) {
        isShowTextRight = showtextright;
        if(isShowTextRight){
            tvTitleRight.setVisibility(VISIBLE);
        }else{
            tvTitleRight.setVisibility(GONE);
        }
    }

    public void setOnTextRightClickListener(OnClickListener listener) {
        if (listener != null) {
            tvTitleRight.setOnClickListener(listener);
        }
    }

    public void setOnTextLeftClickListener(OnClickListener listener) {
        if (listener != null) {
            tvTitleLeft.setOnClickListener(listener);
        }
    }

    public void setTitle(String text) {
        tvTitle.setText(text);
    }

    public void setTitle(int resId) {
        tvTitle.setText(resId);
    }

    public void setTitleTextColor(int color) {
        tvTitle.setTextColor(color);
    }

    public void setViewLineGone() {
        if(viewLine != null){
            viewLine.setVisibility(GONE);
        }
    }

    public void setViewLineVisible() {
        viewLine.setVisibility(VISIBLE);
    }

    public void setBackSrc(int resid) {
        ivTitleLeft.setImageResource(resid);
    }

    /**
     * 加一个设置右边文字的方法
     */
    public void setRightText(String rightText) {
        tvTitleRight.setText(rightText);
    }

    public String getRightText(){
        return tvTitleRight.getText().toString();
    }

    public void setRightTextColor(int color){
        tvTitleRight.setTextColor(color);
    }
    public void setRightTextIsEnabled(boolean isEnabled){
        tvTitleRight.setEnabled(isEnabled);
    }

    public void setLeftText(String leftText) {
        tvTitleLeft.setText(leftText);
    }
    public String getLeftText(){
        return tvTitleLeft.getText().toString();
    }

}
