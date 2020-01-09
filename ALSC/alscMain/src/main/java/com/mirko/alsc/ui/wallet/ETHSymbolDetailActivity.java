package com.mirko.alsc.ui.wallet;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import com.alsc.utils.base.AlscBaseActivity;
import com.alsc.wallet.bean.request.CodeTransfersInofRequest;
import com.alsc.wallet.bean.response.CodeTransferInfoResponse;
import com.alsc.wallet.utils.LogUtils;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ThreadUtils;
import com.mirko.alsc.R;
import com.mirko.alsc.constant.Constants;
import com.mirko.alsc.databinding.ActivitySymbolDetailBinding;
import com.mirko.alsc.utils.UrlRequstUtils;

/**
 * 充币成功界面
 */
public class ETHSymbolDetailActivity extends AlscBaseActivity implements View.OnClickListener {
    private ActivitySymbolDetailBinding binding;
    private int position;

    @Override
    public void initViews(Bundle savedInstanceState) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_symbol_detail);
        binding.commonHeader.tvHeaderMiddle.setVisibility(View.GONE);
        binding.commonHeader.ivHeaderLeft.setOnClickListener(this);

        Intent intent = getIntent();
        position = intent.getIntExtra(Constants.TRANSFER_POSIITION, 1);
        LogUtils.d("position="+position);
    }

    @Override
    public void initAttrs() {
    }

    @Override
    public void loadData() {
        loadSymbalDetail();
    }

    private void loadSymbalDetail() {
        ThreadUtils.executeBySingle(new ThreadUtils.Task<CodeTransferInfoResponse>() {
            @Override
            public CodeTransferInfoResponse doInBackground() throws Throwable {
                CodeTransferInfoResponse response = UrlRequstUtils.CodeTransferInfo(ETHSymbolDetailActivity.this, new CodeTransfersInofRequest(position));
                return response;
            }

            @Override
            public void onSuccess(CodeTransferInfoResponse result) {
                if (result != null) {
                    LogUtils.d("冷钱包转账详情:"+ GsonUtils.toJson(result));
                    CodeTransferInfoResponse.DataBean data = result.getData();
                    if(data!=null){
                        binding.style.setText((data.getType() == 1) ? getString(R.string.my_tranfer) : getString(R.string.my_collect));
                        binding.state.setText(getState(data.getStatus()));
                        binding.time.setText(data.getAdd_time());
                        binding.address.setText(Constants.TEST_ACCOUNT_SEND);
                        binding.txid.setText(data.getHash());
                        binding.amount.setText(data.getAmount());
                    }
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onFail(Throwable t) {

            }

        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_header_left:
                onBackPressed();
                break;
        }
    }

    private String getState(int type) {
        String str = "";
        if (type == 1) {
            str = getString(R.string.meke_sure);
        } else if (type == 2) {
            str = getString(R.string.has_sure);
        } else if (type == 3) {
            str = getString(R.string.tranlation_fali);
        }
        return str;
    }
}
