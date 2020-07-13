package com.deledzis.localshare.presentation.di.builder

import com.deledzis.localshare.presentation.screens.trackpasswords.TrackPasswordsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TrackPasswordsFragmentBuilder {
    @ContributesAndroidInjector()
    abstract fun provideTrackPasswordsFragmentFactory(): TrackPasswordsFragment
}