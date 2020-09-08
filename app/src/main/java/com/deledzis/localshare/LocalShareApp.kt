package com.deledzis.localshare

import android.content.Context
import android.content.Intent
import androidx.multidex.MultiDex
import com.deledzis.localshare.di.component.DaggerApplicationComponent
import com.google.android.gms.security.ProviderInstaller
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber


class LocalShareApp : DaggerApplication() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        upgradeSecurityProvider()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        setupTimber()
    }

    override fun applicationInjector(): AndroidInjector<out LocalShareApp> =
        DaggerApplicationComponent.factory().create(this)

    private fun upgradeSecurityProvider() {
        try {
            ProviderInstaller.installIfNeededAsync(this, object : ProviderInstallListener {
                override fun onProviderInstalled() {
                    Timber.i("New security provider installed.")
                }

                override fun onProviderInstallFailed(
                    errorCode: Int,
                    recoveryIntent: Intent
                ) {
//                    GooglePlayServicesUtil.showErrorDialogFragment(errorCode, applicationContext)
                    Timber.i("New security provider install failed.")
                }
            })
        } catch (ex: Exception) {
            Timber.e("Unknown issue trying to install a new security provider")
        }
    }

    private fun setupTimber() {
        Timber.uprootAll()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}