package com.mediapark.interview.main

import android.content.Intent
import com.mediapark.interview.R
import com.mediapark.interview.base.BasePresenter

class MainActivityPresenter<V: MainActivityMvpView> : BasePresenter<V>(), MainActivityInteractor {

    private var notificationBadge = 0
    private var fragmentListener: MainActivityFragmentListener? = null
    private lateinit var intent: Intent

    fun initContent(intent: Intent) {
        this.intent = intent
    }

    override fun onHasScroll(boolean: Boolean) {
        mvpView?.displayHasScroll(boolean)
    }

    override fun navigateTabFragment(id: Int) {
//        when(id) {
//            R.id.navi_main,
//            R.id.navi_discover,
//            R.id.navi_finance_coach,
//            R.id.navi_home -> {
//                mvpView?.selectBottomNavigation(id)
//            }
//        }
    }

    override fun getNotificationBadge(): Int {
        return notificationBadge
    }

    override fun setFragmentListener(listener: MainActivityFragmentListener?) {
        fragmentListener = listener
    }

    override fun invokeInbox() {
        mvpView?.invokeInbox()
    }

    override fun invokeProfile() {
        mvpView?.invokeProfile()
    }

//    fun onTabSelected(id: Int): Boolean {
//        return when(id) {
//            R.id.navi_discover,
//            R.id.navi_home,
//            R.id.navi_main -> {
//                mvpView?.displayFragment(id)
//                true
//            }
//            else -> {
//                mvpView?.showMessage(getString(R.string.msg_coming_soon))
//                false
//            }
//        }
//    }
}