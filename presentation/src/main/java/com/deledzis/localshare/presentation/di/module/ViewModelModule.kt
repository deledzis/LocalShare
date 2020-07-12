package com.deledzis.localshare.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.presentation.base.ViewModelFactory
import com.deledzis.localshare.presentation.di.key.ViewModelKey
import com.deledzis.localshare.presentation.screens.forgetpassword.ForgetPasswordViewModel
import com.deledzis.localshare.presentation.screens.locationpasswords.LocationPasswordsViewModel
import com.deledzis.localshare.presentation.screens.main.MainActivityViewModel
import com.deledzis.localshare.presentation.screens.register.RegisterViewModel
import com.deledzis.localshare.presentation.screens.signin.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainActivityViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    internal abstract fun bindRegisterViewModel(viewModel: RegisterViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    internal abstract fun bindSignInViewModel(viewModel: SignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ForgetPasswordViewModel::class)
    internal abstract fun bindForgetPasswordViewModel(viewModel: ForgetPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationPasswordsViewModel::class)
    internal abstract fun bindLocationPasswordsViewModel(viewModel: LocationPasswordsViewModel): ViewModel

}