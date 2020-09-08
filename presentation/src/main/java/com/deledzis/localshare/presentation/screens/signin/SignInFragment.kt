package com.deledzis.localshare.presentation.screens.signin

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
import com.deledzis.localshare.presentation.databinding.FragmentSignInBinding
import com.deledzis.localshare.presentation.screens.main.UserViewModel
import timber.log.Timber
import javax.inject.Inject

class SignInFragment @Inject constructor() : BaseFragment<SignInViewModel>(),
    ISignInActionsHandler {

    private lateinit var dataBinding: FragmentSignInBinding

    private lateinit var backPressedCallback: OnBackPressedCallback

    @Inject
    lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signInViewModel = injectViewModel(viewModelFactory)
        backPressedCallback = requireActivity().onBackPressedDispatcher.addCallback {
            activity.finish()
        }
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

    override fun handleRegisterClicked(view: View) {
        signInViewModel.handleFragmentChange()
        val action = SignInFragmentDirections.actionSignInFragmentToRegisterFragment()
        findNavController().navigate(action)
    }

    override fun handleForgetPasswordClicked(view: View) {
        signInViewModel.handleFragmentChange()
        val action = SignInFragmentDirections.actionSignInFragmentToForgetPasswordFragment()
        findNavController().navigate(action)
    }

    override fun bindObservers() {
        signInViewModel.user.observe(this, Observer {
            if (it != null) {
                userViewModel.saveUser(it)
            }
        })
        signInViewModel.error.observe(this, Observer {
            if (!it.isNullOrBlank()) {
//                displayErrorToast(message = it)
            }
        })
        userViewModel.user.observe(this, Observer {
            if (it != null) {
                findNavController().popBackStack()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }
}