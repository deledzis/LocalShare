package com.deledzis.localshare.ui.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.deledzis.localshare.App
import com.deledzis.localshare.R
import com.deledzis.localshare.base.BaseActivity
import com.deledzis.localshare.databinding.ActivityMainBinding
import com.deledzis.localshare.ui.sign_in.SignInFragment
import com.deledzis.localshare.util.SIGN_IN_FRAGMENT_TAG
import com.deledzis.localshare.util.extensions.viewModelFactory

class MainActivity : BaseActivity() {

    private lateinit var databinding: ActivityMainBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory {
                MainViewModel()
            }
        )[MainViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)

        if (!isTaskRoot) {
            finish()
            return
        }

        App.injector.inject(this)

        databinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        databinding.lifecycleOwner = this

        toSignIn()
    }

    private fun toSignIn() {
        setFragment(
            SignInFragment.newInstance(),
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
