package com.diana.application.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.diana.application.MainActivity;
import com.diana.application.R;
import com.diana.application.data.di.module.ContextModule;
import com.diana.application.data.repo.ICartRepository;
import com.diana.application.data.repo.ICatalogRepository;
import com.diana.application.data.utils.IToast;
import com.diana.application.databinding.ActivityProductDetailBinding;
import com.diana.application.vm.ProductDetailsVM;

import javax.inject.Inject;

/**
 * Created by Diana on 26.06.2019.
 */

public class ProductDetailsActivity extends AppCompatActivity {

    public static final String PRODUCT_ID = "productId";

    @Inject
    ICatalogRepository catalogRepository;
    @Inject
    ICartRepository cartRepository;
    @Inject
    IToast toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject();
        long productId = getIntent().getLongExtra(PRODUCT_ID, 0);
        ProductDetailsVM viewModel = new ProductDetailsVM(catalogRepository, cartRepository, productId, toast);
        ActivityProductDetailBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_product_detail);
        binding.setVm(viewModel);
        viewModel.loadProduct();
        setupToolBar();


    }

    private void setupToolBar() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
                } else if (verticalOffset == 0) {
                    // Expanded
                    getSupportActionBar().setHomeAsUpIndicator(null);
                } else if (appBarLayout.getTotalScrollRange() - Math.abs(verticalOffset) >= 50) {
                    getSupportActionBar().setHomeAsUpIndicator(null);
                }
            }
        });
    }

    private void inject() {

        ((MainActivity) getApplication())
                .getApplicationComponent()
                .plusContextModule(new ContextModule(this))
                .inject(this);
    }
}
