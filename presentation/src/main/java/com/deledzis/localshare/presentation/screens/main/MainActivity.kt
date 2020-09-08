package com.deledzis.localshare.presentation.screens.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseActivity
import com.deledzis.localshare.presentation.databinding.ActivityMainBinding
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainActivity : BaseActivity<UserViewModel>() {

    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    @Inject
    lateinit var userViewModel: UserViewModel

    @Inject
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        if (!isTaskRoot) {
            finish()
            return
        }

        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = mainActivityViewModel
        dataBinding.userViewModel = userViewModel


        navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment

        val bottomNavigationView = dataBinding.bottomNav
        NavigationUI.setupWithNavController(bottomNavigationView, navHostFragment.navController)
    }

    override fun onResume() {
        super.onResume()
        mainActivityViewModel.inTopLevelFragment.observe(this, Observer {
            if (it.first && it.second == null) {
                // hide toolbar
                setSupportActionBar(null)
            } else {
                val appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.locationPasswordsFragment,
                        R.id.trackingPasswordsFragment,
                        R.id.settingsFragment
                    )
                )
                // show toolbar
                setSupportActionBar(it.second)
                NavigationUI.setupWithNavController(
                    it.second!!,
                    navHostFragment.navController,
                    appBarConfiguration
                )
            }
        })
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