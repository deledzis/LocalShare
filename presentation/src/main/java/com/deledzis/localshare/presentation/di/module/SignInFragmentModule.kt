package com.deledzis.localshare.presentation.di.module

import com.deledzis.localshare.common.di.scopes.FragmentScope
import com.deledzis.localshare.domain.repository.ISignInRepository
import com.deledzis.localshare.presentation.viewmodel.signin.SignInViewModel
import dagger.Module
import dagger.Provides

@Module
object SignInFragmentModule {

    @FragmentScope
    @Provides
    fun signInViewModel(repository: ISignInRepository): SignInViewModel {
        return SignInViewModel(repository)
    }
}