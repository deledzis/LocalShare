package com.deledzis.localshare.presentation.screens.trackingpasswords.add

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.domain.model.entity.CloseAddTrackingPasswordAction
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.infrastructure.extensions.layoutInflater
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseBottomSheetDialogFragment
import com.deledzis.localshare.presentation.databinding.FragmentBottomSheetAddTrackingPasswordBinding
import com.deledzis.localshare.presentation.screens.trackingpasswords.TrackingPasswordsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import timber.log.Timber

class AddTrackingPasswordFragment(
    private val viewModelFactory: ViewModelProvider.Factory,
    private var trackingPasswordsViewModel: TrackingPasswordsViewModel
) : BaseBottomSheetDialogFragment() {

    private lateinit var dataBinding: FragmentBottomSheetAddTrackingPasswordBinding

    private lateinit var addTrackingPasswordViewModel: AddTrackingPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addTrackingPasswordViewModel = injectViewModel(viewModelFactory)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), R.style.CustomBottomSheetDialogTheme)
        dataBinding = DataBindingUtil.inflate(
            requireContext().layoutInflater,
            R.layout.fragment_bottom_sheet_add_tracking_password,
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
        dataBinding.viewModel = addTrackingPasswordViewModel

        bindObservers()

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        Timber.d("dismissing $this")
        trackingPasswordsViewModel.handleAddDialogDismiss()
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
        addTrackingPasswordViewModel.action.observe(this, Observer {
            if (it is CloseAddTrackingPasswordAction) {
                this.dismiss()
            }
        })
    }
}