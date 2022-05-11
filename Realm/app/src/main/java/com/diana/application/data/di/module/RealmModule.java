package com.diana.application.data.di.module;

import com.diana.application.data.db.ILocalData;
import com.diana.application.data.db.LocalDataImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Diana on 25.06.2019.
 */

@Module
public class RealmModule {

    private final boolean isInMemory;

    public RealmModule(boolean isInMemory) {
        this.isInMemory = isInMemory;
    }

    @Provides
    @Singleton
    public RealmConfiguration provideRealmConfiguration() {

        RealmConfiguration.Builder builder = new RealmConfiguration
                .Builder();

        builder.deleteRealmIfMigrationNeeded();
        if (isInMemory) {

            builder.inMemory();
        }

        return builder.build();
    }


    @Provides
    public Realm provideRealm(RealmConfiguration realmConfiguration) {

        return Realm.getInstance(realmConfiguration);
    }

    @Provides
    public ILocalData provideLocalData(Realm realm) {

        return new LocalDataImpl(realm);
    }
}
