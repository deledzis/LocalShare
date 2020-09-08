package com.deledzis.localshare.presentation.di.module

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.deledzis.localshare.presentation.base.InjectingFragmentFactory
import com.deledzis.localshare.presentation.di.key.FragmentKey
import com.deledzis.localshare.presentation.screens.forgetpassword.ForgetPasswordFragment
import com.deledzis.localshare.presentation.screens.locationpasswords.LocationPasswordsFragment
import com.deledzis.localshare.presentation.screens.register.RegisterFragment
import com.deledzis.localshare.presentation.screens.settings.SettingsFragment
import com.deledzis.localshare.presentation.screens.signin.SignInFragment
import com.deledzis.localshare.presentation.screens.trackingpassword.TrackingPasswordFragment
import com.deledzis.localshare.presentation.screens.trackingpasswords.TrackingPasswordsFragment
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(ForgetPasswordFragment::class)
    abstract fun bindForgetPasswordFragment(fragment: ForgetPasswordFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(LocationPasswordsFragment::class)
    abstract fun bindLocationPasswordsFragment(fragment: LocationPasswordsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(RegisterFragment::class)
    abstract fun bindRegisterFragment(fragment: RegisterFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SettingsFragment::class)
    abstract fun bindSettingsFragment(fragment: SettingsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(SignInFragment::class)
    abstract fun bindSignInFragment(fragment: SignInFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(TrackingPasswordsFragment::class)
    abstract fun bindTrackingPasswordsFragment(fragment: TrackingPasswordsFragment): Fragment

    @Binds
    @IntoMap
    @FragmentKey(TrackingPasswordFragment::class)
    abstract fun bindTrackingPasswordFragment(fragment: TrackingPasswordFragment): Fragment

    @Binds
    abstract fun bindFragmentFactory(factory: InjectingFragmentFactory): FragmentFactory
}