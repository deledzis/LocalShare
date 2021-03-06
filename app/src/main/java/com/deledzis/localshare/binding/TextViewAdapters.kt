package com.deledzis.localshare.binding

import android.os.Build
import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.deledzis.localshare.util.Base64ImageGetter
import com.deledzis.localshare.util.DateUtils
import com.deledzis.localshare.util.GlideImageGetter

@BindingAdapter("df_only_day_str")
fun setDateOnlyDayFormatted(view: TextView, value: String?) {
    value?.let {
        val date = DateUtils.getDate(it)
        view.text = DateUtils.DF_ONLY_DAY.format(date ?: DateUtils.getCurrentDate())
    }
}

@BindingAdapter("df_simple_str")
fun setDateSimpleFormatted(view: TextView, value: String?) {
    value?.let {
        val date = DateUtils.getDate(it, format = DateUtils.SIMPLE_FORMAT)
        view.text = DateUtils.OUTPUT_SIMPLE_FORMAT.format(date ?: DateUtils.getCurrentDate())
    }
}

@BindingAdapter("df_week_day_time_str")
fun setDateWeekDateTimeFormatted(view: TextView, value: String?) {
    value?.let {
        val date = DateUtils.getDate(date = it, format = DateUtils.ISO_24H_FORMAT)
        view.text = DateUtils.DF_WEEK_DAY_TIME.format(date ?: DateUtils.getCurrentDate())
    }
}

@BindingAdapter("df_week_day_time_str_nf")
fun setDateWeekDateTimeFormattedNotFullFormat(view: TextView, value: String?) {
    value?.let {
        val date = DateUtils.getDate(it, format = DateUtils.ISO_24H_FORMAT)
        view.text = DateUtils.DF_DAY_TIME_TIME.format(date ?: DateUtils.getCurrentDate())
    }
}

@Suppress("DEPRECATION")
@BindingAdapter("from_html")
fun setTextFromHtml(view: TextView, value: String?) {
    value?.let {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            view.text = Html.fromHtml(
                it,
                FROM_HTML_MODE_LEGACY,
                if (it.contains("data:image/png;base64"))
                    Base64ImageGetter(view.context, view)
                else
                    GlideImageGetter(view.context, view),
                null
            )
            view.movementMethod = LinkMovementMethod.getInstance()
        } else {
            view.text = Html.fromHtml(
                it,
                if (it.contains("data:image/png;base64"))
                    Base64ImageGetter(view.context, view)
                else
                    GlideImageGetter(view.context, view),
                null
            )
            view.movementMethod = LinkMovementMethod.getInstance()
        }
    }
}