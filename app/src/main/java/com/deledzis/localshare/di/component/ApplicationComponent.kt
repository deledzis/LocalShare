package com.deledzis.localshare.di.component

import android.content.Context
import android.content.SharedPreferences
import com.deledzis.localshare.App
import com.deledzis.localshare.api.ApiInterface
import com.deledzis.localshare.base.BaseFragment
import com.deledzis.localshare.data.model.fcm_token.FcmTokenData
import com.deledzis.localshare.di.model.UserData
import com.deledzis.localshare.di.module.ApplicationModule
import com.deledzis.localshare.di.module.ContextModule
import com.deledzis.localshare.di.module.RetrofitModule
import com.deledzis.localshare.di.qualifier.ApplicationContext
import com.deledzis.localshare.di.scopes.ApplicationScope
import com.deledzis.localshare.ui.main.MainActivity
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        ContextModule::class,
        RetrofitModule::class,
        ApplicationModule::class
    ]
)
interface ApplicationComponent {

    fun inject(application: App)

    fun inject(activity: MainActivity)

    fun inject(fragment: BaseFragment)

    fun api(): ApiInterface

    @ApplicationContext
    fun context(): Context

    fun sharedPreferences(): SharedPreferences

    fun userData(): UserData

    fun fcmToken(): FcmTokenData
}