package com.alsc.wallet.service;


import com.alsc.wallet.entity.Ticker;
import io.reactivex.Observable;

public interface TickerService {

    Observable<Ticker> fetchTickerPrice(String symbols, String currency);
}
