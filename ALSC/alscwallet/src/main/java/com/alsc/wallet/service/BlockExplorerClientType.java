package com.alsc.wallet.service;


import com.alsc.wallet.entity.Transaction;
import io.reactivex.Observable;

public interface BlockExplorerClientType {
    Observable<Transaction[]> fetchTransactions(String forAddress, String forToken);
}
