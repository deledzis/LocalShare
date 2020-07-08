package com.deledzis.localshare.presentation.di.module

import com.deledzis.localshare.presentation.screens.forgetpassword.ForgetPasswordFragment
import com.deledzis.localshare.presentation.screens.register.RegisterFragment
import com.deledzis.localshare.presentation.screens.signin.SignInFragment
import dagger.Module
import dagger.Provides

@Module
object FragmentsModule {
    private var signInFragment: SignInFragment? = null
    private var registerFragment: RegisterFragment? = null
    private var forgetPasswordFragment: ForgetPasswordFragment? = null

    @Provides
    fun signInFragment(): SignInFragment {
        if (signInFragment == null) {
            signInFragment = SignInFragment.newInstance()
        }
        return signInFragment!!
    }

    @Provides
    fun registerFragment(): RegisterFragment {
        if (registerFragment == null) {
            registerFragment = RegisterFragment.newInstance()
        }
        return registerFragment!!
    }

    @Provides
    fun forgetPasswordFragment(): ForgetPasswordFragment {
        if (forgetPasswordFragment == null) {
            forgetPasswordFragment = ForgetPasswordFragment.newInstance()
        }
        return forgetPasswordFragment!!
    }

}