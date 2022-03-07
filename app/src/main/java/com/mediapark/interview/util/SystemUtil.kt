package com.mediapark.interview.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.provider.Settings
import android.util.DisplayMetrics
import androidx.multidex.BuildConfig
import org.json.JSONObject
import java.io.File
import java.util.*


object SystemUtil {
    private val SDK_INT : Int = Build.VERSION.SDK_INT
    private val EMULATOR_FILE = arrayOf(
        // PIPES
        "/dev/socket/qemud",
        "/dev/qemu_pipe",
        // X86_FILES
        "ueventd.android_x86.rc",
        "x86.prop",
        "ueventd.ttVM_x86.rc",
        "init.ttVM_x86.rc",
        "fstab.ttVM_x86",
        "fstab.vbox86",
        "init.vbox86.rc",
        "ueventd.vbox86.rc",
        // ANDY_FILES
        "fstab.andy",
        "ueventd.andy.rc",
        // NOX_FILES
        "fstab.nox",
        "init.nox.rc",
        "ueventd.nox.rc"
    )

    fun invokeWebBrowser(context: Context, url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    fun invokeDialCall(context: Context, phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        context.startActivity(intent)
    }

    fun compareSDKVersion(target : Int) : Boolean {
        return SDK_INT >= target
    }

    fun getDisplaySize(activity: Activity): DisplayMetrics {
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        return metrics
    }

    fun getDeviceInfo(context: Context?) : JSONObject {
        val jsonInfo = JSONObject()
            .put("device_firmware", Build.DEVICE)
//            .put("device_id", UUID.randomUUID())
            .put("device_id", getDeviceId(context))
            .put("device_firmware_product", Build.PRODUCT)
            .put("device_sdk_version", SDK_INT.toString())
            .put("device_brand", Build.BRAND)
            .put("device_manufacturer", Build.MANUFACTURER)
            .put("device_host", Build.HOST)
            .put("device_model", Build.MODEL)
        return jsonInfo
    }

    @SuppressLint("HardwareIds")
    fun getDeviceId(context: Context?): String? {
        var mDeviceId: String? = ""
        try {
            context?.apply {
                mDeviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (mDeviceId == null) {
            mDeviceId = Build.SERIAL
        }
        return mDeviceId
    }

    fun getVersionName(context: Context): String {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
            BuildConfig.VERSION_NAME
        }
    }

    fun initStrictMode() {
        if (false && BuildConfig.DEBUG) {
            val policy = StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyFlashScreen()
//                .penaltyDeath()
                .build()
            StrictMode.setThreadPolicy(policy)

            val builder = StrictMode.VmPolicy.Builder()
            if (compareSDKVersion(Build.VERSION_CODES.JELLY_BEAN)) {
                builder.detectLeakedRegistrationObjects()
            }
            if (compareSDKVersion(Build.VERSION_CODES.JELLY_BEAN_MR2)) {
                builder.detectFileUriExposure()
            }
            builder.detectLeakedClosableObjects()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
//                .penaltyDeath()
            StrictMode.setVmPolicy(builder.build())
        }
        else {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

//    fun isRoot(context: Context): Boolean {
//        return NetverifySDK.isRooted(context) || RootBeer(context).isRooted
//    }

//    fun isEmulator(context: Context) : Boolean {
//        if (Constant.ALLOW_EMULATOR) return false
//        return checkEmulatorFilesConfig() ||
//                checkEmulatorBuildConfig() ||
//                EmulatorDetector.isEmulator(context) ||
//                RootBeer(context).isRootedWithBusyBoxCheck
//    }

    private fun checkEmulatorBuildConfig(): Boolean {
        return (Build.MANUFACTURER.contains("Genymotion")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.lowercase(Locale.getDefault()).contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.HARDWARE == "goldfish"
                || Build.HARDWARE == "vbox86"
                || Build.HARDWARE.lowercase(Locale.getDefault()).contains("nox")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.PRODUCT == "sdk"
                || Build.PRODUCT == "google_sdk"
                || Build.PRODUCT == "sdk_x86"
                || Build.PRODUCT == "vbox86p"
                || Build.PRODUCT == "emulator"
                || Build.PRODUCT == "simulator"
                || Build.PRODUCT == "sdk_gphone64_arm64"
                || Build.PRODUCT.lowercase(Locale.getDefault()).contains("nox")
                || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")))
    }

    private fun checkEmulatorFilesConfig():Boolean {
        for (fileName in EMULATOR_FILE) {
            val file = File(fileName)
            if (file.exists()) {
                return true
            }
        }
        return false
    }
}