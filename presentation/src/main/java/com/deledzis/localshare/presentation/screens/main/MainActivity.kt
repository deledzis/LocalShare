package com.deledzis.localshare.presentation.screens.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.deledzis.localshare.infrastructure.util.SIGN_IN_FRAGMENT_TAG
import com.deledzis.localshare.presentation.R
import com.deledzis.localshare.presentation.base.BaseActivity
import com.deledzis.localshare.presentation.screens.signin.SignInFragment
import com.deledzis.localshare.presentation.viewmodel.main.MainActivityViewModel
import javax.inject.Inject

class MainActivity : BaseActivity<MainActivityViewModel>() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var signInFragment: SignInFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (!isTaskRoot) {
            finish()
            return
        }

        toSignIn()
    }

    private fun toSignIn() {
        setFragment(
            signInFragment,
            SIGN_IN_FRAGMENT_TAG
        )
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