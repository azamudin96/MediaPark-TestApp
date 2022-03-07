package com.mediapark.interview.general.activity

import com.mediapark.interview.base.MvpView

interface WebViewMvpView : MvpView {
    fun loadContent(url: String)
    fun displayLoading(shown: Boolean)
}