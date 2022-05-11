package com.diana.application.data.di.module;


import com.diana.application.data.utils.INavigator;
import com.diana.application.data.utils.IToast;
import com.diana.application.data.utils.NavigatorImpl;
import com.diana.application.data.utils.ToastImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Diana on 26.06.2019.
 */
@Context
@Module
public class ContextModule {

    private final android.content.Context context;

    public ContextModule(android.content.Context context) {
        this.context = context;
    }

    @Provides
    @Context
    public INavigator provideNavigator() {

        return new NavigatorImpl( context);
    }

    @Provides
    @Context
    public IToast provideToast() {

        return new ToastImpl((android.content.Context) context);
    }

}
