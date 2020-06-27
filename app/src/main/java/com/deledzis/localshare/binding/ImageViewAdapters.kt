package com.deledzis.localshare.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.deledzis.localshare.R

@BindingAdapter("src_circle")
fun loadCircleImage(view: ImageView, imageUrl: String?) {
    val circularProgressDrawable = CircularProgressDrawable(view.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()

    if (!imageUrl.isNullOrBlank()) {
        Glide.with(view.context)
            .load("${view.context.getString(R.string.media_base_url)}$imageUrl")
            .thumbnail(0.4f)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions().circleCrop())
            .into(view)
    } else {
        Glide.with(view.context)
            .load(R.drawable.unavailable_placeholder)
            .thumbnail(0.4f)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions().circleCrop())
            .into(view)
    }
}

@BindingAdapter("src")
fun loadImage(view: ImageView, imageUrl: String?) {
    val circularProgressDrawable = CircularProgressDrawable(view.context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.start()
    if (!imageUrl.isNullOrBlank()) {
        if (!(imageUrl.endsWith(".svg") || imageUrl.endsWith(".pdf"))) {
            Glide.with(view.context)
                .load("${view.context.getString(R.string.media_base_url)}$imageUrl")
                .thumbnail(0.4f)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .apply(RequestOptions().centerCrop())
                .into(view)
        }
    } else {
        Glide.with(view.context)
            .load(R.color.white_100)
            .thumbnail(0.4f)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .apply(RequestOptions().centerCrop())
            .into(view)
    }
}