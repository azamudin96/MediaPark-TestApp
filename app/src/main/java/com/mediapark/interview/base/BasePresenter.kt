package com.mediapark.interview.base

import android.os.Handler
import com.mediapark.interview.AppApplication

open class BasePresenter<V: MvpView>: MvpPresenter<V> {

    companion object {
        const val EXTRA_ERROR_ALERT = "error_alert"
    }

    var mvpView : V? = null
    var handler: Handler? = null


    override fun onAttach(mvpView: V) {
        this.handler = Handler()
        this.mvpView = mvpView
    }

    override fun onDetach() {
        handler = null
        mvpView = null
    }

    fun getApplication() : AppApplication? {
        return (mvpView?.appContext()?.applicationContext as? AppApplication)
    }

    fun isViewAttached(): Boolean {
        return mvpView != null
    }

    fun getString(resId: Int): String {
        return mvpView?.appContext()?.getString(resId) ?: ""
    }

    open fun onBackAction() {
        mvpView?.invokeBack()
    }

    open fun onExitAction() {
        mvpView?.invokeExit()
    }

    open fun handleFieldResult(key: String, value: Any) {}
}