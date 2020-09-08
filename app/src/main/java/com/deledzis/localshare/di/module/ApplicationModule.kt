package com.deledzis.localshare.di.module

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.deledzis.localshare.LocalShareApp
import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.data.mapper.AuthMapper
import com.deledzis.localshare.data.mapper.LocationPasswordMapper
import com.deledzis.localshare.data.mapper.TrackingPasswordMapper
import com.deledzis.localshare.data.mapper.UserMapper
import com.deledzis.localshare.data.repository.auth.AuthDataRepository
import com.deledzis.localshare.data.repository.locationpasswords.LocationPasswordsDataRepository
import com.deledzis.localshare.data.repository.trackingpasswords.TrackingPasswordsDataRepository
import com.deledzis.localshare.data.source.auth.AuthDataStoreFactory
import com.deledzis.localshare.data.source.locationpasswords.LocationPasswordsDataStoreFactory
import com.deledzis.localshare.data.source.trackingpasswords.TrackingPasswordsDataStoreFactory
import com.deledzis.localshare.domain.repository.AuthRepository
import com.deledzis.localshare.domain.repository.LocationPasswordsRepository
import com.deledzis.localshare.domain.repository.TrackingPasswordsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindApplication(app: LocalShareApp): Application

    @SuppressLint("ModuleCompanionObjects")
    @Module
    companion object {
        @Provides
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }

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

        @Provides
        internal fun provideTrackingPasswordsRepository(
            factory: TrackingPasswordsDataStoreFactory,
            mapper: TrackingPasswordMapper,
            networkManager: BaseNetworkManager
        ): TrackingPasswordsRepository {
            return TrackingPasswordsDataRepository(
                factory = factory,
                trackingPasswordMapper = mapper,
                networkManager = networkManager
            )
        }
    }
}