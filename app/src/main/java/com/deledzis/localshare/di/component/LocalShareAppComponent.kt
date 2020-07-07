package com.deledzis.localshare.di.component

import com.deledzis.localshare.LocalShareApp
import com.deledzis.localshare.data.di.RepositoryModule
import com.deledzis.localshare.di.module.LocalShareAppModule
import com.deledzis.localshare.infrastructure.di.UtilsModule
import com.deledzis.localshare.presentation.di.builder.MainActivityBuilder
import com.deledzis.localshare.presentation.di.module.ViewModelModule
import com.deledzis.localshare.remote.di.NetworkModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        LocalShareAppModule::class,
        NetworkModule::class,
        UtilsModule::class,
        MainActivityBuilder::class,
        ViewModelModule::class,
        RepositoryModule::class,
        AndroidInjectionModule::class
    ]
)
interface LocalShareAppComponent : AndroidInjector<LocalShareApp> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<LocalShareApp>
}