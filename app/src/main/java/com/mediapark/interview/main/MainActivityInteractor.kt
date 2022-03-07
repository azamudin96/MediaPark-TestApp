package com.mediapark.interview.main


interface MainActivityInteractor {
    fun onHasScroll(boolean: Boolean)
    fun navigateTabFragment(id: Int)
    fun getNotificationBadge(): Int
    fun setFragmentListener(listener: MainActivityFragmentListener?)
    fun invokeInbox()
    fun invokeProfile()
}