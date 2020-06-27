package com.deledzis.localshare.util.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes

fun ImageView.withDrawable(@DrawableRes drawableRes: Int): ImageView {
    this.show()
    this.setImageDrawable(context.drawableCompatFrom(drawableRes))
    return this
}

fun ImageView.withDrawable(drawable: Drawable?): ImageView {
    this.show()
    this.setImageDrawable(drawable)
    return this
}

fun ImageView.setColor(@ColorRes colorId: Int): ImageView {
    this.setColorFilter(
        context.colorFrom(colorId),
        android.graphics.PorterDuff.Mode.SRC_IN
    )
    return this
}