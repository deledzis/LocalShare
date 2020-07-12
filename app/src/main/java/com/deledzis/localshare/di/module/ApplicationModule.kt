package com.deledzis.localshare.di.module

import android.content.Context
import com.deledzis.localshare.LocalShareApp
import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.data.mapper.AuthMapper
import com.deledzis.localshare.data.mapper.LocationPasswordMapper
import com.deledzis.localshare.data.mapper.UserMapper
import com.deledzis.localshare.data.repository.auth.AuthDataRepository
import com.deledzis.localshare.data.repository.locationpassword.LocationPasswordsDataRepository
import com.deledzis.localshare.data.source.auth.AuthDataStoreFactory
import com.deledzis.localshare.data.source.locationpassword.LocationPasswordsDataStoreFactory
import com.deledzis.localshare.domain.repository.AuthRepository
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideContext(application: LocalShareApp): Context {
        return application
    }

    @Singleton
    @Provides
    internal fun provideLocationPasswordsRepository(
        factory: LocationPasswordsDataStoreFactory,
        mapper: LocationPasswordMapper,
        networkManager: BaseNetworkManager
    ): LocationPasswordsRepository {
        return LocationPasswordsDataRepository(
            factory = factory,
            locationPasswordMapper = mapper,
            networkManager = networkManager
        )
    }

    @Singleton
    @Provides
    internal fun provideAuthRepository(
        factory: AuthDataStoreFactory,
        authMapper: AuthMapper,
        userMapper: UserMapper,
        networkManager: BaseNetworkManager
    ): AuthRepository {
        return AuthDataRepository(
            factory = factory,
            authMapper = authMapper,
            userMapper = userMapper,
            networkManager = networkManager
        )
    }
}