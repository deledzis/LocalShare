package com.deledzis.localshare.presentation.screens.forgetpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentForgetPasswordBinding
import com.deledzis.localshare.presentation.viewmodel.forgetpassword.ForgetPasswordViewModel
import javax.inject.Inject

class ForgetPasswordFragment : BaseFragment<ForgetPasswordViewModel>(),
    IForgetPasswordActionsHandler {

    private lateinit var dataBinding: FragmentForgetPasswordBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var forgetPasswordViewModel: ForgetPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgetPasswordViewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_forget_password,
            container,
            false
        )
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = forgetPasswordViewModel
        dataBinding.backHandler = this

        return dataBinding.root
    }

    override fun handleBackClicked() {
        activity.onBackPressed()
    }

    override fun bindObservers() {
        forgetPasswordViewModel.result.observe(this, Observer {
            if (it) {
                displayErrorToast(message = "Пользователь не найден")
            } else {
                displayInfoToast(message = "Сообщение с подтверждением отправлено Вам на почту")
            }
        })
        forgetPasswordViewModel.error.observe(this, Observer {
            if (!it.isNullOrBlank()) {
                displayErrorToast(message = it)
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ForgetPasswordFragment()
    }
}