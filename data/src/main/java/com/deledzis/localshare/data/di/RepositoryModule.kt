package com.deledzis.localshare.data.di

import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.data.repository.ForgetPasswordRepository
import com.deledzis.localshare.data.repository.RegisterRepository
import com.deledzis.localshare.data.repository.SignInRepository
import com.deledzis.localshare.data.source.server.ApiRemote
import com.deledzis.localshare.data.source.server.mapper.AuthMapper
import com.deledzis.localshare.data.source.server.mapper.UserMapper
import com.deledzis.localshare.domain.repository.IForgetPasswordRepository
import com.deledzis.localshare.domain.repository.IRegisterRepository
import com.deledzis.localshare.domain.repository.ISignInRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @Provides
    fun provideSignInRepository(
        apiRemote: ApiRemote,
        networkManager: BaseNetworkManager,
        authMapper: AuthMapper,
        userMapper: UserMapper
    ): ISignInRepository {
        return SignInRepository(
            apiRemote = apiRemote,
            networkManager = networkManager,
            authMapper = authMapper,
            userMapper = userMapper
        )
    }

    @Provides
    fun provideRegisterRepository(
        apiRemote: ApiRemote,
        networkManager: BaseNetworkManager,
        authMapper: AuthMapper,
        userMapper: UserMapper
    ): IRegisterRepository {
        return RegisterRepository(
            apiRemote = apiRemote,
            networkManager = networkManager,
            authMapper = authMapper,
            userMapper = userMapper
        )
    }

    @Provides
    fun provideForgetPasswordRepository(
        apiRemote: ApiRemote,
        networkManager: BaseNetworkManager
    ): IForgetPasswordRepository {
        return ForgetPasswordRepository(
            apiRemote = apiRemote,
            networkManager = networkManager
        )
    }

}