package com.deledzis.localshare.base

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.deledzis.localshare.base.BaseFragment

abstract class RefreshableFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    open lateinit var srl: SwipeRefreshLayout

    override fun onRefresh() {
        bindObservers()
    }
}