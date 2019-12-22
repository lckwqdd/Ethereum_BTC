package com.alsc.net.util;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.widget.ProgressBar;

import com.alsc.net.R;
import com.mirko.androidutil.utils.android.DisplayUtil;


/**
 * Created by Mirko on 2017/10/20.
 *
 */

public class CommUtils {


    public static void setProgress(Context context , ProgressBar progress){

        if (android.os.Build.VERSION.SDK_INT > 22) {
            final Drawable drawable =  context.getApplicationContext().getResources().getDrawable(R.drawable.progress_bar_1);
            int width = DisplayUtil.getInstance(context).dip2px(36);
            Rect bounds = new Rect(0,0,width,width);
            progress.setIndeterminateDrawable(drawable);
            progress.getIndeterminateDrawable().setBounds(bounds);
        }
    }
}
