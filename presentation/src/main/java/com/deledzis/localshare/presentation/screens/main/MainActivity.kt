package com.deledzis.localshare.presentation.screens.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseActivity
import com.deledzis.localshare.presentation.base.UserViewModel
import com.deledzis.localshare.presentation.databinding.ActivityMainBinding
import com.deledzis.localshare.presentation.screens.locationpasswords.LocationPasswordsFragment
import com.deledzis.localshare.presentation.screens.signin.SignInFragment
import javax.inject.Inject

class MainActivity : BaseActivity<UserViewModel>() {

    private lateinit var dataBinding: ActivityMainBinding

    @Inject
    lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var signInFragment: SignInFragment

    @Inject
    lateinit var locationPasswordsFragment: LocationPasswordsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        if (!isTaskRoot) {
            finish()
            return
        }

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = userViewModel

        val bottomNavigationView = dataBinding.bottomNav

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}