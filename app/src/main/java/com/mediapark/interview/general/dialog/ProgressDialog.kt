package com.mediapark.interview.general.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.mediapark.interview.base.BaseDialogFragment
import com.mediapark.interview.databinding.DialogProgressBinding

class ProgressDialog : BaseDialogFragment() {

    companion object {
        fun newInstance(): ProgressDialog {
            val fragment = ProgressDialog()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val TAG: String = ProgressDialog::class.java.simpleName
    private var _binding: DialogProgressBinding? = null
    private val binding get() = _binding!!
    private var message: String? = null

    var animation: AnimatedVectorDrawableCompat? = null

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DialogProgressBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setup(view: View) {
        super.setup(view)

        displayMessage()
    }

    fun showDialog(fragmentManager: FragmentManager) {
//        fragmentManager.executePendingTransactions()
        super.showDialog(fragmentManager, TAG)
    }

    fun dismissDialog() {
        super.dismissDialog(TAG);
    }

    fun setMessage(message: String? = null) {
        this.message = message
        displayMessage()
    }

    private fun displayMessage() {
        view ?: return
    }
}