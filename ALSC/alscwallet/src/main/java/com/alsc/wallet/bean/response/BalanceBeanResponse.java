package com.alsc.wallet.bean.response;

import com.alsc.wallet.bean.BasicData;

public class BalanceBeanResponse extends BasicData<BalanceBeanResponse> {
    private float assets;

    public float getAssets() {
        return assets;
    }

    public void setAssets(float assets) {
        this.assets = assets;
    }
}
