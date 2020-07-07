package com.deledzis.localshare.infrastructure.di

import android.content.Context
import com.deledzis.localshare.common.BaseNetworkManager
import com.deledzis.localshare.infrastructure.services.NetworkManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UtilsModule {

    @Singleton
    @Provides
    fun provideNetworkManager(context: Context): BaseNetworkManager {
        return NetworkManager(
            context = context
        )
    }
}