package com.deledzis.localshare.presentation.di.builder

import com.deledzis.localshare.presentation.di.module.SignInFragmentModule
import com.deledzis.localshare.presentation.screens.signin.SignInFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SignInFragmentBuilder {
    @ContributesAndroidInjector(modules = [SignInFragmentModule::class])
    abstract fun provideSignInFragmentFactory(): SignInFragment
}