package com.thinkmobiles.blockchainsample.presentation.fragments.menu;

/**
 * @author Michael Soyma (Created on 4/6/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public final class MenuPresenter implements MenuContract.MenuPresenter {

    private MenuContract.MenuView view;
    private MenuContract.MenuModel model;

    public MenuPresenter(MenuContract.MenuView view, MenuContract.MenuModel model) {
        this.view = view;
        this.model = model;

        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        this.view = null;
        this.model = null;
    }

    @Override
    public void openCreateWallet() {
        if (this.view != null)
            this.view.showCreateWallet();
    }

    @Override
    public void openExchangeRate() {
        if (this.view != null)
            this.view.showExchangeRate();
    }
}
