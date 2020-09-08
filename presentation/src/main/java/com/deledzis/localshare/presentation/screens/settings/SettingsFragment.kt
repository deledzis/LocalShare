package com.deledzis.localshare.presentation.screens.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentSettingsBinding
import com.deledzis.localshare.presentation.screens.main.UserViewModel
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsFragment @Inject constructor(): BaseFragment<SettingsViewModel>(), ISettingsActionsHandler {

    private lateinit var dataBinding: FragmentSettingsBinding

    @Inject
    lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingsViewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_settings,
            container,
            false
        )
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = settingsViewModel
        dataBinding.controller = this

        return dataBinding.root
    }

    override fun bindObservers() {
        userViewModel.user.observe(this, Observer {
            if (it == null) {
                findNavController().navigate(R.id.signInFragment)
            }
        })
    }

    override fun handleLogoutClicked(view: View) {
        userViewModel.saveUser(null)
    }
}