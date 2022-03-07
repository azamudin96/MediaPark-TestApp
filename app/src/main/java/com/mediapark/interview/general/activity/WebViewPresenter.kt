package com.mediapark.interview.general.activity

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.mediapark.interview.base.BasePresenter

class WebViewPresenter<V: WebViewMvpView> constructor() : BasePresenter<V>() {

    fun initContent(url: String) {
        mvpView?.displayLoading(true)
        mvpView?.loadContent(url)
    }

    val webChromeClient = object: WebChromeClient() {}
    val webViewClient = object: WebViewClient() {
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            mvpView?.displayLoading(false)
        }
    }
}