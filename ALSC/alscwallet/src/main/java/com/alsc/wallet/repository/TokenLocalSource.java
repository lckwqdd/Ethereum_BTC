package com.alsc.wallet.repository;


import com.alsc.wallet.entity.NetworkInfo;
import com.alsc.wallet.entity.TokenInfo;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface TokenLocalSource {
    Completable put(NetworkInfo networkInfo, String walletAddress, TokenInfo tokenInfo);
    Single<TokenInfo[]> fetch(NetworkInfo networkInfo, String walletAddress);
}
