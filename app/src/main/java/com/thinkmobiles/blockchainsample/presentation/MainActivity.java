package com.thinkmobiles.blockchainsample.presentation;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thinkmobiles.blockchainsample.R;
import com.thinkmobiles.blockchainsample.presentation.fragments.menu.MenuFragment;
import com.thinkmobiles.blockchainsample.presentation.rules.BaseFragment;

public final class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        setContentView(R.layout.activity_main);
        replaceFragment(new MenuFragment(), false);
    }

    public void replaceFragment(final BaseFragment fragment, final boolean addToBackStack) {
        if (fragment != null) {
            if (addToBackStack)
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right, R.anim.slide_in_left, R.anim.slide_out_left)
                        .replace(R.id.flFragmentsContainer, fragment)
                        .addToBackStack(null)
                        .commitAllowingStateLoss();
            else
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flFragmentsContainer, fragment)
                        .commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportFragmentManager().removeOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
        shouldDisplayHomeUp();
    }

    @Override
    public boolean onSupportNavigateUp() {
        getSupportFragmentManager().popBackStack();
        return true;
    }

    public void shouldDisplayHomeUp(){
        boolean backExist = getSupportFragmentManager().getBackStackEntryCount() > 0;
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(backExist);
    }
}
