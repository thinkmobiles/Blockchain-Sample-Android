package com.thinkmobiles.blockchainsample.presentation.fragments.create_wallet;

/**
 * @author Michael Soyma (Created on 4/6/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
abstract class RegisterWalletError {

    enum Email {
        NONE, EMPTY, NOT_VALID
    }

    enum Password {
        NONE, EMPTY, NOT_VALID
    }

    enum PasswordConfirm {
        NONE, EMPTY, NOT_EQUAL
    }
}
