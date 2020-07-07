package com.deledzis.localshare.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.transition.Slide
import com.deledzis.localshare.infrastructure.util.log.Loggable
import com.deledzis.localshare.presentation.screens.main.MainActivity
import dagger.android.support.DaggerFragment

abstract class BaseFragment<T : ViewModel> : DaggerFragment(),
    Loggable {
    protected lateinit var activity: MainActivity
    protected lateinit var fm: FragmentManager

    override fun onAttach(context: Context) {
        super.onAttach(context)

        exitTransition = Slide(Gravity.END)
        returnTransition = Slide(Gravity.END)
        enterTransition = Slide(Gravity.END)

        activity = context as MainActivity
        fm = childFragmentManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
    }

    protected open fun bindObservers() {}
}