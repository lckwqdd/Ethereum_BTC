package com.mirko.alsc.utils;

import android.graphics.Bitmap;
import com.alsc.net.bean.CapitalData;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.cache.CacheManager;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.Hashtable;

/**
 * Created by Mirko on 2019/12/26 20:49.
 */
public class ComUtils {

    private static final String TAG = "ComUtils";
    private static final int QR_WIDTH = 300;
    private static final int QR_HEIGHT = 300;
    private static Bitmap bitmap;

    /**
     * 获取token缓存
     * @return
     */
    public static String getTokenCache(){
        if(CacheManager.LoginToken.get()!=null){
            return CacheManager.LoginToken.get();
        }
        return null;
    }


    /**
     * 获取sid
     *
     * @return
     */
    public static String getSid() {
        if (CacheManager.PicSid.get() != null) {
            return CacheManager.PicSid.get();
        }
        return null;
    }

    /**
     * 获取用户缓存
     *
     * @return
     */
    public static UserInfoResult getUserInfo() {
        if (CacheManager.UserInfoResult.get() != null) {
            return CacheManager.UserInfoResult.get();
        }
        return null;
    }

    /**
     * QR_WIDETH 二维码宽度 自行设置
     * QR_HIGHT 二维码高度 自行设置
     * imgQrcode 一个ImageView
     */
    public static Bitmap createQRImage(String address) {
        try {
            //判断URL合法性
            if (address == null || "".equals(address) || address.length() < 1) {
                return null;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(address,
                    BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
//下面这里按照二维码的算法，逐个生成二维码的图片，
//两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
                        pixels[y * QR_WIDTH + x] = 0xff000000;
                    } else {
                        pixels[y * QR_WIDTH + x] = 0xffffffff;
                    }
                }
            }
//生成二维码图片的格式，使用ARGB_8888
            bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
	
	
    /**
     * @return
     */
    public static CapitalData getCapitalData() {
        if (CacheManager.CapitalData.get() != null) {
            return CacheManager.CapitalData.get();
        }
        return null;
    }

}
