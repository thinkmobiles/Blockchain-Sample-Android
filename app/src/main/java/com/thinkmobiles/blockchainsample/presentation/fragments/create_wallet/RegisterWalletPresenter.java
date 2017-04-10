package com.thinkmobiles.blockchainsample.presentation.fragments.create_wallet;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import java.util.regex.Pattern;

/**
 * @author Michael Soyma (Created on 4/6/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
final class RegisterWalletPresenter implements RegisterWalletContract.RegisterWalletPresenter {

    private RegisterWalletContract.RegisterWalletView view;
    private RegisterWalletContract.RegisterWalletModel model;

    private final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9_@]{10,}+$");

    RegisterWalletPresenter(RegisterWalletContract.RegisterWalletView view, RegisterWalletContract.RegisterWalletModel model) {
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
    public void createWallet(String email, String password, String confirmPassword) {
        boolean allValid = true;

        if (TextUtils.isEmpty(email)) {
            this.view.showErrorStateForEmail(RegisterWalletError.Email.EMPTY);
            allValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            this.view.showErrorStateForEmail(RegisterWalletError.Email.NOT_VALID);
            allValid = false;
        } else this.view.showErrorStateForEmail(RegisterWalletError.Email.NONE);

        if (TextUtils.isEmpty(password)) {
            this.view.showErrorStateForPassword(RegisterWalletError.Password.EMPTY);
            allValid = false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            this.view.showErrorStateForPassword(RegisterWalletError.Password.NOT_VALID);
            allValid = false;
        } else this.view.showErrorStateForPassword(RegisterWalletError.Password.NONE);

        if (TextUtils.isEmpty(confirmPassword)) {
            this.view.showErrorStateForPasswordConfirm(RegisterWalletError.PasswordConfirm.EMPTY);
            allValid = false;
        } else if (!password.equals(confirmPassword)) {
            this.view.showErrorStateForPasswordConfirm(RegisterWalletError.PasswordConfirm.NOT_EQUAL);
            allValid = false;
        } else this.view.showErrorStateForPasswordConfirm(RegisterWalletError.PasswordConfirm.NONE);

        if (allValid) createWallet(email, password);
    }

    private void createWallet(final String email, final String password) {
        this.view.showProgress();
        this.model.createWallet(email, password).subscribe(createWalletResponse -> {
            if (this.view != null) {
                this.view.dismissProgress();
                this.view.showToast(createWalletResponse != null);
            }
        }, throwable -> {
            throwable.printStackTrace();
            if (this.view != null) {
                this.view.dismissProgress();
                this.view.showToast(false);
            }
        });
    }
}
