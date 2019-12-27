package com.mirko.alsc.views.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mirko.alsc.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 支持异步的ImageView 最好是用于需要异步的列表，否则static的imageCache无法及时释放 <br />
 * 初始化时设置高宽度才能时progressbar居中
 * @author honestwalker
 *
 */
public class AsyncImageView extends RelativeLayout {
	
	private String TAG = "AsyncImageView";
	
	private static ExecutorService threadPool = Executors.newFixedThreadPool(20);
	
	public static Map<String,SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	
	private String imageUrl = null;
	
	private boolean isLoaded = false;
	
	/** 是否启用 sqlite 图片缓存 */
	private boolean useDBCache = true;
	
	/** 是否根据指定的width 计算相应的inSampleSize, 开启比较耗时，但可一定程度防止OOM错误 ， 一般图片都较大时才开启 */
	private boolean caleInSampleSize = false;
	
	protected Context context;
	
	public MyImageView   imageView;

	protected ProgressBar progressBar;

	protected LayoutInflater inflater;
	private  int dpWidth= 0;
	
	public AsyncImageView(Context context) {
		super(context);
		this.context = context;
		createView();
	}
	
	public AsyncImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		createView();
	}
	
	public AsyncImageView(Context context, AttributeSet attrs , int defStyle) {
		super(context, attrs);
		this.context = context;
		createView();
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}
	
	/** 建立图片和loading */
	protected void createView() {

//		progressBar = new ProgressBar(context);
//		LayoutParams lp = new LayoutParams(40,40);
//		lp.addRule(RelativeLayout.CENTER_IN_PARENT);
//		progressBar.setLayoutParams(lp);
//		this.addView(progressBar);
		
		imageView   = new MyImageView(context);
//		imageView.setScaleType(ScaleType.FIT_XY);
		//imageView   = new GestureImageView(context);
		
		LayoutParams ivlp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		AsyncImageView.this.setLayoutParams(ivlp);
		imageView.setLayoutParams(ivlp);
		this.addView(imageView);
		
	}

	public void setImageViewWH(int dp){
		dpWidth = dp;
		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, dp);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, dp);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, dp);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, dp);
	}

	public void setImageViewHeight(int dp){
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, dp);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, dp);
	}

	public void setScaleType(ScaleType scaleType) {
		imageView.setScaleType(scaleType);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

	}
	
	/**
	 * 异步加载图片
	 * @param imageUrl
	 * @param useDBCache       是否使用数据库缓存
	 * @param useMemCache      是否使用内存缓存
	 */
	public void loadUrl(String imageUrl,final int width , boolean useDBCache , boolean useMemCache) {
		
		this.useDBCache = useDBCache;
		if(!useMemCache) {
			imageCache.remove(imageUrl);
		}
		loadUrl(imageUrl, width);
	}


	/**
	 * 加载带圆角图片
	 * @param imageUrl
	 */
	public void loadUrlRectRound(String imageUrl,final double width) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, (int)width);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.displayer(new RoundedBitmapDisplayer(20))
				.build();

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
	}
	/**
	 * 加载带圆角图片
	 * @param imageUrl
	 */
	public void loadUrlRectRound(String imageUrl,final int width,final int height,int corner) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, height);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, height);

		imageView.setScaleType(ScaleType.FIT_XY);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.displayer(new FlexibleRoundedBitmapDisplayer(20, corner))
				.build();

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
	}
	/**
	 * 加载圆形图片
	 * @param imageUrl
	 */
	public void loadUrlHeadRound(String imageUrl,final double width) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, (int)width);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.displayer(new AsyncImageRoundDisplayer(0))
				.showImageOnLoading(R.drawable.icon_default_head)
				.showImageForEmptyUri(R.drawable.icon_default_head)
				.showImageOnFail(R.drawable.icon_default_head)
				.build();

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
	}
	/**
	 * 加载管理员头像圆形图片
	 * @param imageUrl
	 */
	public void loadUrlMangerHeadRound(String imageUrl,final double width) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, (int)width);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.displayer(new AsyncImageRoundDisplayer(0))
				.showImageOnLoading(R.drawable.icon_default_head)
				.showImageForEmptyUri(R.drawable.icon_default_head)
				.showImageOnFail(R.drawable.icon_default_head)
				.build();

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
	}
	/**
	 * 加载管理员头像圆形图片
	 * @param imageUrl
	 */
	public void loadUrlGroupHeadRound(String imageUrl,final double width) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, (int)width);


		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.displayer(new AsyncImageRoundDisplayer(0))
				.showImageOnLoading(R.drawable.icon_default_head)
				.showImageForEmptyUri(R.drawable.icon_default_head)
				.showImageOnFail(R.drawable.icon_default_head)
				.build();

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
	}

	/**
	 * 加载用户头像头像圆形图片
	 * @param imageUrl
	 */
	public void loadImUserUrlIcon(String imageUrl,final double width) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, (int)width);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.displayer(new AsyncImageRoundDisplayer(0))
				.showImageOnLoading(R.drawable.icon_default_head)
				.showImageForEmptyUri(R.drawable.icon_default_head)
				.showImageOnFail(R.drawable.icon_default_head)
				.build();

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
	}

	/**
	 * 加载群头像头像圆形图片
	 * @param imageUrl
	 */
	public void loadImTeamUrlIcon(String imageUrl,final double width) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, (int)width);


		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.displayer(new AsyncImageRoundDisplayer(0))
				.showImageOnLoading(R.drawable.icon_default_head)
				.showImageForEmptyUri(R.drawable.icon_default_head)
				.showImageOnFail(R.drawable.icon_default_head)
				.build();

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
	}

	/**
	 * 使用universalimageloader来加载图片
	 * @param imageUrl
	 * @param width
     */
	public void loadUrl(String imageUrl,final double width,final double height) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, (int)height);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, (int)width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, (int)height);

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.build();

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
//		loadUrl(imageUrl, (int) width);
	}

	/**
	 * 使用universalimageloader来加载图片
	 * @param imageUrl
	 * @param width
     */
	public void loadUrl(final String imageUrl,final int width,final int height) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, height);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, height);

		final DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.ARGB_8888)
				.imageScaleType(ImageScaleType.EXACTLY)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.build();

		AsyncImageView.this.imageView.setScaleType(ScaleType.FIT_XY);

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);

//		loadUrl(imageUrl, (int) width);
	}

	/**
	 * 使用universalimageloader来加载图片
	 * @param imageUrl
	 * @param width
     */
	public void loadUrlAct(final String imageUrl,final int width,final int height) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, width);
		ViewSizeHelper.getInstance(getContext()).setHeight(AsyncImageView.this, height);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, width);
		ViewSizeHelper.getInstance(getContext()).setHeight(imageView, height);

		final DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.ARGB_8888)
				.imageScaleType(ImageScaleType.EXACTLY)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.build();

		AsyncImageView.this.imageView.setScaleType(ScaleType.FIT_CENTER);

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);

//		loadUrl(imageUrl, (int) width);
	}

	/**
	 * 使用universalimageloader来加载图片
	 * @param imageUrl
	 * @param width
     */
	public void loadUrl(final String imageUrl,final double width) {

		ViewSizeHelper.getInstance(getContext()).setWidth(AsyncImageView.this, (int)width);
		ViewSizeHelper.getInstance(getContext()).setWidth(imageView, (int)width);

		final DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565 )
				.imageScaleType(ImageScaleType.EXACTLY)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.build();

		AsyncImageView.this.imageView.post(new Runnable() {
			@Override
			public void run() {
				ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
			}
		});

//		loadUrl(imageUrl, (int) width);
	}

	public void loadUrl(String imageUrl) {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.build();

		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
	}

	public void loadUrl(String imageUrl,ScaleType fitType) {

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.cacheInMemory(true)   //开启内存缓存
				.cacheOnDisk(true)     //开启SD卡缓存
				.build();

		imageView.setScaleType(fitType);
		ImageLoader.getInstance().displayImage(imageUrl,AsyncImageView.this.imageView,options);
//		loadUrl(imageUrl, (int) width);
	}
	@Override
	public void setTag(Object tag) {
		imageView.setTag(tag);
	}
}
