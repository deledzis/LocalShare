package com.deledzis.localshare.presentation.screens.trackpasswords

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
import com.deledzis.localshare.infrastructure.extensions.injectViewModel
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseFragment
import com.deledzis.localshare.presentation.base.UserViewModel
import com.deledzis.localshare.presentation.databinding.FragmentTrackPasswordsBinding
import javax.inject.Inject

class TrackPasswordsFragment : BaseFragment<TrackPasswordsViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var dataBinding: FragmentTrackPasswordsBinding

    @Inject
    lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var trackPasswordsViewModel: TrackPasswordsViewModel

    @Inject
    lateinit var adapter: TrackPasswordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        trackPasswordsViewModel = injectViewModel(viewModelFactory)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_track_passwords,
            container,
            false
        )
        dataBinding.lifecycleOwner = viewLifecycleOwner
        dataBinding.viewModel = trackPasswordsViewModel
        dataBinding.handler = trackPasswordsViewModel

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(dataBinding) {
            rvLocationPasswords.layoutManager = LinearLayoutManager(activity)
            rvLocationPasswords.adapter = adapter
            layoutEmptyState.handler = trackPasswordsViewModel
            srl.setOnRefreshListener(this@TrackPasswordsFragment)
        }

        adapter.listener = trackPasswordsViewModel
    }

    override fun bindObservers() {
        trackPasswordsViewModel.fetchData()
        userViewModel.user.observe(this, Observer {
            if (it == null) {
                findNavController().navigate(R.id.signInFragment)
            }
        })
        trackPasswordsViewModel.locationPasswords.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            adapter.locationPasswords = it ?: return@Observer
            adapter.notifyDataSetChanged()
        })
        trackPasswordsViewModel.locationPasswordUpdate.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            adapter.notifyItemChanged(it.second)
        })
        trackPasswordsViewModel.error.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            if (!it.isNullOrBlank()) {
//                displayErrorToast(message = it)
            }
        })
        trackPasswordsViewModel.userError.observe(this, Observer {
            dataBinding.srl.isRefreshing = false
            if (it) {
                // TODO: logout user and navigate back to sign in
//                activity.toSignIn()
            }
        })
    }

    override fun onRefresh() {
        trackPasswordsViewModel.refreshLocationPasswords()
    }
}