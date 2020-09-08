package com.deledzis.localshare.presentation.screens.locationpasswords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.deledzis.localshare.domain.model.entity.CloseAddLocationPasswordAction
import com.deledzis.localshare.domain.model.entity.CloseEditLocationPasswordAction
import com.deledzis.localshare.domain.model.entity.ShowAddLocationPasswordAction
import com.deledzis.localshare.domain.model.entity.ShowEditLocationPasswordAction
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentLocationPasswordsBinding
import com.deledzis.localshare.presentation.screens.locationpasswords.add.AddLocationPasswordFragment
import com.deledzis.localshare.presentation.screens.locationpasswords.edit.EditLocationPasswordFragment
import com.deledzis.localshare.presentation.screens.main.UserViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationPasswordsFragment @Inject constructor() : BaseFragment<LocationPasswordsViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var dataBinding: FragmentLocationPasswordsBinding

    @Inject
    lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var locationPasswordsViewModel: LocationPasswordsViewModel

    @Inject
    lateinit var adapter: LocationPasswordsAdapter

    private var addLocationPasswordFragment: AddLocationPasswordFragment? = null
    private var editLocationPasswordFragment: EditLocationPasswordFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationPasswordsViewModel = injectViewModel(viewModelFactory)
        locationPasswordsViewModel.fetchData()
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
            /*if (!it.isNullOrBlank()) {
                displayErrorToast(message = it)
            }*/
        })
        locationPasswordsViewModel.action.observe(this, Observer {
            it ?: return@Observer
            if (it is ShowAddLocationPasswordAction) {
                if (addLocationPasswordFragment == null) {
                    addLocationPasswordFragment =
                        AddLocationPasswordFragment(viewModelFactory, locationPasswordsViewModel)
                    addLocationPasswordFragment!!.show(
                        parentFragmentManager,
                        "addBottomSheetDialogFragment"
                    )
                }
            }
            if (it is CloseAddLocationPasswordAction) {
                addLocationPasswordFragment = null
            }
            if (it is ShowEditLocationPasswordAction) {
                if (editLocationPasswordFragment == null) {
                    editLocationPasswordFragment =
                        EditLocationPasswordFragment(
                            viewModelFactory = viewModelFactory,
                            locationPasswordsViewModel = locationPasswordsViewModel,
                            password = it.password,
                            position = it.position
                        )
                    editLocationPasswordFragment!!.show(
                        parentFragmentManager,
                        "editBottomSheetDialogFragment"
                    )
                }
            }
            if (it is CloseEditLocationPasswordAction) {
                editLocationPasswordFragment = null
            }
        })
        userViewModel.user.observe(this, Observer {
            if (it == null) {
                findNavController().navigate(R.id.signInFragment)
            }
        })
    }

    override fun onRefresh() {
        locationPasswordsViewModel.refreshLocationPasswords()
    }
}