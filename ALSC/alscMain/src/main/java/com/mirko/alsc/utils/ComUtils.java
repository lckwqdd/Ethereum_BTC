package com.mirko.alsc.utils;

import android.graphics.Bitmap;

import com.alsc.net.bean.CapitalData;
import com.alsc.net.bean.UserInfoResult;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.db.bean.BtcWallet;
import com.alsc.wallet.utils.BTCWalletDaoUtils;
import com.alsc.wallet.utils.LogUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import org.bitcoinj.core.Address;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.UnreadableWalletException;
import org.bitcoinj.wallet.Wallet;
import org.mybitcoinj.core.DumpedPrivateKey;
import org.mybitcoinj.core.ECKey;
import org.mybitcoinj.core.LegacyAddress;
import org.mybitcoinj.core.SegwitAddress;
import org.mybitcoinj.crypto.ChildNumber;
import org.mybitcoinj.crypto.HDKeyDerivation;
import org.mybitcoinj.params.MainNetParams;

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
     *
     * @return
     */
    public static String getTokenCache() {
        if (CacheManager.LoginToken.get() != null) {
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

    /**
     * 比特币导入助记词
     *
     * @param seedCode
     * @return
     * @throws UnreadableWalletException
     * @throws org.mybitcoinj.wallet.UnreadableWalletException
     */
    public static BtcWallet importMnemonic(String seedCode) throws UnreadableWalletException, org.mybitcoinj.wallet.UnreadableWalletException {
        org.mybitcoinj.params.MainNetParams params = org.mybitcoinj.params.MainNetParams.get();//正式网络
        String passphrase = "";
        Long creationtime = System.currentTimeMillis() / 1000L;
        org.mybitcoinj.wallet.DeterministicSeed seed = new org.mybitcoinj.wallet.DeterministicSeed(seedCode, null, passphrase, creationtime);
        org.mybitcoinj.crypto.DeterministicKey hd = HDKeyDerivation.createMasterPrivateKey(seed.getSeedBytes());
        //分层
        hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(44, true));
        hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(0, true));
        hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(0, true));
        hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(0, false));
        hd = HDKeyDerivation.deriveChildKey(hd, new ChildNumber(0, false));
        ECKey ecKey = ECKey.fromPrivate(hd.getPrivKey());
        LegacyAddress address = LegacyAddress.fromKey(params, ecKey);

        BtcWallet btcWallet = new BtcWallet();
        btcWallet.setAddress(address.toString());
        btcWallet.setMnemonic(seedCode);
        btcWallet.setPrivateKey(hd.getPrivateKeyAsWiF(params));
        if (btcWallet != null) {
            BTCWalletDaoUtils.insertNewWallet(btcWallet);
        }
        return btcWallet;
    }

    /**
     * 比特币导入私钥
     *
     * @param privatekey
     */
    public void importFromPrivateKey(String privatekey) {
        MainNetParams mainNetParams = MainNetParams.get();
        ECKey key = DumpedPrivateKey.fromBase58(MainNetParams.get(), privatekey).getKey();
        SegwitAddress segwitAddress_1 = SegwitAddress.fromKey(mainNetParams, key);
        LegacyAddress address = LegacyAddress.fromKey(mainNetParams, key);
        System.out.println("----私钥: " + privatekey);
        System.out.println("----普通地址: " + address.toBase58());
        System.out.println("----隔离地址: " + segwitAddress_1.toString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("私钥：" + privatekey + "\n")
                .append("普通地址：" + address.toBase58() + "\n")
                .append("隔离地址：" + segwitAddress_1.toString() + "\n")
                .append("隔离地址类型：" + segwitAddress_1.getOutputScriptType());
    }

    public void importMnemonic1(String seedCode) throws UnreadableWalletException {
        org.bitcoinj.params.MainNetParams params = org.bitcoinj.params.MainNetParams.get();
        String passphrase = "";
        Long creationtime = System.currentTimeMillis() / 1000L;
        DeterministicSeed seed = new DeterministicSeed(seedCode, null, passphrase, creationtime);

        Wallet restoredWallet = Wallet.fromSeed(params, seed);
        DeterministicKey deterministicKey = restoredWallet.currentReceiveKey();
        String btcPrivateKey = deterministicKey.getPrivateKeyAsWiF(params);
        Address currentReceiveAddress = restoredWallet.currentReceiveAddress();
        String addressString = currentReceiveAddress.toBase58();
        LogUtils.d("BTC私钥：" + btcPrivateKey);
        LogUtils.d("BTC地址：" + addressString);
    }

}
