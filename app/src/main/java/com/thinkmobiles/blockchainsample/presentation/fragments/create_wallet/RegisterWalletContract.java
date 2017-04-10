package com.thinkmobiles.blockchainsample.presentation.fragments.create_wallet;

import com.thinkmobiles.blockchainsample.presentation.rules.BaseModel;
import com.thinkmobiles.blockchainsample.presentation.rules.BasePresenter;
import com.thinkmobiles.blockchainsample.presentation.rules.BaseView;

import info.blockchain.api.createwallet.CreateWalletResponse;
import rx.Observable;

/**
 * @author Michael Soyma (Created on 4/6/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public interface RegisterWalletContract {
    interface RegisterWalletView extends BaseView<RegisterWalletPresenter> {
        void showProgress();
        void dismissProgress();
        void showToast(boolean success);

        void showErrorStateForEmail(final RegisterWalletError.Email error);
        void showErrorStateForPassword(final RegisterWalletError.Password error);
        void showErrorStateForPasswordConfirm(final RegisterWalletError.PasswordConfirm error);
    }
    interface RegisterWalletPresenter extends BasePresenter {
        void createWallet(final String email, final String password, final String confirmPassword);
    }
    interface RegisterWalletModel extends BaseModel{
        Observable<CreateWalletResponse> createWallet(final String email, final String password);
    }
}
