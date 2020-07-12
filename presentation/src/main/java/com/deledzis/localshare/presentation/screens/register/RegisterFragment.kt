package com.deledzis.localshare.presentation.screens.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentRegisterBinding
import javax.inject.Inject

class RegisterFragment @Inject constructor() : BaseFragment<RegisterViewModel>(), IRegisterActionsHandler {

    private lateinit var dataBinding: FragmentRegisterBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var registerViewModel: RegisterViewModel

    @Inject
    lateinit var userData: BaseUserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_register,
            container,
            false
        )
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = registerViewModel
        dataBinding.backHandler = this

        return dataBinding.root
    }

    override fun handleBackClicked() {
        activity.onBackPressed()
    }

    override fun bindObservers() {
        registerViewModel.user.observe(this, Observer {
            displayInfoToast(
                message = "Добро пожаловать! Вы вошли как ${it.email}"
            )
            userData.saveUser(it)
            activity.toHome()
        })
        registerViewModel.error.observe(this, Observer {
            if (!it.isNullOrBlank()) {
//                displayErrorToast(message = it)
            }
        })
    }
}