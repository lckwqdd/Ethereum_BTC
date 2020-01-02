package com.alsc.wallet.service;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

/**
 * Created by pc on 2018/1/26.
 */

public class Web3JService {
//    private static final Web3j ourInstance = Web3j.build(new HttpService("http://10.0.1.70:7545"));
    private static final Web3j ourInstance = Web3j.build(new HttpService("https://mainnet.infura.io/llyrtzQ3YhkdESt2Fzrk"));

    public static Web3j getInstance() {
        return ourInstance;
    }

    private Web3JService() {
    }


}
