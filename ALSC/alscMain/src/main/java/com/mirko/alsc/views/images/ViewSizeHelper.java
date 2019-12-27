package com.mirko.alsc.views.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import com.mirko.androidutil.utils.android.DisplayUtil;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class ViewSizeHelper {
	
	private static ViewSizeHelper instance;
	
	private ViewSizeHelper(){}
	
	private static Context context;
	
	private static final String TAG = "ViewSizeHelper";
	
	public static ViewSizeHelper getInstance(Context context) {
		ViewSizeHelper.context = context;
		if(instance == null) {
			instance = new ViewSizeHelper();
		}
		return instance;
	}
	
	/**
	 * 获取view宽度
	 * @param view
	 * @return
	 */
	public int getWidth(View view) {
		try{
			LayoutParams lp = view.getLayoutParams();
			return lp.width;
		} catch (Exception e) {
			try{
				RelativeLayout.LayoutParams rlp = (RelativeLayout.LayoutParams) view.getLayoutParams();
				return rlp.width;
			}catch (Exception e1) {
				try{
					LinearLayout.LayoutParams llp = (LinearLayout.LayoutParams) view.getLayoutParams();
					return llp.width;
				}catch (Exception e2) {
					return -1;
				}
			}
		}
	}
	
	/**
	 * 获取view宽度dip
	 * @param view
	 * @return
	 */
	public int getWidthDip(View view) {
		return DisplayUtil.getInstance(context).px2dip(getWidth(view));
	}
	
	/**
	 * 设置view宽度 单位px
	 * @param view
	 * @param width
	 * @return
	 */
	public int setWidth(View view,int width) {
		try{
			LayoutParams lp = view.getLayoutParams();
			lp.width = width;
			return lp.width;
		} catch (Exception e) {
			try {
			RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
			lp.width = width;
			return lp.width;
			} catch (Exception e1) {
				try {
					LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
					lp.width = width;
				return lp.width;
				} catch (Exception e2) {
					return -1;
				}
			}
		}
	}
	
	/** 
	 * 设置控件宽度，并根据比例设置高度
	 * @param view   待设置的控件
	 * @param width  要设置的宽度
	 * @param scaleWidth   比例参照宽度
	 * @param scaleHeight  比例参照高度
	 */
	public void setWidth(View view , int width , int scaleWidth, int scaleHeight) {
		int height = width * scaleHeight / scaleWidth;
		setWidth(view, width);
		setHeight(view, height);
		
	}
	
	public void setHeight(View view , int height , int scaleWidth, int scaleHeight) {
		int width = height * scaleWidth / scaleHeight;
		setHeight(view, height);
		setWidth(view, width);
		
	}
	
	/**
	 * 获取view高度 单位px
	 * @return
	 */
	public int getHeight(View view) {
		try{
			LayoutParams lp = view.getLayoutParams();
			return lp.height;
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * 设置view高度 ， 单位px
	 * @param view
	 * @param height
	 * @return
	 */
	public int setHeight(View view,int height) {
		try{
			LayoutParams lp = view.getLayoutParams();
			lp.height = height;
			return lp.height;
		} catch (Exception e) {
			try {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
				lp.height = height;
				return lp.height;
			} catch (Exception e2) {
				try {
					RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) view.getLayoutParams();
					lp.height = height;
					return lp.height;
				} catch (Exception e3) {
				}
			}
			return -1;
		}
	}
	
	public int setHeight(View view,float height) {
		try{
			LayoutParams lp = view.getLayoutParams();
			String heightStr = height + "";
			if(heightStr.indexOf(".") > -1) {
				heightStr = heightStr.substring(0,heightStr.indexOf("."));
			}
			lp.height = Integer.parseInt(heightStr);
			return lp.height;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public void setSize(View view,float width,float height) {
		setSize(view, (int)width, (int)height);
	}
	/**
	 * 设置view尺寸 单位px
	 * @param view
	 * @param width
	 * @param height
	 */
	public void setSize(View view,int width,int height) {
		try{
			LayoutParams lp = view.getLayoutParams();
			lp.width  = width;
			lp.height = height;
		} catch (Exception e) {
		}
	}
	
	public void margin(View view , int left , int top , int right , int bottom) {
		try{
			LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) view.getLayoutParams();
			if(left == -1) {
				left = lp.leftMargin;
			}
			if(top == -1) {
				top = lp.topMargin;
			}
			if(right == -1) {
				right = lp.rightMargin;
			}
			if(bottom == -1) {
				bottom = lp.bottomMargin;
			}
			lp.setMargins(left, top, right, bottom);
			view.setLayoutParams(lp);
		} catch (Exception e) {
			try{
				RelativeLayout.LayoutParams lp2  = (RelativeLayout.LayoutParams) view.getLayoutParams();
				if(left == -1) {
					left = lp2.leftMargin;
				}
				if(top == -1) {
					top = lp2.topMargin;
				}
				if(right == -1) {
					right = lp2.rightMargin;
				}
				if(bottom == -1) {
					bottom = lp2.bottomMargin;
				}
				lp2.setMargins(left, top, right, bottom);
				view.setLayoutParams(lp2);
			}catch (Exception e1) {
			}
		}
	}
	
	public void marginTop(View view , int marginTop) {
		margin(view,-1,marginTop,-1,-1);
	}
	public void marginTop(View view , float marginTop) {
		margin(view,-1,(int)marginTop,-1,-1);
	}
	
	public void marginLeft(View view, int marginLeft) {
		margin(view,marginLeft,-1,-1,-1);
	}
	public void marginLeft(View view, float marginLeft) {
		margin(view,(int)marginLeft,-1,-1,-1);
	}
	
	public void marginRight(View view,int marginRight) {
		margin(view,-1,-1,marginRight,-1);
	}
	public void marginRight(View view,float marginRight) {
		margin(view,-1,-1,(int)marginRight,-1);
	}
	
	public void marginBottom(View view,int marginBottom) {
		margin(view,-1,-1,-1,marginBottom);
	}
	public void marginBottom(View view,float marginBottom) {
		margin(view,-1,-1,-1,(int)marginBottom);
	}


	/**
	 * 转换图片成圆形
	 * @param bitmap 传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;//The radius of the picture
		float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
		if (width <= height) {
			roundPx = width / 2;

			//Define the coordinate of the 4 points of the rectangle
			top = 0;
			bottom = width;
			left = 0;
			right = width;

			height = width;

			//Define the coordinate of the 4 points of the rectangle
			dst_left = 3;
			dst_top = 3;
			dst_right = width-3;
			dst_bottom = width-3;
		}
		else {
			roundPx = height / 2;
			float clip = (width - height) / 2;

			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;

			width = height;

			dst_left = 3;
			dst_top = 3;
			dst_right = height-3;
			dst_bottom = height-3;
		}

		Bitmap output = Bitmap.createBitmap(width,
				height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
		final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);

		return output;
	}

	/**
	 * 转换带圆角图片
	 * @param bitmap 传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRectRoundBitmap(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		final Paint paint = new Paint();
		Bitmap output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Bitmap b = getRoundBitmap(bitmap, 20);
		final Rect rectSrc = new Rect(0, 0, b.getWidth(), b.getHeight());
		final Rect rectDest = new Rect(0,0,width,height);
		paint.reset();
		canvas.drawBitmap(b, rectSrc, rectDest, paint);


		return output;
	}

	/**
	 * 转换图片成正方形
	 * @param bitmap 传入Bitmap对象
	 * @return 转化后的bitmap
	 */
	public static Bitmap toSquareBitmap(Bitmap bitmap) {
		int w = bitmap.getWidth(); // 得到图片的宽，高
		int h = bitmap.getHeight();
		int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
		int retX = w > h ? (w - h) / 2 : 0;//基于原图，取正方形左上角x坐标
		int retY = w > h ? 0 : (h - w) / 2;
		return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
	}

	/**
	 * 获取圆角矩形图片方法
	 * @param bitmap
	 * @param roundPx,一般设置成14
	 * @return Bitmap
	 * @author caizhiming
	 */
	private static Bitmap getRoundBitmap(Bitmap bitmap, int roundPx) {

		Paint paint  = new Paint();
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xff424242;

		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		int x = bitmap.getWidth();

		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;


	}

}
