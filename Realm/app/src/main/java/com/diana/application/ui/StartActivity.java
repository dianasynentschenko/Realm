package com.diana.application.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.diana.application.MainActivity;
import com.diana.application.R;
import com.diana.application.data.di.module.ContextModule;
import com.diana.application.data.repo.ICartRepository;
import com.diana.application.data.utils.INavigator;

import javax.inject.Inject;

/**
 * Created by Diana on 26.06.2019.
 */

public class StartActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    @Inject
    ICartRepository cartRepository;

    @Inject
    INavigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        resolveDependency();
        getSupportFragmentManager().addOnBackStackChangedListener(this);
        if (savedInstanceState == null) {
            navigator.openCategoryList();
        } else {
            onBackStackChanged();
        }
    }

    private void resolveDependency() {
        ((MainActivity)getApplication())
                .getApplicationComponent()
                .plusContextModule(new ContextModule(this))
                .inject(this);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem cartItem = menu.getItem(0);
        ((TextView)cartItem.getActionView()
                .findViewById(R.id.txtCartQuantity))
                .setText(String.valueOf(cartRepository.getCardCount()));
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() <= 1) {
            finish();
        } else {
            fragmentManager.popBackStack();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackStackChanged() {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.container);
        if (fragment != null) {
            setTitle(((BaseFragment) fragment).getTitle());
        }
        if (fragment instanceof CategoryListFragment) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setHomeAsUpIndicator(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
}
