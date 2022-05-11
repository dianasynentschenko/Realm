package com.diana.application.data.di.module;

import com.diana.application.data.network.ApiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Diana on 25.06.2019.
 */

@Module
public class NetworkModule {

    private final String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideRxJava2CallAdapterFactory() {

        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    JacksonConverterFactory provideJacksonConverterFactory() {

        return JacksonConverterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(RxJava2CallAdapterFactory rxJava2CallAdapterFactory,
                             JacksonConverterFactory jacksonConverterFactory) {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(rxJava2CallAdapterFactory)
                .addConverterFactory(jacksonConverterFactory)
                .build();
    }

    @Provides
    @Singleton
    protected ApiService provideApiService(Retrofit retrofit) {

        return retrofit.create(ApiService.class);
    }
}
