package com.deledzis.localshare.presentation.di.builder

import com.deledzis.localshare.presentation.di.module.RegisterFragmentModule
import com.deledzis.localshare.presentation.screens.register.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RegisterFragmentBuilder {
    @ContributesAndroidInjector(modules = [RegisterFragmentModule::class])
    abstract fun provideRegisterFragmentFactory(): RegisterFragment
}