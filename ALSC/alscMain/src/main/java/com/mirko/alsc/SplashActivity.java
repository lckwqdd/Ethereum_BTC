package com.mirko.alsc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.alsc.net.cache.CacheManager;
import com.alsc.net.db.bean.ETHWallet;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.interact.FetchWalletInteract;
import com.alsc.wallet.utils.LogUtils;
import com.blankj.utilcode.constant.PermissionConstants;
import com.blankj.utilcode.util.PermissionUtils;
import com.mirko.alsc.ui.wallet.TotalAssetsActivity;
import com.mirko.alsc.ui.wallet.code.CodeWalletTypeActivity;
import com.mirko.androidutil.utils.android.ActivityUtils;
import com.mirko.androidutil.utils.android.ScreenUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * 欢迎界面
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applePermission();
    }


    /**
     * 7.0d动态申请权限
     */
    private void applePermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE).callback(new PermissionUtils.FullCallback() {
            @Override
            public void onGranted(List<String> permissionsGranted) {
                for (String permission : permissionsGranted) {
                    LogUtils.d("permission: " + permission);
                }
                new FetchWalletInteract().fetch().observeOn(AndroidSchedulers.mainThread()).delay(2, TimeUnit.SECONDS).subscribe(
                        SplashActivity.this::onWalltes, SplashActivity.this::onError
                );
            }

            @Override
            public void onDenied(List<String> permissionsDeniedForever, List<String> permissionsDenied) {
                //申请失败需要重新申请
                if (!permissionsDeniedForever.isEmpty()) {
                    showOpenAppSettingDialog();
                    return;
                }
                applePermission();
            }
        }).theme((activity -> {
            ScreenUtils.setFullScreen(activity);
        })).request();
    }

    /**
     * 系统设置权限
     */
    private void showOpenAppSettingDialog() {
        new AlertDialog.Builder(ActivityUtils.getTopActivity())
                .setTitle(android.R.string.dialog_alert_title)
                .setMessage(R.string.permission_denied_forever_message)
                .setPositiveButton(android.R.string.ok, ((dialog, which) -> {
                    PermissionUtils.launchAppDetailsSettings();
                    dialog.dismiss();
                })).setOnCancelListener((dialog -> {
            dialog.dismiss();
        })).setCancelable(false)
                .create()
                .show();
    }

    public void onWalltes(List<ETHWallet> ethWallets) {

//        if (ethWallets.size() == 0) {
//            Intent intent = new Intent(SplashActivity.this, CodeWalletTypeActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        } else {
//            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//        }
        if (getExpiresTime() > System.currentTimeMillis()) {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
//		   if (ethWallets.size() == 0) {
	            Intent intent = new Intent(SplashActivity.this, CodeWalletTypeActivity.class);
	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
	            startActivity(intent);
//	        } else {
//	            Intent intent = new Intent(SplashActivity.this, TotalAssetsActivity.class);
//	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//	            startActivity(intent);
//	        }

        }
    }


    public void onError(Throwable throwable) {
//        Intent intent = new Intent(SplashActivity.this, CodeWalletTypeActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        SplashActivity.this.startActivity(intent);
    }


    /**
     * 获取登录过期时间
     *
     * @return
     */
    private long getExpiresTime() {
        if (CacheManager.ExpiresTime.get() != null) {
            return CacheManager.ExpiresTime.get();
        }
        return 0;
    }

}
