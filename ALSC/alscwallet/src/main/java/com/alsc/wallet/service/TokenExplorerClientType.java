package com.alsc.wallet.service;


import com.alsc.wallet.entity.TokenInfo;
import io.reactivex.Observable;

public interface TokenExplorerClientType {
    Observable<TokenInfo[]> fetch(String walletAddress);
}