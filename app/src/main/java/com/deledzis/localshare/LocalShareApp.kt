package com.deledzis.localshare

import com.deledzis.localshare.di.component.DaggerLocalShareAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class LocalShareApp : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerLocalShareAppComponent.factory().create(this)
    }
}