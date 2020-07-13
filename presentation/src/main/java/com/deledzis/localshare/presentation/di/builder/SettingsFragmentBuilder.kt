package com.deledzis.localshare.presentation.di.builder

import com.deledzis.localshare.presentation.screens.settings.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SettingsFragmentBuilder {
    @ContributesAndroidInjector()
    abstract fun provideSettingsFragmentFactory(): SettingsFragment
}