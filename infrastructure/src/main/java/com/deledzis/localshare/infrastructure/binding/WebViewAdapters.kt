package com.deledzis.localshare.infrastructure.binding

import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.databinding.BindingAdapter

@Suppress("DEPRECATION")
@BindingAdapter("web_view_content_html")
fun loadWebViewFromHtml(view: WebView, value: String?) {
    value?.let {
        val settings: WebSettings = view.settings
        settings.minimumFontSize = 18
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = false
        settings.builtInZoomControls = true
        settings.displayZoomControls = false

        view.webChromeClient = WebChromeClient()
        view.loadDataWithBaseURL(
            null,
            it,
            "text/html",
            "UTF-8",
            null
        )
    }
}