package com.deledzis.localshare.presentation.screens.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentRegisterBinding
import com.deledzis.localshare.presentation.viewmodel.register.RegisterViewModel
import javax.inject.Inject

class RegisterFragment : BaseFragment<RegisterViewModel>(), IBackHandler {

    private lateinit var dataBinding: FragmentRegisterBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var registerViewModel: RegisterViewModel

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

    companion object {
        @JvmStatic
        fun newInstance() = RegisterFragment()
    }
}