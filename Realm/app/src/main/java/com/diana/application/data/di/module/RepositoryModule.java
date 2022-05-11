package com.diana.application.data.di.module;

import com.diana.application.data.db.ILocalData;
import com.diana.application.data.network.ApiService;
import com.diana.application.data.repo.CartRepositoryImpl;
import com.diana.application.data.repo.CatalogRepositoryImpl;
import com.diana.application.data.repo.ICartRepository;
import com.diana.application.data.repo.ICatalogRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Diana on 26.06.2019.
 */

@Module(includes = {NetworkModule.class, RealmModule.class})
public class RepositoryModule {

    @Provides
    @Singleton
    public ICatalogRepository provideCatalogRepository(ILocalData localData,
                                                       ApiService apiService) {

        return new CatalogRepositoryImpl(localData, apiService);
    }

    @Provides
    @Singleton
    public ICartRepository provideCartRepository(ILocalData localData) {

        return new CartRepositoryImpl(localData);
    }
}
