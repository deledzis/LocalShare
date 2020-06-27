package com.deledzis.localshare.ui.sign_in

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.view.inputmethod.InputMethodManager.HIDE_NOT_ALWAYS
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.App
import com.deledzis.localshare.R
import com.deledzis.localshare.base.BaseFragment
import com.deledzis.localshare.databinding.FragmentSignInBinding
import com.deledzis.localshare.util.extensions.viewModelFactory

class SignInFragment : BaseFragment() {
    private lateinit var dataBinding: FragmentSignInBinding

    private val viewModel: SignInViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory { SignInViewModel() }
        )[SignInViewModel::class.java]
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
        dataBinding.viewModel = viewModel

        // TODO
        /*dataBinding.tvSignInRecovery.setOnClickListener {
            activity.addFragment(
                RecoverFragment.newInstance(),
                RECOVER_FRAGMENT_TAG
            )
        }*/

        App.injector.inject(this)

        return dataBinding.root
    }

    override fun bindObservers() {
        viewModel.userData.observe(this, Observer {
            val inputManager = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken,
                HIDE_NOT_ALWAYS
            )
            if (it != null) {
                // TODO
            }
        })
        viewModel.employee.observe(this, Observer {
            if (it != null) {
                // TODO
            }
        })
        viewModel.error.observe(this, Observer {
            val inputManager = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity.currentFocus?.windowToken,
                HIDE_NOT_ALWAYS
            )
            Toast.makeText(activity, it ?: "Неизвестная ошибка", Toast.LENGTH_LONG).show()
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}