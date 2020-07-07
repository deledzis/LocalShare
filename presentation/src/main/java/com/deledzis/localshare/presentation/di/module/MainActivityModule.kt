package com.deledzis.localshare.presentation.di.module

import com.deledzis.localshare.presentation.viewmodel.main.MainActivityViewModel
import dagger.Module

import dagger.Provides

@Module
class MainActivityModule {

    @Provides
    fun provideMainActivityViewModel(): MainActivityViewModel {
        return MainActivityViewModel()
    }
}