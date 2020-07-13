package com.deledzis.localshare.infrastructure.binding

import android.graphics.PorterDuff
import android.os.Build
import android.widget.ProgressBar
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import com.deledzis.localshare.infrastructure.R
import com.deledzis.localshare.infrastructure.extensions.colorFrom
import com.deledzis.localshare.infrastructure.extensions.colorStateListFrom

@BindingAdapter("progress_tint")
fun setProgressBarTint(view: ProgressBar, @ColorRes colorId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        view.indeterminateTintList = view.context.colorStateListFrom(R.color.white_100)
    } else {
        view.progressDrawable.setColorFilter(view.context.colorFrom(R.color.white_100), PorterDuff.Mode.SRC_IN)
    }
}