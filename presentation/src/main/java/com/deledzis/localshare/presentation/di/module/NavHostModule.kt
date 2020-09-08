package com.deledzis.localshare.presentation.di.module

import com.deledzis.localshare.presentation.base.InjectingNavHostFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NavHostModule {

    @ContributesAndroidInjector(modules = [FragmentBindingModule::class])
    abstract fun navHostFragmentInjector(): InjectingNavHostFragment
}