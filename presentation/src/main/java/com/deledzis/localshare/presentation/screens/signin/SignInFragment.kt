package com.deledzis.localshare.presentation.screens.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.domain.model.BaseUserData
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.infrastructure.util.FORGET_PASSWORD_FRAGMENT_TAG
import com.deledzis.localshare.infrastructure.util.REGISTER_FRAGMENT_TAG
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentSignInBinding
import com.deledzis.localshare.presentation.screens.forgetpassword.ForgetPasswordFragment
import com.deledzis.localshare.presentation.screens.register.RegisterFragment
import javax.inject.Inject

class SignInFragment @Inject constructor() : BaseFragment<SignInViewModel>(), ISignInActionsHandler {

    private lateinit var dataBinding: FragmentSignInBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var signInViewModel: SignInViewModel

    @Inject
    lateinit var registerFragment: RegisterFragment

    @Inject
    lateinit var forgetPasswordFragment: ForgetPasswordFragment

    @Inject
    lateinit var userData: BaseUserData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInViewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_sign_in,
            container,
            false
        )
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = signInViewModel
        dataBinding.controller = this

        return dataBinding.root
    }

    override fun handleRegisterClicked() {
        signInViewModel.handleFragmentChange()
        activity.addFragment(
            registerFragment,
            REGISTER_FRAGMENT_TAG
        )
    }

    override fun handleForgetPasswordClicked() {
        signInViewModel.handleFragmentChange()
        activity.addFragment(
            forgetPasswordFragment,
            FORGET_PASSWORD_FRAGMENT_TAG
        )
    }

    override fun bindObservers() {
        signInViewModel.user.observe(this, Observer {
            displayWarningToast(
                message = "Добро пожаловать! Вы вошли как ${it.email}"
            )
            userData.saveUser(it)
            activity.toHome()
        })
        signInViewModel.error.observe(this, Observer {
            if (!it.isNullOrBlank()) {
//                displayErrorToast(message = it)
            }
        })
    }
}