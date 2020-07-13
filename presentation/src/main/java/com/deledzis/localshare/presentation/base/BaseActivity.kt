package com.deledzis.localshare.presentation.base

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.deledzis.localshare.infrastructure.util.log.Loggable
import dagger.android.support.DaggerAppCompatActivity

/**
 * The base class for activity
 */
abstract class BaseActivity<T : ViewModel> : DaggerAppCompatActivity(), LifecycleOwner,
    Loggable