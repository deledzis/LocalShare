package com.deledzis.localshare.presentation.screens.trackingpasswords.edit

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.domain.model.TrackingPassword
import com.deledzis.localshare.domain.model.entity.CloseEditTrackingPasswordAction
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.infrastructure.extensions.layoutInflater
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseBottomSheetDialogFragment
import com.deledzis.localshare.presentation.databinding.FragmentBottomSheetEditTrackingPasswordBinding
import com.deledzis.localshare.presentation.screens.trackingpasswords.TrackingPasswordsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import timber.log.Timber

class EditTrackingPasswordFragment(
    private val viewModelFactory: ViewModelProvider.Factory,
    private var trackingPasswordsViewModel: TrackingPasswordsViewModel,
    private val password: TrackingPassword,
    private val position: Int
) : BaseBottomSheetDialogFragment(), IEditTrackingPasswordHandler {

    private lateinit var dataBinding: FragmentBottomSheetEditTrackingPasswordBinding

    private lateinit var editTrackingPasswordViewModel: EditTrackingPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        editTrackingPasswordViewModel = injectViewModel(viewModelFactory)
        editTrackingPasswordViewModel.init(this.password, this.position)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialogTheme)
        dataBinding = DataBindingUtil.inflate(
            requireContext().layoutInflater,
            R.layout.fragment_bottom_sheet_edit_tracking_password,
            null,
            false
        )
        dialog.setContentView(dataBinding.root)
        dialog.setOnShowListener {
            Handler().postDelayed({
                val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
                bottomSheet ?: return@postDelayed
                val bottomSheetBehavior: BottomSheetBehavior<*> =
                    BottomSheetBehavior.from(bottomSheet)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }, 0)
        }

        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = editTrackingPasswordViewModel
        dataBinding.controller = this

        bindObservers()

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        Timber.e("dismissing $this")
        trackingPasswordsViewModel.handleEditDialogDismiss()
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
        editTrackingPasswordViewModel.action.observe(this, Observer {
            if (it is CloseEditTrackingPasswordAction) {
                this.dismiss()
            }
        })
    }

    override fun handlePasswordKeyClicked(view: View) {
        displayInfoToast("Скопировано в буфер обмена")
    }
}