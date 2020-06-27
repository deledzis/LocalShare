package com.deledzis.localshare.util.extensions

import android.widget.TextView
import androidx.annotation.StringRes
import com.deledzis.localshare.R

fun TextView.withText(@StringRes stringRes: Int, vararg args: Any): TextView {
    this.show()
    if (args.isEmpty()) {
        this.text = context.getString(stringRes)
    } else {
        this.text = context.getString(stringRes, *args)
    }
    return this
}

fun TextView.withText(text: String): TextView {
    this.show()
    this.text = text
    return this
}

fun TextView.setDefault(): TextView {
    this.setTextColor(context.colorFrom(R.color.text_100))
    return this
}