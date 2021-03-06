package com.alsc.wallet.repository;


import com.alsc.wallet.entity.Transaction;

import io.reactivex.Maybe;
import io.reactivex.Observable;

public interface TransactionRepositoryType {
	Observable<Transaction[]> fetchTransaction(String walletAddr, String token);
	Maybe<Transaction> findTransaction(String walletAddr, String transactionHash);
}
