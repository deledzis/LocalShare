package com.deledzis.localshare.presentation.screens.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.infrastructure.util.REGISTER_FRAGMENT_TAG
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentSignInBinding
import com.deledzis.localshare.presentation.screens.register.RegisterFragment
import com.deledzis.localshare.presentation.viewmodel.signin.SignInViewModel
import javax.inject.Inject

class SignInFragment : BaseFragment<SignInViewModel>(), IRegisterHandler {

    private lateinit var dataBinding: FragmentSignInBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var signInViewModel: SignInViewModel

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
        activity.addFragment(
            RegisterFragment.newInstance(),
            REGISTER_FRAGMENT_TAG
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}