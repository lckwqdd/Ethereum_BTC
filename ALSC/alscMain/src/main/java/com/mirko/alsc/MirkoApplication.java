package com.mirko.alsc;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alsc.net.NetApplication;
import com.alsc.net.db.GreenDaoUtil;
import com.alsc.wallet.utils.AppFilePath;
import com.mirko.alsc.utils.Constant;
import com.mirko.androidutil.BaseApplication;
import com.mirko.androidutil.utils.FileUtils;
import com.mirko.androidutil.utils.MetaDataUtil;
import com.mirko.androidutil.utils.android.Application;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import io.realm.Realm;

public class MirkoApplication extends BaseApplication {

    public  static String HOST;
    private final static String ALSC_API = "ALSC_API";

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        initData();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this) ;
    }

    private void initData(){
        try {
            //获取API地址
            HOST = MetaDataUtil.getMetaDataValue(mContext, ALSC_API);
        } catch (Exception e) {
            Application.exit(mContext);
        }
        NetApplication.getInstance().init(HOST);
        Realm.init(this);
        AppFilePath.init(this);
        GreenDaoUtil.getInstance().initGreenDao(this);
        FileUtils.getInstance().init(this);
        initUniversalImageLoader();
    }


    /**
     * 初始化配置ImageLoader
     */
    private void initUniversalImageLoader(){
        File cacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), Constant.CACHE_PATH);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .memoryCacheExtraOptions(1080,1920)     //缓存文件的最大长宽
                .diskCacheExtraOptions(1080,1920,null)  //本地缓存文件的最大长宽
                .threadPoolSize(3)					  //线程池的加载数量
                .threadPriority(Thread.NORM_PRIORITY-2) //线程池的优先级
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2*1024*1024))
                .memoryCacheSizePercentage(13)
                .memoryCacheSize(2*1024*1024)     //内存缓存的大小
                .diskCacheSize(100*1024*1024)      //本地缓存的大小
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator()) //将保存的Uri名称进行MD5加密
                .diskCacheFileCount(100)      //可以缓存的文件数量
                .imageDownloader(new BaseImageDownloader(getApplicationContext(),5*1000,30*1000)) // connectTimeout (5 s), readTimeout (30 s)
                .imageDecoder(new BaseImageDecoder(true))
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .build();

        ImageLoader.getInstance().init(config);
    }

}
