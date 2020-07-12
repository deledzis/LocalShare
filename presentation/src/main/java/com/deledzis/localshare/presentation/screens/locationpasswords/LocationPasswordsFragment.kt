package com.deledzis.localshare.presentation.screens.locationpasswords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentLocationPasswordsBinding
import javax.inject.Inject

class LocationPasswordsFragment : BaseFragment<LocationPasswordsViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

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
        dataBinding.handler = locationPasswordsViewModel

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(dataBinding) {
            rvLocationPasswords.layoutManager = LinearLayoutManager(activity)
            rvLocationPasswords.adapter = adapter
            layoutEmptyState.handler = locationPasswordsViewModel
            srl.setOnRefreshListener(this@LocationPasswordsFragment)
        }

        adapter.listener = locationPasswordsViewModel
    }

    override fun bindObservers() {
        locationPasswordsViewModel.locationPasswords.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            adapter.locationPasswords = it ?: return@Observer
            adapter.notifyDataSetChanged()
        })
        locationPasswordsViewModel.locationPasswordUpdate.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            adapter.notifyItemChanged(it.second)
        })
        locationPasswordsViewModel.error.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            if (!it.isNullOrBlank()) {
//                displayErrorToast(message = it)
            }
        })
        locationPasswordsViewModel.userError.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            if (it) {
                activity.toSignIn()
            }
        })
    }

    override fun onRefresh() {
        locationPasswordsViewModel.refreshLocationPasswords()
    }
}