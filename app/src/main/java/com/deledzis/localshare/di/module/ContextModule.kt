package com.deledzis.localshare.di.module

import android.content.Context
import com.deledzis.localshare.di.qualifier.ApplicationContext
import com.deledzis.localshare.di.scopes.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class ContextModule(val context: Context) {

    @Provides
    @ApplicationScope
    @ApplicationContext
    fun provideContext(): Context {
        return context
    }
}
