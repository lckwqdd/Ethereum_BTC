package com.mirko.alsc.ui.devote;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.alsc.net.api.DevoteCheckPayPwdApi;
import com.alsc.net.api.DevoteCreatApi;
import com.alsc.net.api.DevoteExchangeApi;
import com.alsc.net.api.DevoteRealPriceApi;
import com.alsc.net.api.DevoteSuperApi;
import com.alsc.net.api.HomeMsgApi;
import com.alsc.net.bean.CapitalData;
import com.alsc.net.bean.entity.EmptyResultEntity;
import com.alsc.net.bean.entity.HomeMsgResultEntity;
import com.alsc.net.bean.entity.RealPriceResultEntity;
import com.alsc.net.bean.request.DevoteExchangeRequest;
import com.alsc.net.cache.CacheManager;
import com.alsc.net.retrofit.http.HttpManager;
import com.alsc.net.retrofit.listener.HttpOnNextListener;
import com.alsc.utils.base.AlscBaseActivity;
import com.mirko.alsc.R;
import com.mirko.alsc.databinding.ActivityDevoteHome1Binding;
import com.mirko.alsc.databinding.ActivityDevoteHomeBinding;
import com.mirko.alsc.utils.ComUtils;
import com.mirko.androidutil.encryption.MD5Utils;
import com.mirko.androidutil.utils.android.LogUtils;
import com.mirko.androidutil.view.ToastHelper;
import com.mirko.androidutil.view.statusbar.StatusBarUtil;


/**
 * Created by Mirko on 2019/12/28.
 * 贡献首页
 */
public class DevoteHome1Activity extends AlscBaseActivity implements View.OnClickListener {


    private static final String TAG = "DevoteHomeActivity";
    ActivityDevoteHome1Binding binding;
    private CapitalData capitalData = new CapitalData();
    private DevoteExchangeRequest devoteExchangeRequest = new DevoteExchangeRequest();
    private float realPrice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_devote_home1);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        updateView();
        binding.setClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadHomeData();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColorNoTranslucent(DevoteHome1Activity.this, getResources().getColor(R.color.color_slide_bg));
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        devoteRealPriceApi();
    }

    /**
     * 加载首页数据
     */

    private void loadHomeData() {

        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new HomeMsgApi((new HttpOnNextListener<HomeMsgResultEntity>() {
            @Override
            public void onNext(HomeMsgResultEntity result) {
                if (result != null) {
                    if (result.getUser_info() != null) {
                        updateView();
                        capitalData.setContri(result.getContri());
                        capitalData.setFenx_total(result.getFenx_total());
                        capitalData.setMax_sinvestment(result.getMax_sinvestment());
                        capitalData.setShare_total(result.getShare_total());
                        capitalData.setSinvestment(result.getSinvestment());
                        capitalData.setSurplusRep(result.getSurplusRep());
                        capitalData.setTotalJackpot(result.getTotalJackpot());
                        CacheManager.CapitalData.set(capitalData);
//                        binding.tvMsg.setText(capitalData.toString());
                    }
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }


        }), DevoteHome1Activity.this, token));
    }

    /**
     * 参与贡献
     */

    private void devoteCreatApi(float usdt) {
        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new DevoteCreatApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                ToastHelper.alert(DevoteHome1Activity.this, "参与贡献成功");
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }


        }), DevoteHome1Activity.this, token, usdt));
    }

    /**
     * 获取实时价格
     */

    private void devoteRealPriceApi() {
        HttpManager.getInstance().doHttpDeal(new DevoteRealPriceApi((new HttpOnNextListener<RealPriceResultEntity>() {
            @Override
            public void onNext(RealPriceResultEntity result) {
                binding.tvRealPrice.setText("实时价格：" +
                        result.getResult().getSymbol().substring(0, 4) +
                        "='" + result.getResult().getPrice() + result.getResult().getSymbol().substring(4, 8));
                binding.tvRealPrice2.setText("实时价格：" +
                        result.getResult().getSymbol().substring(0, 4) +
                        "='" + result.getResult().getPrice() + result.getResult().getSymbol().substring(4, 8));
                realPrice = Float.parseFloat(result.getResult().getPrice());
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }


        }), DevoteHome1Activity.this));
    }


    /**
     * 资产兑换
     */

    private void devoteExchangeApi() {

        float usdt = Float.parseFloat(binding.etCapitalCount.getText().toString());
        float alsc = usdt * realPrice;
        binding.etAlsc.setText(alsc + "");
        devoteExchangeRequest.setPrice(realPrice);
        devoteExchangeRequest.setAlsc(alsc);
        devoteExchangeRequest.setUsdt(usdt);
        devoteExchangeRequest.setToken(ComUtils.getTokenCache());

        HttpManager.getInstance().doHttpDeal(new DevoteExchangeApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                ToastHelper.alert(DevoteHome1Activity.this, "兑换成功");
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }


        }), DevoteHome1Activity.this, devoteExchangeRequest));
    }


    /**
     * 超级节点
     */
    private void devoteSuperApi() {

        HttpManager.getInstance().doHttpDeal(new DevoteSuperApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                ToastHelper.alert(DevoteHome1Activity.this, "参与节点成功");
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }


        }), DevoteHome1Activity.this, ComUtils.getTokenCache(),realPrice));
    }



    /**
     * 验证密码
     */
    private void devoteCheckPayPwd(String pwd, int type) {
        String token = ComUtils.getTokenCache();
        HttpManager.getInstance().doHttpDeal(new DevoteCheckPayPwdApi((new HttpOnNextListener<EmptyResultEntity>() {
            @Override
            public void onNext(EmptyResultEntity result) {
                if (type == 1) {
                    float usdt = Float.parseFloat(binding.etCount.getText().toString());
                    devoteCreatApi(usdt);
                } else if (type == 2) {
                    devoteExchangeApi();
                } else if (type == 3) {
                    devoteSuperApi();
                }
            }

            @Override
            public void onCacheNext(String string) {
                super.onCacheNext(string);
            }

            @Override
            public void onCancel() {
                super.onCancel();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }


        }), DevoteHome1Activity.this, token, pwd));
    }


    private void updateView() {
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_left:
                LogUtils.d(TAG, "返回点击");
                onBackPressed();

            case R.id.btn_devote_creat:
                String pwd = binding.etPayPwd.getText().toString();
                devoteCheckPayPwd(MD5Utils.getMD5Code(pwd), 1);

            case R.id.btn_exchange:
                String pwd2 = binding.etPayPwd2.getText().toString();
                devoteCheckPayPwd(MD5Utils.getMD5Code(pwd2), 2);
            case R.id.btn_super:
                String pwd3 = binding.etPayPwd3.getText().toString();
                devoteCheckPayPwd(MD5Utils.getMD5Code(pwd3), 3);

        }
    }
}
