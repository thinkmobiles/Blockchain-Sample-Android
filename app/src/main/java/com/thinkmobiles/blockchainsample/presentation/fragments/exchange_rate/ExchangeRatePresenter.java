package com.thinkmobiles.blockchainsample.presentation.fragments.exchange_rate;

import android.support.v4.util.Pair;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import info.blockchain.api.exchangerates.Currency;

/**
 * @author Michael Soyma (Created on 4/7/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
final class ExchangeRatePresenter implements ExchangeRateContract.ExchangeRatePresenter {

    static final String BITCOIN = "BTC";

    private ExchangeRateContract.ExchangeRateView view;
    private ExchangeRateContract.ExchangeRateModel model;

    private Map<String, Currency> exchangeRates;
    private Pair<String, Currency> fromExchange;
    private Pair<String, Currency> toExchange;

    private double valueFrom = 1d;
    private double valueTo = 1d;

    ExchangeRatePresenter(ExchangeRateContract.ExchangeRateView view, ExchangeRateContract.ExchangeRateModel model) {
        this.view = view;
        this.model = model;

        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        fromExchange = new Pair<>(BITCOIN, new Currency(1d, 1d, 1d, 1d, BITCOIN));
        toExchange = new Pair<>(BITCOIN, new Currency(1d, 1d, 1d, 1d, BITCOIN));
        this.view.setFromExchange(fromExchange.first, fromExchange.second.getBuy().doubleValue());

        loadExchanges();
    }

    private void loadExchanges() {
        view.showProgress();
        model.getExchangeRates().subscribe(stringCurrencyMap -> {
            if (this.view != null) {
                exchangeRates = stringCurrencyMap;
                this.view.dismissProgress();
                this.view.setExchangeRates(prepareDataForList());
            }
        }, throwable -> {
            throwable.printStackTrace();
            if (this.view != null) {
                this.view.dismissProgress();
                this.view.showErrorToast();
            }
        });
    }

    @Override
    public void unsubscribe() {
        this.view = null;
        this.model = null;
    }

    @Override
    public void setTargetCurrencyInKey(String keyLabel) {
        if (fromExchange.first.equals(BITCOIN)) {
            toExchange = new Pair<>(keyLabel, exchangeRates.get(keyLabel));
            calculateExchange(valueFrom);
        }
        else {
            fromExchange = new Pair<>(keyLabel, exchangeRates.get(keyLabel));
            this.view.setFromExchange(fromExchange.first, fromExchange.second.getBuy().doubleValue());
        }
    }

    @Override
    public void calculateExchange(double valueFrom) {
        this.valueFrom = valueFrom;
        if (fromExchange.first.equals(BITCOIN))
            valueTo = toExchange.second.getBuy().multiply(BigDecimal.valueOf(valueFrom)).doubleValue();
        else valueTo = BigDecimal.valueOf(valueFrom).divide(fromExchange.second.getBuy(), 15, BigDecimal.ROUND_HALF_UP).doubleValue();
        this.view.setToExchange(toExchange.first, valueTo);
    }

    @Override
    public void changeExchangeSide() {
        final Pair<String, Currency> from = new Pair<>(fromExchange.first, fromExchange.second);
        fromExchange = toExchange;
        toExchange = from;
        this.view.setFromExchange(fromExchange.first, valueTo);
    }

    private List<Pair<String, Currency>> prepareDataForList() {
        final List<Pair<String, Currency>> data = new ArrayList<>();
        for (String label: exchangeRates.keySet()) {
            data.add(new Pair<>(label, exchangeRates.get(label)));
        }
        return data;
    }
}
