package com.mediapark.interview.main

import com.mediapark.interview.base.MvpView

interface MainActivityMvpView : MvpView {
    fun displayHasScroll(boolean: Boolean)
    fun displayFragment(int: Int)
    fun selectBottomNavigation(int: Int)
    fun invokeInbox()
    fun invokeProfile()
}