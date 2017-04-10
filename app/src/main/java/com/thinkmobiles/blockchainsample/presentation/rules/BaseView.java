package com.thinkmobiles.blockchainsample.presentation.rules;

/**
 * @author Michael Soyma (Created on 4/6/2017).
 *         Company: Thinkmobiles
 *         Email: michael.soyma@thinkmobiles.com
 */
public interface BaseView<P extends BasePresenter> {
    void setPresenter(final P presenter);
}
