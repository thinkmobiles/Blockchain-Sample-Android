package com.thinkmobiles.blockchainsample.domain;

import com.thinkmobiles.blockchainsample.presentation.fragments.create_wallet.RegisterWalletContract;
import com.thinkmobiles.blockchainsample.presentation.fragments.exchange_rate.ExchangeRateContract;
import com.thinkmobiles.blockchainsample.presentation.utils.Constants;

import java.util.Map;

import info.blockchain.api.createwallet.CreateWallet;
import info.blockchain.api.createwallet.CreateWalletResponse;
import info.blockchain.api.exchangerates.Currency;
import info.blockchain.api.exchangerates.ExchangeRates;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Michael Soyma (Created on 4/6/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public final class BlockChainRepository implements  RegisterWalletContract.RegisterWalletModel,
                                                    ExchangeRateContract.ExchangeRateModel {

    @Override
    public Observable<CreateWalletResponse> createWallet(String email, String password) {
        return Observable.fromCallable(() -> CreateWallet.create(
                    Constants.BASE_URL,
                    password,
                    Constants.API_KEY,
                    null, null,
                    email))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public Observable<Map<String, Currency>> getExchangeRates() {
        return Observable.fromCallable(() -> ExchangeRates.getTicker(Constants.API_KEY))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread());
    }
}
