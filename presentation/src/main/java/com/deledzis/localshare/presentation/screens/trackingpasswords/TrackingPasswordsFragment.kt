package com.deledzis.localshare.presentation.screens.trackingpasswords

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
import com.deledzis.localshare.domain.model.entity.CloseAddTrackingPasswordAction
import com.deledzis.localshare.domain.model.entity.CloseEditTrackingPasswordAction
import com.deledzis.localshare.domain.model.entity.ShowAddTrackingPasswordAction
import com.deledzis.localshare.domain.model.entity.ShowEditTrackingPasswordAction
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.databinding.FragmentTrackingPasswordsBinding
import com.deledzis.localshare.presentation.screens.main.MainActivityViewModel
import com.deledzis.localshare.presentation.screens.main.UserViewModel
import com.deledzis.localshare.presentation.screens.trackingpasswords.add.AddTrackingPasswordFragment
import com.deledzis.localshare.presentation.screens.trackingpasswords.edit.EditTrackingPasswordFragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TrackingPasswordsFragment @Inject constructor() : BaseFragment<TrackingPasswordsViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var dataBinding: FragmentTrackingPasswordsBinding

    @Inject
    lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var trackingPasswordsViewModel: TrackingPasswordsViewModel

    @Inject
    lateinit var adapter: TrackingPasswordsAdapter

    private var addTrackingPasswordsFragment: AddTrackingPasswordFragment? = null
    private var editTrackingPasswordsFragment: EditTrackingPasswordFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackingPasswordsViewModel = injectViewModel(viewModelFactory)
        trackingPasswordsViewModel.fetchData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_tracking_passwords,
            container,
            false
        )
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = trackingPasswordsViewModel
        dataBinding.handler = trackingPasswordsViewModel

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(dataBinding) {
            rvLocationPasswords.layoutManager = LinearLayoutManager(activity)
            rvLocationPasswords.adapter = adapter
            layoutEmptyState.handler = trackingPasswordsViewModel
            srl.setOnRefreshListener(this@TrackingPasswordsFragment)
        }

        adapter.listener = trackingPasswordsViewModel
    }

    override fun onResume() {
        super.onResume()
        mainActivityViewModel.setInTopLevelFragment()
    }

    override fun bindObservers() {
        trackingPasswordsViewModel.trackingPasswords.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            adapter.trackingPasswords = it ?: return@Observer
            adapter.notifyDataSetChanged()
        })
        trackingPasswordsViewModel.trackingPasswordUpdate.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            adapter.notifyItemChanged(it.second)
        })
        trackingPasswordsViewModel.error.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            if (!it.isNullOrBlank()) {
//                displayErrorToast(message = it)
            }
        })
        trackingPasswordsViewModel.trackingPasswordSelection.observe(this, Observer {
            if (it != null && findNavController().currentDestination?.id == R.id.trackingPasswordsFragment) {
                val action = TrackingPasswordsFragmentDirections
                    .actionTrackingPasswordsFragmentToTrackingPasswordFragment(it)
                findNavController().navigate(action)
            }
        })
        trackingPasswordsViewModel.action.observe(this, Observer {
            it ?: return@Observer
            if (it is ShowAddTrackingPasswordAction) {
                if (addTrackingPasswordsFragment == null) {
                    addTrackingPasswordsFragment =
                        AddTrackingPasswordFragment(viewModelFactory, trackingPasswordsViewModel)
                    addTrackingPasswordsFragment!!.show(
                        parentFragmentManager,
                        "addBottomSheetDialogFragment"
                    )
                }
            }
            if (it is CloseAddTrackingPasswordAction) {
                addTrackingPasswordsFragment = null
            }
            if (it is ShowEditTrackingPasswordAction) {
                if (editTrackingPasswordsFragment == null) {
                    editTrackingPasswordsFragment =
                        EditTrackingPasswordFragment(
                            viewModelFactory = viewModelFactory,
                            trackingPasswordsViewModel = trackingPasswordsViewModel,
                            password = it.password,
                            position = it.position
                        )
                    editTrackingPasswordsFragment!!.show(
                        parentFragmentManager,
                        "editBottomSheetDialogFragment"
                    )
                }
            }
            if (it is CloseEditTrackingPasswordAction) {
                editTrackingPasswordsFragment = null
            }
        })
        userViewModel.user.observe(this, Observer {
            if (it == null) {
                findNavController().navigate(R.id.signInFragment)
            }
        })
    }

    override fun onRefresh() {
        trackingPasswordsViewModel.refreshTrackingPasswords()
    }
}