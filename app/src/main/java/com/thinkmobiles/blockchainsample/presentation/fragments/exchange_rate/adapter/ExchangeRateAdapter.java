package com.thinkmobiles.blockchainsample.presentation.fragments.exchange_rate.adapter;

import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.thinkmobiles.blockchainsample.R;
import com.thinkmobiles.blockchainsample.presentation.rules.IRecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import info.blockchain.api.exchangerates.Currency;

/**
 * @author Michael Soyma (Created on 4/7/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public class ExchangeRateAdapter extends RecyclerView.Adapter<ExchangeRateVH> {

    private final List<Pair<String, Currency>> listData = new ArrayList<>();
    private final IRecyclerItemClickListener<String> itemClickListener;
    private int selectedPosition = -1;

    public ExchangeRateAdapter(IRecyclerItemClickListener<String> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void updateData(final List<Pair<String, Currency>> listData) {
        this.listData.addAll(listData);
        notifyItemRangeInserted(0, this.listData.size());
    }

    public void selectItem(final int pos) {
        final int prevPosititon = this.selectedPosition;
        this.selectedPosition = pos;
        notifyItemChanged(prevPosititon);
        notifyItemChanged(this.selectedPosition);
    }

    @Override
    public ExchangeRateVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExchangeRateVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_exchange_rate, parent, false), itemClickListener);
    }

    @Override
    public void onBindViewHolder(ExchangeRateVH holder, int position) {
        holder.bind(listData.get(position), selectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
