package com.mediapark.interview.base

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View

interface MvpView {
    fun showLoading(message: String? = null)
    fun hideLoading()
    fun onError(message: String, title: String = "", callback: DialogInterface.OnClickListener? = null)
    fun showConfirm(
        message: String,
        title: String,
        yesCallback: DialogInterface.OnClickListener,
        noCallback: DialogInterface.OnClickListener,
        cancelCallback: DialogInterface.OnClickListener = noCallback) : AlertDialog?
    fun showMessage(message: String)
    fun showBottomMessage(message: String)
    fun hideKeyboard(view : View? = null)
    fun appContext(): Context?
    fun invokeBack()
    fun invokeExit()
}