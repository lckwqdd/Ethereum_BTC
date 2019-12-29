package com.mirko.alsc.ui.wallet;

import android.Manifest;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.utils.LogUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityAddWalletBinding;
import com.mirko.alsc.databinding.ActivityQrcodeScannerBinding;

import java.util.List;

import cn.bingoogolapple.qrcode.core.BarcodeType;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 二维码扫描界面
 */
public class ScanQrCodeActivity extends AlscBaseActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks, QRCodeView.Delegate {
    private ActivityQrcodeScannerBinding binding;
    private static final String TAG = ScanQrCodeActivity.class.getSimpleName();
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private static final int QRCODE_RESULT = 124;
    private RelativeLayout rlFlashLight;
    private LinearLayout llBack;
    private ZXingView mZXingView;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qrcode_scanner);
        mZXingView = binding.zxingview;
    }

    @Override
    public void initAttrs() {
        rlFlashLight = findViewById(R.id.rl_flash_light);
        rlFlashLight.setOnClickListener(this);

        llBack = findViewById(R.id.lly_back);
        llBack.setOnClickListener(this);
        findViewById(R.id.choose_qrcde_from_gallery).setOnClickListener(this);
    }

    @Override
    public void loadData() {
        mZXingView.setDelegate(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lly_back:
                finish();
                break;
            case R.id.rl_flash_light:
                binding.zxingview.openFlashlight();
                break;
        }
    }


    @AfterPermissionGranted(REQUEST_CODE_QRCODE_PERMISSIONS)
    private void requestCodeQRCodePermissions() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, getString(R.string.open_camera_light), REQUEST_CODE_QRCODE_PERMISSIONS, perms);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        mZXingView.startCamera();
        mZXingView.startSpotAndShowRect();   // 显示扫描框，并开始识别
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
    }


    @Override
    protected void onStart() {
        super.onStart();

        requestCodeQRCodePermissions();

        mZXingView.startCamera();
        mZXingView.changeToScanQRCodeStyle();
        mZXingView.setType(BarcodeType.ONLY_QR_CODE, null); // 只识别 QR_CODE
        mZXingView.startSpotAndShowRect();   // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    public void onScanQRCodeSuccess(String result) {

        vibrate();
        LogUtils.d(result);
        Intent intent = new Intent();
        intent.putExtra("scan_result", result);

        setResult(RESULT_OK, intent);
        finish();

    }

    /**
     * 摄像头环境亮度发生变化
     *
     * @param isDark 是否变暗
     */
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n"+getString(R.string.too_an);
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    /**
     * 处理打开相机出错
     */
    public void onScanQRCodeOpenCameraError() {
        LogUtils.e(TAG, "打开相机出错");
    }

}
