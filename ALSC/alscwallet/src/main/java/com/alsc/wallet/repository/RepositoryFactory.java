package com.alsc.wallet.repository;

import com.alsc.wallet.service.BlockExplorerClient;
import com.alsc.wallet.service.EthplorerTokenService;
import com.alsc.wallet.service.TokenExplorerClientType;
import com.google.gson.Gson;
import okhttp3.OkHttpClient;

/**
 */

public class RepositoryFactory {

    public TokenRepository tokenRepository;
    public TransactionRepository transactionRepository;
    public EthereumNetworkRepository ethereumNetworkRepository;

    public static RepositoryFactory sSelf;

    private RepositoryFactory(SharedPreferenceRepository sp, OkHttpClient httpClient, Gson gson) {
        ethereumNetworkRepository = EthereumNetworkRepository.init(sp);

        TokenLocalSource tokenLocalSource = new RealmTokenSource();

        TokenExplorerClientType tokenExplorerClientType =  new EthplorerTokenService(httpClient, gson);
        BlockExplorerClient blockExplorerClient = new BlockExplorerClient(httpClient, gson, ethereumNetworkRepository);

        tokenRepository = new TokenRepository(httpClient, ethereumNetworkRepository, tokenExplorerClientType, tokenLocalSource);

        TransactionLocalSource inMemoryCache = new TransactionInMemorySource();

        transactionRepository = new TransactionRepository(ethereumNetworkRepository, inMemoryCache, null, blockExplorerClient);
    }

    public static RepositoryFactory init (SharedPreferenceRepository sp, OkHttpClient httpClient, Gson gson) {
        if (sSelf == null) {
            sSelf = new RepositoryFactory(sp, httpClient, gson);
        }
        return sSelf;
    }

}
