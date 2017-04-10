package com.thinkmobiles.blockchainsample.presentation.rules;

/**
 * @author Michael Soyma (Created on 4/7/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public interface IRecyclerItemClickListener<T> {
    void selectItem(final T data, final int position);
}
