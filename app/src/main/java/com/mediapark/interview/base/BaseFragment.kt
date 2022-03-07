package com.mediapark.interview.base

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.mediapark.interview.R

open class BaseFragment : Fragment() , MvpView {

    var baseActivity: BaseActivity? = null
    var isNavigationFragment = true
    var UIInitialized = false

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is BaseActivity) {
            baseActivity = context

            if (this.parentFragment == null) {
                baseActivity?.onFragmentAttached(this)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
        UIInitialized = true
    }

    override fun onDetach() {
        baseActivity?.onFragmentDetached(this::class.java.simpleName)
        baseActivity = null
        super.onDetach()
    }

    override fun showLoading(message: String?) {
        baseActivity?.showLoading(message)
    }

    override fun hideLoading() {
        baseActivity?.hideLoading()
    }

    override fun onError(
        message: String,
        title: String,
        callback: DialogInterface.OnClickListener?
    ) {
        baseActivity?.onError(message, title, callback)
    }

    override fun showConfirm(
        message: String,
        title: String,
        yesCallback: DialogInterface.OnClickListener,
        noCallback: DialogInterface.OnClickListener,
        cancelCallback: DialogInterface.OnClickListener
    ): AlertDialog? {
        return baseActivity?.showConfirm(message, title, yesCallback, noCallback, cancelCallback) ?: null
    }

    override fun showMessage(message: String) {
        baseActivity?.showMessage(message)
    }

    override fun showBottomMessage(message: String) {
        baseActivity?.showBottomMessage(message)
    }

    override fun hideKeyboard(view : View?) {
        baseActivity?.hideKeyboard(view)
    }

    open fun setup(view: View) {
        view.findViewById<View>(R.id.btn_default_back)?.setOnClickListener(onBackClickListener)
        view.findViewById<View>(R.id.btn_header_help)?.setOnClickListener(onHeaderHelpClickListener)
    }

    override fun appContext(): Context? {
        return context
    }

    override fun invokeBack() {
        baseActivity?.onBackPressed()
    }

    override fun invokeExit() {
        baseActivity?.finish()
    }

    private val onBackClickListener = View.OnClickListener {
        invokeBack()
    }
    private val onHeaderHelpClickListener = View.OnClickListener {
        Log.d(this.javaClass.name,"To be implemented")
    }

    interface Callback {
        fun onFragmentAttached(fragment: BaseFragment?)
        fun onFragmentDetached(tag: String?)
    }
}