package com.alsc.utils.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alsc.utils.R;


/**
 * Created by Mirko on 2017/1/16.
 *
 */

public class ItemGroup extends FrameLayout {

    private RelativeLayout rlItemGroup;
    private View mView;
    private TextView tvTitle;
    private TextView tvRightText;
    private ImageView ivRight; //箭头符号
    private ImageView ivRightDes; //描述图片
    private View      line;

    private String   strTitle;
    private Drawable drawableright;
    private Drawable drawablerightDes;
    private Drawable drawableLeft;
    private int      lineHeight;
    private float    height;
    private int      imageWidth;
    private int      lineColor;
    private boolean  showRightText;
    private boolean  showRightImage;

    private Context mContext;

    public ItemGroup(Context context) {
        super(context);
    }

    public ItemGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public ItemGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        initView(context);
        initAttrs(context,attrs);
        setData();
    }

    /**
     * 初始化，引入相关属性
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context,AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemGroup);
        strTitle = typedArray.getString(R.styleable.ItemGroup_group_title);
        drawableLeft  = typedArray.getDrawable(R.styleable.ItemGroup_drawable_left);
        imageWidth    = (int)typedArray.getDimension(R.styleable.ItemGroup_left_image_width,0);
        drawableright = typedArray.getDrawable(R.styleable.ItemGroup_drawable_right);
        drawablerightDes = typedArray.getDrawable(R.styleable.ItemGroup_drawable_right_des);
        lineHeight    = typedArray.getInt(R.styleable.ItemGroup_line_height,1);
        height        =     typedArray.getDimension(R.styleable.ItemGroup_rl_height,0);
        lineColor     = typedArray.getColor(R.styleable.ItemGroup_line_color,0xffffffff);
        showRightText  = typedArray.getBoolean(R.styleable.ItemGroup_show_right_text,false);
        showRightImage  = typedArray.getBoolean(R.styleable.ItemGroup_show_right_image,true);
        typedArray.recycle();
    }

    /**
     * 控件初始化
     */
    private void initView(Context context){
        mContext = context;
        mView       = LayoutInflater.from(context).inflate(R.layout.view_item_group,null);
        rlItemGroup = (RelativeLayout)mView.findViewById(R.id.rl_item_group);
        tvTitle     = (TextView) mView.findViewById(R.id.tv_title);
        tvRightText = (TextView) mView.findViewById(R.id.tv_content);
        ivRight     = (ImageView)mView.findViewById(R.id.iv_right);
        ivRightDes  = (ImageView)mView.findViewById(R.id.iv_right_des);
        line        = mView.findViewById(R.id.line);
        this.addView(mView);

    }

    private void setData(){
        tvTitle.setText(strTitle);

        if(drawableLeft != null){
            if(imageWidth == 0){
                imageWidth = (int)mContext.getResources().getDimension(R.dimen.px48);
            }
            drawableLeft.setBounds(0, 0, imageWidth, imageWidth);
            tvTitle.setCompoundDrawables(drawableLeft, null, null, null);
        }
        if(drawableright != null){
            ivRight.setImageDrawable(drawableright);
        }
        if(drawablerightDes != null){
            ivRightDes.setImageDrawable(drawablerightDes);
        }

        if(showRightImage){
            ivRight.setVisibility(VISIBLE);
        }else{
            ivRight.setVisibility(GONE);
        }

        if(showRightText){
            tvRightText.setVisibility(VISIBLE);
        }else{
            tvRightText.setVisibility(GONE);
        }
        line.setBackgroundColor(lineColor);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,lineHeight);
        params.leftMargin = (int)mContext.getResources().getDimension(R.dimen.px36);
        line.setLayoutParams(params);

        rlItemGroup.setBackgroundResource(R.drawable.xml_bg_click);
        LinearLayout.LayoutParams rlParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,(int)height);
        if(height == 0){
            height = (int)mContext.getResources().getDimension(R.dimen.px168);
        }
        int padding = (int)mContext.getResources().getDimension(R.dimen.px48);
//        rlParams.leftMargin = padding;
        rlParams.height = (int)height;
        rlItemGroup.setPadding(padding,0,padding,0);
        rlItemGroup.setLayoutParams(rlParams);
    }

    /**
     * 创建点击事件监听
     */
    public void setItemOnClickListener(OnClickListener listener){
        if(listener != null){
            rlItemGroup.setOnClickListener(listener);
        }
    }

    public void setItemClickEnable(boolean enable){
        rlItemGroup.setClickable(enable);
    }

    public void setTvRightText(String text){
        tvRightText.setText(text);
    }

    public void setTvRightSize(int textSize){
        tvRightText.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);
    }

    public void setTvRightColor(int color){
        tvRightText.setTextColor(color);
    }

    public void setTvTitleColor(int color){
        tvTitle.setTextColor(color);
    }

    public TextView getTvRightText() {
        return tvRightText;
    }

    public void setIvRight(int id){
        RelativeLayout.LayoutParams mParams = new RelativeLayout.LayoutParams((int)mContext.getResources().getDimension(R.dimen.px96),
                (int)mContext.getResources().getDimension(R.dimen.px72));
        mParams.leftMargin = (int)mContext.getResources().getDimension(R.dimen.px48);
        ivRight.setLayoutParams(mParams);
        ivRight.setImageResource(id);
    }

    /**
     * 加上一个修改title文本的方法
     * @param text
     */
    public void setTitleContent(String text){
        tvTitle.setText(text);
    }

    /**
     * 加上一个显示右边图片的方法
     */
    public void showRightImage(){
        ivRight.setVisibility(VISIBLE);
    }

    /**
     * 加上一个隐藏右边图片的方法
     */
    public void hideRightImage(){
        ivRight.setVisibility(INVISIBLE);
    }
    /**
     * 加上一个隐藏右边图片的方法
     */
    public void setRightImageDesVisibility(int visable){
        ivRightDes.setVisibility(visable);
    }


}