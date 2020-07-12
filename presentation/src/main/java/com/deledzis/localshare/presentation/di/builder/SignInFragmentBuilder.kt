package com.deledzis.localshare.presentation.di.builder

import com.deledzis.localshare.presentation.screens.signin.SignInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SignInFragmentBuilder {
    @ContributesAndroidInjector()
    abstract fun provideSignInFragmentFactory(): SignInFragment
}