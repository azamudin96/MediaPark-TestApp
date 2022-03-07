package com.mediapark.interview.main.search

import com.mediapark.interview.base.MvpView
import com.mediapark.interview.modal.ui.RecyclerItem

interface SearchFragmentMvpView :MvpView {
    fun displayListing(data: MutableList<RecyclerItem>)
    fun invokeWebView(url: String, title: String)
}