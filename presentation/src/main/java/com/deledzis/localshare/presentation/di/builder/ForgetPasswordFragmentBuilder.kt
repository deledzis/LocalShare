package com.deledzis.localshare.presentation.di.builder

import com.deledzis.localshare.presentation.di.module.ForgetPasswordFragmentModule
import com.deledzis.localshare.presentation.screens.forgetpassword.ForgetPasswordFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForgetPasswordFragmentBuilder {

    @ContributesAndroidInjector(modules = [ForgetPasswordFragmentModule::class])
    abstract fun provideForgetPasswordFragmentFactory(): ForgetPasswordFragment
}