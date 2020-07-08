package com.deledzis.localshare.presentation.di.module

import com.deledzis.localshare.domain.repository.IForgetPasswordRepository
import com.deledzis.localshare.presentation.viewmodel.forgetpassword.ForgetPasswordViewModel
import dagger.Module
import dagger.Provides

@Module
class ForgetPasswordFragmentModule {

    @Provides
    fun forgetPasswordViewModel(repository: IForgetPasswordRepository): ForgetPasswordViewModel {
        return ForgetPasswordViewModel(repository)
    }
}