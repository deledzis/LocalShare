package com.deledzis.localshare.presentation.base

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.transition.Slide
import com.deledzis.localshare.common.util.ToastType
import com.deledzis.localshare.infrastructure.util.log.Loggable
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.screens.main.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_error_toast.*
import kotlinx.android.synthetic.main.layout_info_toast.*
import kotlinx.android.synthetic.main.layout_warning_toast.*
import javax.inject.Inject

abstract class BaseBottomSheetDialogFragment : BottomSheetDialogFragment(),
    Loggable {
    protected lateinit var activity: MainActivity

    @Inject
    protected lateinit var applicationContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)

        exitTransition = Slide(Gravity.END)
        returnTransition = Slide(Gravity.END)
        enterTransition = Slide(Gravity.END)

        activity = context as MainActivity
    }

    protected open fun bindObservers() {}

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