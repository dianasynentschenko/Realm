package com.diana.application.data.di.component;

import com.diana.application.data.di.module.ContextModule;
import com.diana.application.data.di.module.RepositoryModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Diana on 26.06.2019.
 */

@Component(modules = RepositoryModule.class)
@Singleton
public interface IApplicationComponent {

    IContextComponent plusContextModule(ContextModule contextModule);

}
