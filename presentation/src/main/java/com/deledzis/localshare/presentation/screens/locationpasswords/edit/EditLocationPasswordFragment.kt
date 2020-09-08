package com.deledzis.localshare.presentation.screens.locationpasswords.edit

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.domain.model.entity.CloseEditLocationPasswordAction
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.infrastructure.extensions.layoutInflater
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseBottomSheetDialogFragment
import com.deledzis.localshare.presentation.databinding.FragmentBottomSheetEditLocationPasswordBinding
import com.deledzis.localshare.presentation.screens.locationpasswords.LocationPasswordsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import timber.log.Timber

class EditLocationPasswordFragment(
    private val viewModelFactory: ViewModelProvider.Factory,
    private val locationPasswordsViewModel: LocationPasswordsViewModel,
    private val password: LocationPassword,
    private val position: Int
) : BaseBottomSheetDialogFragment(), IEditLocationPasswordHandler {

    private lateinit var dataBinding: FragmentBottomSheetEditLocationPasswordBinding

    private lateinit var editLocationPasswordViewModel: EditLocationPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editLocationPasswordViewModel = injectViewModel(viewModelFactory)
        editLocationPasswordViewModel.init(this.password, this.position)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialogTheme)
        dataBinding = DataBindingUtil.inflate(
            requireContext().layoutInflater,
            R.layout.fragment_bottom_sheet_edit_location_password,
            null,
            false
        )
        dialog.setContentView(dataBinding.root)
        dialog.setOnShowListener {
            Handler().postDelayed({
                val bottomSheetDialog = it as BottomSheetDialog
                setupFullHeight(bottomSheetDialog)
            }, 0)
        }

        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = editLocationPasswordViewModel
        dataBinding.controller = this

        bindObservers()

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        Timber.d("dismissing $this")
        locationPasswordsViewModel.handleEditDialogDismiss()
        super.onDismiss(dialog)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        val attrs = dialog?.window?.attributes
        attrs?.apply {
            gravity = Gravity.BOTTOM
        }
        dialog?.window?.attributes = attrs
    }

    override fun bindObservers() {
        editLocationPasswordViewModel.action.observe(this, Observer {
            if (it is CloseEditLocationPasswordAction) {
                this.dismiss()
            }
        })
    }

    override fun handlePasswordKeyClicked(view: View) {
        displayInfoToast("Скопировано в буфер обмена")
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet) as FrameLayout?
        bottomSheet ?: return

        val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from<FrameLayout?>(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        layoutParams?.height = windowHeight
        bottomSheet.layoutParams = layoutParams
        behavior.peekHeight = layoutParams.height
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}