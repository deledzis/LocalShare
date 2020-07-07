package com.deledzis.localshare.di.module

import android.content.Context
import com.deledzis.localshare.LocalShareApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalShareAppModule {

    @Singleton
    @Provides
    fun provideContext(application: LocalShareApp): Context {
        return application
    }
}