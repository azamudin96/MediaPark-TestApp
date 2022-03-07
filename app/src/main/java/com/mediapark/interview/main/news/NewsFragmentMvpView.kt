package com.mediapark.interview.main.news

import com.mediapark.interview.base.MvpView
import com.mediapark.interview.modal.ui.RecyclerItem

interface NewsFragmentMvpView :MvpView {
    fun displayListing(data: MutableList<RecyclerItem>)
    fun invokeWebView(url: String, title: String)
}