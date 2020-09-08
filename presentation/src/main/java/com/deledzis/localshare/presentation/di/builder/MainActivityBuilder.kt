package com.deledzis.localshare.presentation.di.builder

import com.deledzis.localshare.common.di.scopes.ActivityScope
import com.deledzis.localshare.presentation.di.module.NavHostModule
import com.deledzis.localshare.presentation.screens.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            NavHostModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity

}