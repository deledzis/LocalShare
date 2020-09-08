package com.deledzis.localshare.presentation.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.presentation.base.ViewModelFactory
import com.deledzis.localshare.presentation.di.key.ViewModelKey
import com.deledzis.localshare.presentation.screens.forgetpassword.ForgetPasswordViewModel
import com.deledzis.localshare.presentation.screens.locationpasswords.LocationPasswordsViewModel
import com.deledzis.localshare.presentation.screens.locationpasswords.add.AddLocationPasswordViewModel
import com.deledzis.localshare.presentation.screens.locationpasswords.edit.EditLocationPasswordViewModel
import com.deledzis.localshare.presentation.screens.main.MainActivityViewModel
import com.deledzis.localshare.presentation.screens.register.RegisterViewModel
import com.deledzis.localshare.presentation.screens.settings.SettingsViewModel
import com.deledzis.localshare.presentation.screens.signin.SignInViewModel
import com.deledzis.localshare.presentation.screens.trackingpassword.TrackingPasswordViewModel
import com.deledzis.localshare.presentation.screens.trackingpasswords.TrackingPasswordsViewModel
import com.deledzis.localshare.presentation.screens.trackingpasswords.add.AddTrackingPasswordViewModel
import com.deledzis.localshare.presentation.screens.trackingpasswords.edit.EditTrackingPasswordViewModel
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
    @ViewModelKey(TrackingPasswordsViewModel::class)
    internal abstract fun bindTrackingPasswordsViewModel(viewModel: TrackingPasswordsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTrackingPasswordViewModel::class)
    internal abstract fun bindAddTrackingPasswordViewModel(viewModel: AddTrackingPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditTrackingPasswordViewModel::class)
    internal abstract fun bindEditTrackingPasswordViewModel(viewModel: EditTrackingPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TrackingPasswordViewModel::class)
    internal abstract fun bindTrackingPasswordViewModel(viewModel: TrackingPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationPasswordsViewModel::class)
    internal abstract fun bindLocationPasswordsViewModel(viewModel: LocationPasswordsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddLocationPasswordViewModel::class)
    internal abstract fun bindAddLocationPasswordViewModel(viewModel: AddLocationPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditLocationPasswordViewModel::class)
    internal abstract fun bindEditLocationPasswordViewModel(viewModel: EditLocationPasswordViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    internal abstract fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

}