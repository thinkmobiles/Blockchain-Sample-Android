package com.thinkmobiles.blockchainsample.presentation.fragments.menu;

import com.thinkmobiles.blockchainsample.presentation.rules.BaseModel;
import com.thinkmobiles.blockchainsample.presentation.rules.BasePresenter;
import com.thinkmobiles.blockchainsample.presentation.rules.BaseView;

/**
 * @author Michael Soyma (Created on 4/6/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public interface MenuContract {
    interface MenuView extends BaseView<MenuPresenter> {
        void showCreateWallet();
        void showExchangeRate();
    }
    interface MenuPresenter extends BasePresenter {
        void openCreateWallet();
        void openExchangeRate();
    }
    interface MenuModel extends BaseModel {

    }
}
