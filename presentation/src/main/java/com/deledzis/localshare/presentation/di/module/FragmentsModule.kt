package com.deledzis.localshare.presentation.di.module

import com.deledzis.localshare.presentation.screens.locationpasswords.ILocationPasswordActionsHandler
import com.deledzis.localshare.presentation.screens.locationpasswords.LocationPasswordsFragment
import dagger.Module
import dagger.Provides

@Module
object FragmentsModule {
    private var locationPasswordsFragment: LocationPasswordsFragment? = null

    @Provides
    fun provideLocationPasswordsFragment(): LocationPasswordsFragment {
        if (locationPasswordsFragment == null) {
            locationPasswordsFragment = LocationPasswordsFragment()
        }
        return locationPasswordsFragment!!
    }

    @Provides
    fun provideLocationPasswordActionsHandler(): ILocationPasswordActionsHandler {
        if (locationPasswordsFragment == null) {
            locationPasswordsFragment = LocationPasswordsFragment()
        }
        return locationPasswordsFragment!!
    }

}