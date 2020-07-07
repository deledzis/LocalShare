package com.deledzis.localshare.presentation.di.module

import com.deledzis.localshare.domain.repository.IRegisterRepository
import com.deledzis.localshare.presentation.viewmodel.register.RegisterViewModel
import dagger.Module
import dagger.Provides

@Module
class RegisterFragmentModule {

    @Provides
    fun registerViewModel(repository: IRegisterRepository): RegisterViewModel {
        return RegisterViewModel(repository)
    }
}