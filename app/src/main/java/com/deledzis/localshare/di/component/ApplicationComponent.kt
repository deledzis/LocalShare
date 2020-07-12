package com.deledzis.localshare.di.component

import com.deledzis.localshare.LocalShareApp
import com.deledzis.localshare.cache.di.DataCacheModule
import com.deledzis.localshare.di.module.ApplicationModule
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
        ApplicationModule::class,
        NetworkModule::class,
        UtilsModule::class,
        DataCacheModule::class,
        MainActivityBuilder::class,
        ViewModelModule::class,
        AndroidInjectionModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<LocalShareApp> {

    @Component.Factory
    interface Factory : AndroidInjector.Factory<LocalShareApp>
}