package com.mirko.androidutil.utils.android;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.lang.reflect.Field;

/** ==================================================================
 * 												    		         *
 * 		用于获取屏幕宽、高;View宽、高;或百分比<br />	     	         	 *
 * 		例子:DisplayUtil.getInstance(this).getWidth()<br />   		 *
 * 		@author lan zhe 2011-6-9<br />   	        	        	 *
 *												                     *
 *===================================================================*/
public class DisplayUtil {

	private static Display display;
	private static Activity act;
	private static DisplayUtil displayUtil;
	
	private DisplayUtil(){}
	
	/**
	 * 获取DisplayUtil实例
	 * @param activity
	 * @return
	 */
	public static DisplayUtil getInstance(Context activity){
		act = scanForActivity(activity);
		display = act.getWindowManager().getDefaultDisplay();
		if(displayUtil == null){
			displayUtil = new DisplayUtil();
		}
		return displayUtil;
	}

	private static Activity scanForActivity(Context cont) {
		if (cont == null)
			return null;
		else if (cont instanceof Activity)
			return (Activity)cont;
		else if (cont instanceof ContextWrapper)
			return scanForActivity(((ContextWrapper)cont).getBaseContext());
		return null;
	}
	
	/**
	 * 获取屏幕宽度
	 * @return
	 */
	public int getWidth(){
		return display.getWidth();
	}
	
	/**
	 * 获取屏幕宽度 单位dip
	 * @return
	 */
	public int getWidthDip() {
		return DisplayUtil.getInstance(act).px2dip(getWidth());
	}
	
	/**
	 * 获取View控件宽度
	 * @param viewId
	 * @return
	 */
	public int getViewWidthByResource(int viewId){
		View view = this.act.findViewById(viewId);
		Integer width = view.getLayoutParams().width;
		return (width != null)?width:0;
	}
	
	/**
	 * 获取屏幕高度
	 * @return
	 */
	public int getHeight(){
		return display.getHeight();
	}
	
	/**
	 * 获取屏幕高度 单位dp
	 * @return
	 */
	public int getHeightDip() {
		return DisplayUtil.getInstance(act).px2dip(getHeight());
	}
	
	/**
	 * 获取View控件高度
	 * @param viewId 控件id
	 * @return
	 */
	public int getViewHeightByResource(int viewId){
		View view = this.act.findViewById(viewId);
		Integer height = view.getLayoutParams().height;
		return (height != null)?height:0;
	}
	
	/**
	 * 获取View控件高度 单位dip
	 * @param viewId
	 * @return
	 */
	public int getViewHeightByResourceDip(int viewId) {
		return DisplayUtil.getInstance(act).px2dip(getViewHeightByResource(viewId));
	}
	
	/**
	 * 获取view控件百分比宽度
	 * @param viewId 控件id
	 * @param percent 控件宽度百分比
	 * @return
	 */
	public int getViewWidthPercent(int viewId,int percent){
		View view = this.act.findViewById(viewId);
		return (int) (view.getLayoutParams().width * percent * 0.01);
	}
	
	/**
	 * 获取view控件百分比宽度
	 * @param viewId
	 * @param percent
	 * @return
	 */
	public int getViewWidthPercentDip(int viewId,int percent) {
		return DisplayUtil.getInstance(act).px2dip(getViewWidthPercent(viewId,percent));
	}
	
	
	/**
	 * 获取view百分比高度
	 * @param viewId 控件id
	 * @param percent 控件高度百分比
	 * @return
	 */
	public int getViewHeightPercent(int viewId,int percent){
		View view = this.act.findViewById(viewId);
		return (int) (view.getLayoutParams().height * percent * 0.01);
	}
	
	/**
	 * 获取view百分比高度 单位dip
	 * @param viewId 控件id
	 * @param percent 控件高度百分比
	 * @return
	 */
	public int getViewHeightPercentDip(int viewId,int percent){
		return DisplayUtil.getInstance(act).px2dip(getViewHeightPercent(viewId,percent));
	}
	
	/**
	 * 获取屏幕百分比宽度
	 * @param percent 百分比
	 * @return
	 */
	public int getWidthPercent(int percent){
		return (int) (display.getWidth() * percent * 0.01);
	}
	
	/**
	 * 获取屏幕百分比高度
	 * @param percent
	 * @return
	 */
	public int getHeightPercent(int percent){
		return (int) (display.getHeight() * percent * 0.01);
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	public int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * context属性从GlobalContext.getGlobalContext()读取
	 */
	public int dip2px(float dpValue) {
		final float scale = act.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * context属性从GlobalContext.getGlobalContext()读取
	 */
	public int dip2px(int dpValue) {
		final float scale = act.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * context属性从GlobalContext.getGlobalContext()读取
	 */
	public int px2dip(float pxValue) {
		final float scale = act.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	/**
	 * px单位转换dp
	 * @param pxValue
	 * @return
	 */
	public int px2dip(int pxValue) {
		final float scale = act.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	
	public float getDensity() {
		return act.getResources().getDisplayMetrics().density;
	}
	
	/**
	 * 设置全屏与取消全屏
	 * @param isFullScreen
	 */
	public void fullScreen(Boolean isFullScreen) {
		if(isFullScreen) {
			act.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
					WindowManager.LayoutParams.FLAG_FULLSCREEN); //设置全屏
		} else {
			    WindowManager.LayoutParams attrs = act.getWindow().getAttributes();  
			    attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);  
			    act.getWindow().setAttributes(attrs);  
			    act.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);  
		}
	}
	
	public int getStatusBarHeight(){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = act.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        } 
        return statusBarHeight;
    }
	
}
