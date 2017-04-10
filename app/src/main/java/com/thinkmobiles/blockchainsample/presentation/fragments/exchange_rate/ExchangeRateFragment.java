package com.thinkmobiles.blockchainsample.presentation.fragments.exchange_rate;

import android.app.ProgressDialog;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkmobiles.blockchainsample.R;
import com.thinkmobiles.blockchainsample.domain.BlockChainRepository;
import com.thinkmobiles.blockchainsample.presentation.fragments.exchange_rate.adapter.ExchangeRateAdapter;
import com.thinkmobiles.blockchainsample.presentation.rules.BaseFragment;
import com.thinkmobiles.blockchainsample.presentation.rules.IRecyclerItemClickListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import info.blockchain.api.exchangerates.Currency;

/**
 * @author Michael Soyma (Created on 4/7/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public final class ExchangeRateFragment extends BaseFragment implements ExchangeRateContract.ExchangeRateView,
        View.OnClickListener, IRecyclerItemClickListener<String> {

    private ExchangeRateContract.ExchangeRatePresenter presenter;

    private ImageView fromExchangeIcon, toExchangeIcon;
    private TextView fromExchangeIconInText, toExchangeIconInText, emptyListText;
    private EditText fromExchangeValue, toExchangeValue;
    private RecyclerView exchangeRatesRecycler;
    private ExchangeRateAdapter exchangeRateAdapter;

    private ProgressDialog progressDialog;

    private NumberFormat numberFormat = DecimalFormat.getInstance();

    @Override
    protected int screenName() {
        return R.string.exchange_rate;
    }

    @Override
    protected int layoutRes() {
        return R.layout.fragment_exchange_rate;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void initUI() {
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(15);

        fromExchangeIcon = findView(R.id.ivFromExchangeIcon_FER);
        toExchangeIcon = findView(R.id.ivToExchangeIcon_FER);

        fromExchangeIconInText = findView(R.id.tvFromExchangeIconInText_FER);
        toExchangeIconInText = findView(R.id.tvToExchangeIconInText_FER);
        emptyListText = findView(R.id.tvListEmpty_FER);

        fromExchangeValue = findView(R.id.etFromExchangeValue_FER);
        toExchangeValue = findView(R.id.etToExchangeValue_FER);
        exchangeRatesRecycler = findView(R.id.rvExchangeRates_FER);

        exchangeRatesRecycler.setLayoutManager(new LinearLayoutManager(activity));
        exchangeRatesRecycler.setAdapter(exchangeRateAdapter = new ExchangeRateAdapter(this));

        fromExchangeValue.addTextChangedListener(fromExchangeTextWatcher);
        findView(R.id.ivChangeExchangeSides_FER).setOnClickListener(this);
    }

    @Override
    protected void initPresenter() {
        new ExchangeRatePresenter(this, new BlockChainRepository());
    }

    @Override
    public void setPresenter(ExchangeRateContract.ExchangeRatePresenter presenter) {
        this.presenter = presenter;
        this.presenter.subscribe();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivChangeExchangeSides_FER:
                presenter.changeExchangeSide();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fromExchangeValue.removeTextChangedListener(fromExchangeTextWatcher);
        this.presenter.unsubscribe();
    }

    @Override
    public void showProgress() {
        dismissProgress();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Getting exchange rates... Please wait.");
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
    public void showErrorToast() {
        Toast.makeText(activity, R.string.exchange_rate_error, Toast.LENGTH_SHORT).show();
        emptyListText.setVisibility(exchangeRateAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setExchangeRates(List<Pair<String, Currency>> data) {
        exchangeRateAdapter.updateData(data);
        emptyListText.setVisibility(exchangeRateAdapter.getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setFromExchange(String exchangeCode, double value) {
        if (exchangeCode.equals(ExchangeRatePresenter.BITCOIN)) {
            fromExchangeIconInText.setVisibility(View.INVISIBLE);
            fromExchangeIcon.setVisibility(View.VISIBLE);
        } else {
            fromExchangeIconInText.setText(exchangeCode);
            fromExchangeIconInText.setVisibility(View.VISIBLE);
            fromExchangeIcon.setVisibility(View.INVISIBLE);
        }
        fromExchangeValue.setText(numberFormat.format(value));
    }

    @Override
    public void setToExchange(String exchangeCode, double value) {
        if (exchangeCode.equals(ExchangeRatePresenter.BITCOIN)) {
            toExchangeIconInText.setVisibility(View.INVISIBLE);
            toExchangeIcon.setVisibility(View.VISIBLE);
        } else {
            toExchangeIconInText.setText(exchangeCode);
            toExchangeIconInText.setVisibility(View.VISIBLE);
            toExchangeIcon.setVisibility(View.INVISIBLE);
        }
        toExchangeValue.setText(numberFormat.format(value));
    }

    private final TextWatcher fromExchangeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            double result;
            try {
                result = numberFormat.parse(charSequence.toString()).doubleValue();
            } catch (ParseException e) {
                result = 0d;
                e.printStackTrace();
            }
            presenter.calculateExchange(result);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void selectItem(String data, int position) {
        exchangeRateAdapter.selectItem(position);
        presenter.setTargetCurrencyInKey(data);
    }
}
