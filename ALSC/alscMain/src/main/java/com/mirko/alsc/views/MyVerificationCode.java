package com.mirko.alsc.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.mirko.alsc.R;


/**
 * 获取验证码，倒数按钮
 */
public class MyVerificationCode extends Button {
    /**
     * 倒数秒数
     */
    private int countDownSecond = 120;
    /**
     * 回调监听
     */
    private HandlerListener listener;
    /**
     * 按钮字符串
     */
    private String buttonString = "";
    /**
     * 倒数字符串
     */
    private String condDownString = "";
    /**
     * 当前倒数计数器
     */
    private int countDown = 0;
    private boolean isCoundDowning = false;

    public MyVerificationCode(final Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MyVerificationCode);
        if (a.hasValue(R.styleable.MyVerificationCode_buttonText)) {
            buttonString = a.getString(R.styleable.MyVerificationCode_buttonText);
            setText(buttonString);
        }
        if (a.hasValue(R.styleable.MyVerificationCode_countDownText)) {
            condDownString = a.getString(R.styleable.MyVerificationCode_countDownText);
        }
        if (a.hasValue(R.styleable.MyVerificationCode_countDownSecond)) {
            countDownSecond = a.getInteger(R.styleable.MyVerificationCode_countDownSecond, 120);
        }
        a.recycle();
    }

    /**
     * 开始倒计时，按钮不可点击
     */
    public void startCountDown() {
        setEnabled(false);
        isCoundDowning  =true;
        countDown = countDownSecond;
        setText(String.format(buttonString, countDown));
        postDelayed(runnable, 1000);
    }
    public boolean isCoundDowning(){
        return isCoundDowning;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            countDown--;
            setText(String.format(condDownString, countDown));
            if (countDown == 0) {
                isCoundDowning = false;
                if (listener != null) {
                    listener.onFinish();
                }
                removeCallbacks(runnable);
                setText(buttonString);
                setEnabled(true);
            } else {
                postDelayed(runnable, 1000);
            }
        }
    };

    interface HandlerListener {
        /**
         * 倒数结束
         */
        void onFinish();
    }

    public void setCondDownString(String condDownString) {
        this.condDownString = condDownString;
    }

    public void setButtonString(String buttonString) {
        this.buttonString = buttonString;
        setText(buttonString);
    }

    public void setListener(HandlerListener listener) {
        this.listener = listener;
    }

    public void setCountDownSecond(int countDownSecond) {
        this.countDownSecond = countDownSecond;
    }
}
