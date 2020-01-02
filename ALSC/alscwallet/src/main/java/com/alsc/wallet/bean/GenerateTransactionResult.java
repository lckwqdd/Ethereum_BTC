package com.alsc.wallet.bean;

import android.support.annotation.Nullable;

import com.alsc.wallet.btcCoin.btc.Transaction;

public class GenerateTransactionResult {
    public static final int ERROR_SOURCE_UNKNOWN = 0;
    public static final int ERROR_SOURCE_INPUT_TX_FIELD = 1;
    public static final int ERROR_SOURCE_ADDRESS_FIELD = 2;
    public static final int HINT_FOR_ADDRESS_FIELD = 3;
    public static final int ERROR_SOURCE_AMOUNT_FIELD = 4;

    public final Transaction btcTx, bchTx;
    public final String errorMessage;
    public final int errorSource;
    public final long fee;

    public GenerateTransactionResult(String errorMessage, int errorSource) {
        btcTx = null;
        bchTx = null;
        this.errorMessage = errorMessage;
        this.errorSource = errorSource;
        fee = -1;
    }

    public GenerateTransactionResult(Transaction btcTx, @Nullable Transaction bchTx, long fee) {
        this.btcTx = btcTx;
        this.bchTx = bchTx;
        errorMessage = null;
        errorSource = ERROR_SOURCE_UNKNOWN;
        this.fee = fee;
    }
}
