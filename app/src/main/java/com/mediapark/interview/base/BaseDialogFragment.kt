package com.mediapark.interview.base

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.annotation.NonNull
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mediapark.interview.R
import com.mediapark.interview.util.SystemUtil


open class BaseDialogFragment : DialogFragment(), MvpDialogView {
    var baseActivity: BaseActivity? = null
    var animStyle: Int? = null
    var backgroundColor: Int? = null
    var dialogStyle: Int? = null

    @NonNull
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog { // the content
        val root = ConstraintLayout(requireActivity().baseContext)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val dialog = if (dialogStyle == null) Dialog(requireContext()) else
            Dialog(requireContext(), dialogStyle!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)

        dialog.window?.apply {
            setBackgroundDrawableResource(if (backgroundColor == null) android.R.color.transparent else backgroundColor!!)
            setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            animStyle?.also {
                setWindowAnimations(it)
            }
            if (SystemUtil.compareSDKVersion(21)) {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
            }
        }
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            baseActivity = context
        }
    }

    override fun onDetach() {
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

    open fun setup(view: View) {}

    override fun invokeBack() {
        baseActivity?.onBackPressed()
    }

    override fun invokeExit() {
        dismiss()
    }

    override fun appContext(): Context? {
        return context
    }

    open fun showDialog(fragmentManager: FragmentManager, tag: String?) {
        try {

            if (!isShowing() && !isAdded) {
//                val transaction: FragmentTransaction = fragmentManager.beginTransaction()
//                val prevFragment: Fragment? = fragmentManager.findFragmentByTag(tag)
//                if (prevFragment != null) {
//                    transaction.remove(prevFragment)
//                }
//                transaction.addToBackStack(null)
//                show(transaction, tag)
                show(fragmentManager, tag)
            }
        }
        catch (e: IllegalStateException) { e.printStackTrace() }
    }

    open fun isShowing(): Boolean {
        return if (dialog == null) false else dialog!!.isShowing
    }

    override fun dismissDialog(tag: String?) {
        try {
            if (!isStateSaved)
                dismiss()
        }
        catch (e: Exception) { e.printStackTrace() }
    }
}