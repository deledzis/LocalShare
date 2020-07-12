package com.deledzis.localshare.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModel
import androidx.transition.Slide
import com.deledzis.localshare.infrastructure.util.log.Loggable
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment.ToastType.*
import com.deledzis.localshare.presentation.screens.main.MainActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.layout_error_toast.*
import kotlinx.android.synthetic.main.layout_info_toast.*
import kotlinx.android.synthetic.main.layout_warning_toast.*
import javax.inject.Inject

abstract class BaseFragment<T : ViewModel> : DaggerFragment(),
    Loggable {
    protected lateinit var activity: MainActivity
    protected lateinit var fm: FragmentManager

    @Inject
    protected lateinit var applicationContext: Context

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

    fun displayInfoToast(
        message: String
    ) {
        displayCustomToast(
            type = INFO,
            message = message,
            duration = Toast.LENGTH_LONG
        )
    }

    fun displayWarningToast(
        message: String
    ) {
        displayCustomToast(
            type = WARNING,
            message = message,
            duration = Toast.LENGTH_LONG
        )
    }

    fun displayErrorToast(
        message: String
    ) {
        displayCustomToast(
            type = ERROR,
            message = message,
            duration = Toast.LENGTH_LONG
        )
    }

    private fun displayCustomToast(
        type: ToastType,
        message: String,
        duration: Int
    ) {
        val layout = when(type) {
            INFO -> getInfoToastLayout()
            WARNING -> getWarningToastLayout()
            ERROR -> getErrorToastLayout()
        }

        val text: TextView = layout.findViewById(R.id.text)
        text.text = message

        val toast = Toast(context)
        toast.setGravity(Gravity.BOTTOM, 0, 40)
        toast.duration = duration
        toast.view = layout
        toast.show()
    }

    private fun getInfoToastLayout(): View {
        return layoutInflater.inflate(
            R.layout.layout_info_toast,
            custom_info_toast_container
        )
    }

    private fun getWarningToastLayout(): View {
        return layoutInflater.inflate(
            R.layout.layout_warning_toast,
            custom_warning_toast_container
        )
    }

    private fun getErrorToastLayout(): View {
        return layoutInflater.inflate(
            R.layout.layout_error_toast,
            custom_error_toast_container
        )
    }

    enum class ToastType {
        INFO,
        WARNING,
        ERROR
    }
}