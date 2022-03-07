package com.mediapark.interview.base

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mediapark.interview.AppApplication
import com.mediapark.interview.R
import com.mediapark.interview.general.dialog.ProgressDialog
import com.mediapark.interview.helper.LoadingDialog
import com.mediapark.interview.util.AppPermissionUtil
import com.mediapark.interview.util.SystemUtil

open class BaseActivity : AppCompatActivity(), MvpView , BaseFragment.Callback{

    var attachedFragment: BaseFragment? = null
    private var loadingDialog : LoadingDialog? = null
    private var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullscreen(true)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    override fun onResume() {
        super.onResume()
        setApplicationActivity()
    }

    override fun showLoading(message: String?) {
        try {
            hideLoading()
            progressDialog = ProgressDialog.newInstance().apply {
                setMessage(message)
                showDialog(supportFragmentManager)
            }
        }
        catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun hideLoading() {
        try {
            progressDialog?.apply {
                dismissDialog()
            }
            supportFragmentManager.executePendingTransactions()
        }
        catch (e: Exception) { e.printStackTrace() }
    }

    override fun onError(message: String, title: String, callback: DialogInterface.OnClickListener?) {
        if (!TextUtils.isEmpty(message)) {
            val builder = MaterialAlertDialogBuilder(this)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, callback)
            if (title.isNotEmpty()) {
                builder.setTitle(title)
            }
            builder.show()
        }
    }

    override fun showConfirm(
        message: String,
        title: String,
        yesCallback: DialogInterface.OnClickListener,
        noCallback: DialogInterface.OnClickListener,
        cancelCallback: DialogInterface.OnClickListener): AlertDialog? {

        return AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.yes, yesCallback)
            .setNegativeButton(android.R.string.no, noCallback)
            .setOnCancelListener {
                cancelCallback.onClick(it, android.R.string.cancel)
            }
            .show()
    }

    override fun showMessage(message: String) {
        if (!TextUtils.isEmpty(message))
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun showBottomMessage(message: String) {
        Log.d(this.javaClass.name , "To be implemented")
    }

    override fun hideKeyboard(view: View?) {
        val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var v: View? = view ?: currentFocus
        if (v == null) {
            v = View(this)
        }
        imm.hideSoftInputFromWindow(v.windowToken, 0)
    }

    override fun onFragmentAttached(fragment: BaseFragment?) {
        attachedFragment = fragment
    }

    override fun onFragmentDetached(tag: String?) {
        attachedFragment?.apply {
            if (this::class.java.simpleName.equals(tag))
                attachedFragment = null
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun invokeBack() {
        onBackPressed()
    }

    override fun invokeExit() {
        finish()
    }

    override fun appContext(): Context? {
        return this
    }

    fun requestPermission(permissions: Array<String>,
                          launcher: ActivityResultLauncher<Array<out String>>? = null,
                          rationalMessage: String = ""): Boolean {
        if (!SystemUtil.compareSDKVersion(Build.VERSION_CODES.M)) return true
        return when {
            AppPermissionUtil.checkPermissions(this, permissions) -> true
            shouldShowRequestPermissionRationale(
                AppPermissionUtil.getNonGrantedPermissions(this, permissions)[0]
            ) && rationalMessage.isNotEmpty() -> {
                val cancelAction = DialogInterface.OnClickListener { dialog: DialogInterface, _: Int ->
                    dialog.dismiss()
                }
                showConfirm(
                    rationalMessage,
                    getString(R.string.request_permissions), { dialog: DialogInterface, _: Int ->
                        dialog.dismiss()
                        AppPermissionUtil.requestPermission(this, permissions, launcher)
                    },
                    cancelAction,
                    cancelAction
                )
                false
            }
            else -> {
                AppPermissionUtil.requestPermission(this, permissions, launcher)
                false
            }
        }
    }

    open fun getPresenter() : Any? = null

    open fun setup() {}

    private fun setApplicationActivity() {
        val application = applicationContext as? AppApplication
        application?.apply {
            currentActivity = this@BaseActivity
        }
    }

    private fun setFullscreen(darkIcon: Boolean = true) {
        var stat = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && darkIcon) {
            stat = stat or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.decorView.systemUiVisibility = stat
        window.statusBarColor = ContextCompat.getColor(baseContext, android.R.color.transparent)
    }


    fun checkPermission(){
        requestPermission(AppPermissionUtil.ALL_PERMISSIONS,allPermissionLauncher,"")
    }

    fun checkLocation(){
        val cancelAction = DialogInterface.OnClickListener { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }
        showConfirm(
            getString(R.string.request_permissions),
            getString(R.string.request_permissions), { dialog: DialogInterface, _: Int ->
                dialog.dismiss()
                requestPermission(AppPermissionUtil.LOCATION_PERMISSIONS,locationPermissionLauncher,"")
            },
            cancelAction,
            cancelAction
        )
    }

    private val allPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle Permission granted/rejected
        var granted = true
        permissions.entries.forEach {
            if (!it.value) granted = false
        }
    }

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle Permission granted/rejected
        var granted = true
        permissions.entries.forEach {
            if (!it.value) granted = false
        }

        if (granted){
            requestPermission(AppPermissionUtil.BACKGROUND_LOCATION_PERMISSION,backPermissionLauncher,getString(R.string.request_permissions))
        }
    }

    private val backPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        // Handle Permission granted/rejected
        var granted = true
        permissions.entries.forEach {
            if (!it.value) granted = false
        }
    }

    fun checkSinglePermission(permissionName: String): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            val granted =
                ContextCompat.checkSelfPermission(this, permissionName)
            granted == PackageManager.PERMISSION_GRANTED
        } else {
            val granted =
                PermissionChecker.checkSelfPermission(this, permissionName)
            granted == PermissionChecker.PERMISSION_GRANTED
        }
    }

}