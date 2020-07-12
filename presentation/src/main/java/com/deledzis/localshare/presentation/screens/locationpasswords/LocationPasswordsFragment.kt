package com.deledzis.localshare.presentation.screens.locationpasswords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.deledzis.localshare.domain.model.LocationPassword
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentLocationPasswordsBinding
import javax.inject.Inject

class LocationPasswordsFragment : BaseFragment<LocationPasswordsViewModel>(),
    ILocationPasswordActionsHandler/*, SwipeRefreshLayout.OnRefreshListener*/ {

    private lateinit var dataBinding: FragmentLocationPasswordsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var locationPasswordsViewModel: LocationPasswordsViewModel

    @Inject
    lateinit var adapter: LocationPasswordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationPasswordsViewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_location_passwords,
            container,
            false
        )
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = locationPasswordsViewModel

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataBinding.rvLocationPasswords.layoutManager = LinearLayoutManager(activity)
        dataBinding.rvLocationPasswords.adapter = adapter
    }

    override fun bindObservers() {
        locationPasswordsViewModel.locationPasswords.observe(this, Observer {
            adapter.locationPasswords = it ?: return@Observer
        })
        locationPasswordsViewModel.error.observe(this, Observer {
            if (!it.isNullOrBlank()) {
                displayErrorToast(message = it)
            }
        })
    }

    override fun handleOnClick(password: LocationPassword) {

    }

    override fun handleActiveTrigger(password: LocationPassword) {
        locationPasswordsViewModel.handleActiveTrigger(password = password)
    }

    // TODO
    /*override fun onRefresh() {
        bindObservers()
        dataBinding.srl.isRefreshing = false
    }*/
}