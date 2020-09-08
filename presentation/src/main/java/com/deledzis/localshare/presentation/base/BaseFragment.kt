package com.deledzis.localshare.presentation.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.deledzis.localshare.common.util.ToastType
import com.deledzis.localshare.infrastructure.util.log.Loggable
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.screens.main.MainActivity
import kotlinx.android.synthetic.main.layout_error_toast.*
import kotlinx.android.synthetic.main.layout_info_toast.*
import kotlinx.android.synthetic.main.layout_warning_toast.*
import timber.log.Timber
import javax.inject.Inject

abstract class BaseFragment<T : ViewModel> : Fragment(),
    Loggable {
    protected lateinit var activity: MainActivity

    @Inject
    protected lateinit var applicationContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Timber.d("onAttach: $this")

        activity = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated: $this")
        bindObservers()
    }

    protected open fun bindObservers() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate: $this")
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart: $this")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop: $this")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy: $this")
    }

    fun displayInfoToast(
        message: String
    ) {
        displayCustomToast(
            type = ToastType.INFO,
            message = message,
            duration = Toast.LENGTH_LONG
        )
    }

    fun displayWarningToast(
        message: String
    ) {
        displayCustomToast(
            type = ToastType.WARNING,
            message = message,
            duration = Toast.LENGTH_LONG
        )
    }

    fun displayErrorToast(
        message: String
    ) {
        displayCustomToast(
            type = ToastType.ERROR,
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
            ToastType.INFO -> getInfoToastLayout()
            ToastType.WARNING -> getWarningToastLayout()
            ToastType.ERROR -> getErrorToastLayout()
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
}