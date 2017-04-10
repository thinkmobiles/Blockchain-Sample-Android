package com.thinkmobiles.blockchainsample.presentation.fragments.menu;

import android.view.View;

import com.thinkmobiles.blockchainsample.R;
import com.thinkmobiles.blockchainsample.presentation.fragments.create_wallet.RegisterWalletFragment;
import com.thinkmobiles.blockchainsample.presentation.fragments.exchange_rate.ExchangeRateFragment;
import com.thinkmobiles.blockchainsample.presentation.rules.BaseFragment;

/**
 * @author Michael Soyma (Created on 4/6/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public final class MenuFragment extends BaseFragment implements MenuContract.MenuView, View.OnClickListener {

    private MenuContract.MenuPresenter presenter;

    @Override
    protected int screenName() {
        return R.string.app_name;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_menu;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initUI() {
        findView(R.id.llCreateWallet_FM).setOnClickListener(this);
        findView(R.id.llExchangeRate_FM).setOnClickListener(this);
    }

    @Override
    protected void initPresenter() {
        new MenuPresenter(this, null);
    }

    @Override
    public void setPresenter(MenuContract.MenuPresenter presenter) {
        this.presenter = presenter;
        this.presenter.subscribe();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llCreateWallet_FM:
                presenter.openCreateWallet();
                break;
            case R.id.llExchangeRate_FM:
                presenter.openExchangeRate();
                break;
        }
    }

    @Override
    public void showCreateWallet() {
        activity.replaceFragment(new RegisterWalletFragment(), true);
    }

    @Override
    public void showExchangeRate() {
        activity.replaceFragment(new ExchangeRateFragment(), true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.presenter.unsubscribe();
    }
}
