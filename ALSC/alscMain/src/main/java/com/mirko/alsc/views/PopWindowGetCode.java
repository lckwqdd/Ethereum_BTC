package com.mirko.alsc.views;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.mirko.alsc.R;


/**
 * 获取验证码
 *
 */
public class PopWindowGetCode extends PopWindowBase{

    private static final String TAG = "PopWindowGetCode";

    public PopWindowGetCode(int id, Activity context){
        super(id,context);
//        initView();
    }

    private void initView(){
    }

    public void showPopWindow(View view){
        if(!this.isShowing()){
            int width = getPopWidth()/2;
            int marginRight = (int)mContext.getResources().getDimension(R.dimen.DIMEN_24PX);
            this.showAsDropDown(view ,-(width + marginRight),0);
        }else{
            this.dismiss();
        }
    }
}
