package com.alsc.wallet.repository;


import com.alsc.wallet.entity.NetworkInfo;

public interface OnNetworkChangeListener {
	void onNetworkChanged(NetworkInfo networkInfo);
}
