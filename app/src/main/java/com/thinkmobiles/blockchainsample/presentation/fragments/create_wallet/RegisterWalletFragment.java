package com.thinkmobiles.blockchainsample.presentation.fragments.create_wallet;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thinkmobiles.blockchainsample.R;
import com.thinkmobiles.blockchainsample.domain.BlockChainRepository;
import com.thinkmobiles.blockchainsample.presentation.rules.BaseFragment;

/**
 * @author Michael Soyma (Created on 4/6/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public final class RegisterWalletFragment extends BaseFragment implements RegisterWalletContract.RegisterWalletView, View.OnClickListener {

    private RegisterWalletContract.RegisterWalletPresenter presenter;

    private EditText email, password, confirmPassword;
    private TextInputLayout emailLayout, passwordLayout, confirmPasswordLayout;
    private ProgressDialog progressDialog;

    @Override
    protected int screenName() {
        return R.string.create_wallet;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_register_wallet;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initUI() {
        email = findView(R.id.etEmail_FRW);
        password = findView(R.id.etPassword_FRW);
        confirmPassword = findView(R.id.etPasswordConfirm_FRW);

        emailLayout = findView(R.id.tilEmail_FRW);
        passwordLayout = findView(R.id.tilPassword_FRW);
        confirmPasswordLayout = findView(R.id.tilPasswordConfirm_FRW);

        findView(R.id.btnCreateWallet_FRW).setOnClickListener(this);
        testDemo();
    }

    private void testDemo() {
        email.setText("test@mail.ru");
        password.setText("1234567890");
        confirmPassword.setText("1234567890");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCreateWallet_FRW:
                presenter.createWallet(
                        email.getText().toString(),
                        password.getText().toString(),
                        confirmPassword.getText().toString());
                break;
        }
    }

    @Override
    protected void initPresenter() {
        new RegisterWalletPresenter(this, new BlockChainRepository());
    }

    @Override
    public void setPresenter(RegisterWalletContract.RegisterWalletPresenter presenter) {
        this.presenter = presenter;
        this.presenter.subscribe();
    }

    @Override
    public void showProgress() {
        dismissProgress();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Loading... Please wait.");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void dismissProgress() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void showToast(boolean success) {
        if (success) {
            Toast.makeText(activity, R.string.wallet_is_created, Toast.LENGTH_SHORT).show();
            activity.onSupportNavigateUp();
        }
        else Toast.makeText(activity, R.string.wallet_creating_error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorStateForEmail(RegisterWalletError.Email error) {
        switch (error) {
            case EMPTY:
                emailLayout.setError(getString(R.string.error_field_is_empty));
                emailLayout.setErrorEnabled(true);
                break;
            case NOT_VALID:
                emailLayout.setError(getString(R.string.error_field_not_valid));
                emailLayout.setErrorEnabled(true);
                break;
            case NONE:
                emailLayout.setError(null);
                emailLayout.setErrorEnabled(false);
                break;
        }
    }

    @Override
    public void showErrorStateForPassword(RegisterWalletError.Password error) {
        switch (error) {
            case EMPTY:
                passwordLayout.setError(getString(R.string.error_field_is_empty));
                passwordLayout.setErrorEnabled(true);
                break;
            case NOT_VALID:
                passwordLayout.setError(getString(R.string.error_field_not_valid));
                passwordLayout.setErrorEnabled(true);
                break;
            case NONE:
                passwordLayout.setError(null);
                passwordLayout.setErrorEnabled(false);
                break;
        }
    }

    @Override
    public void showErrorStateForPasswordConfirm(RegisterWalletError.PasswordConfirm error) {
        switch (error) {
            case EMPTY:
                confirmPasswordLayout.setError(getString(R.string.error_field_is_empty));
                confirmPasswordLayout.setErrorEnabled(true);
                break;
            case NOT_EQUAL:
                confirmPasswordLayout.setError(getString(R.string.error_field_not_equal_passwords));
                confirmPasswordLayout.setErrorEnabled(true);
                break;
            case NONE:
                confirmPasswordLayout.setError(null);
                confirmPasswordLayout.setErrorEnabled(false);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.presenter.unsubscribe();
    }
}
