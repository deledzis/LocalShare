package com.deledzis.localshare.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.deledzis.localshare.ui.main.MainActivity
import com.deledzis.localshare.util.Loggable

abstract class BaseFragment : Fragment(), Loggable {
    protected lateinit var activity: MainActivity
    protected lateinit var fm: FragmentManager

    override fun onAttach(context: Context) {
        super.onAttach(context)

        activity = context as MainActivity
        fm = childFragmentManager
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindObservers()
    }

    protected open fun bindObservers() {}
}