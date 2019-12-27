package com.alsc.wallet.entity;

import android.os.Parcel;
import android.os.Parcelable;

public class TokenInfo implements Parcelable {
    public final String address;  //地址
    public final String name;     //名称
    public final String symbol;   //币种  如BTC
    public final int decimals;    //扩展字段

    public TokenInfo(String address, String name, String symbol, int decimals) {
        this.address = address;
        this.name = name;
        this.symbol = symbol;
        this.decimals = decimals;
    }

    private TokenInfo(Parcel in) {
        address = in.readString();
        name = in.readString();
        symbol = in.readString();
        decimals = in.readInt();
    }

    public static final Creator<TokenInfo> CREATOR = new Creator<TokenInfo>() {
        @Override
        public TokenInfo createFromParcel(Parcel in) {
            return new TokenInfo(in);
        }

        @Override
        public TokenInfo[] newArray(int size) {
            return new TokenInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(name);
        dest.writeString(symbol);
        dest.writeInt(decimals);
    }
}
