package com.deledzis.localshare.presentation.di.module

import com.deledzis.localshare.domain.repository.ISignInRepository
import com.deledzis.localshare.presentation.viewmodel.signin.SignInViewModel
import dagger.Module
import dagger.Provides

@Module
class SignInFragmentModule {

    @Provides
    fun signInViewModel(repository: ISignInRepository): SignInViewModel {
        return SignInViewModel(repository)
    }
}