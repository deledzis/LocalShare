package com.deledzis.localshare.presentation.di.builder

import com.deledzis.localshare.presentation.screens.locationpasswords.LocationPasswordsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class LocationPasswordsFragmentBuilder {
    @ContributesAndroidInjector()
    abstract fun provideLocationPasswordsFragmentFactory(): LocationPasswordsFragment
}