package com.deledzis.localshare.presentation.screens.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.base.UserViewModel
import com.deledzis.localshare.presentation.databinding.FragmentRegisterBinding
import javax.inject.Inject

class RegisterFragment @Inject constructor() : BaseFragment<RegisterViewModel>(), IRegisterActionsHandler {

    private lateinit var dataBinding: FragmentRegisterBinding

    private lateinit var backPressedCallback: OnBackPressedCallback

    @Inject
    lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerViewModel = injectViewModel(viewModelFactory)
        backPressedCallback = requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack(R.id.signInFragment, false)
        }
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
        findNavController().popBackStack(R.id.signInFragment, false)
    }

    override fun bindObservers() {
        registerViewModel.user.observe(this, Observer {
            userViewModel.saveUser(it)
        })
        userViewModel.user.observe(this, Observer {
            if (it != null) {
                displayWarningToast(
                    message = "Добро пожаловать! Вы вошли как ${it.email}"
                )
                findNavController().popBackStack()
            }
        })
        registerViewModel.error.observe(this, Observer {
            if (!it.isNullOrBlank()) {
//                displayErrorToast(message = it)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }
}