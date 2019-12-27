package com.mirko.alsc.views.images;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Mirko
 * 2016-3-28.
 */
public class MyImageView extends ImageView{

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
            Log.e("test","AsyncImageView  -> onDraw() Canvas: trying to use a recycled bitmap");
        }
    }
}
