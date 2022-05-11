package com.diana.application.data.di.component;

import com.diana.application.data.di.module.Context;
import com.diana.application.data.di.module.ContextModule;
import com.diana.application.ui.ProductDetailsActivity;
import com.diana.application.ui.ProductListFragment;
import com.diana.application.ui.CategoryListFragment;
import com.diana.application.ui.StartActivity;

import dagger.Subcomponent;

/**
 * Created by Diana on 26.06.2019.
 */

@Subcomponent(modules = ContextModule.class)
@Context
public interface IContextComponent {

    void inject(StartActivity mainActivity);
    void inject(ProductListFragment fragment);
    void inject(CategoryListFragment fragment);
    void inject(ProductDetailsActivity mainActivity);

}
