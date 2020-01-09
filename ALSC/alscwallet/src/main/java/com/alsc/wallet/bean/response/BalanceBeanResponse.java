package com.alsc.wallet.bean.response;

public class BalanceBeanResponse {

    /**
     * code : 1
     * msg : 成功
     * data : {"assets":0}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * assets : 0
         */

        private float assets;

        public float getAssets() {
            return assets;
        }

        public void setAssets(float assets) {
            this.assets = assets;
        }
    }
}
