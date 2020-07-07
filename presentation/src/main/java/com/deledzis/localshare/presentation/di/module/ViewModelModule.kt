package com.deledzis.localshare.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.presentation.di.key.ViewModelKey
import com.deledzis.localshare.presentation.viewmodel.ViewModelFactory
import com.deledzis.localshare.presentation.viewmodel.register.RegisterViewModel
import com.deledzis.localshare.presentation.viewmodel.signin.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    internal abstract fun registerViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    internal abstract fun signInViewModel(viewModel: SignInViewModel): ViewModel

}