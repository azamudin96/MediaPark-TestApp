package com.mediapark.interview

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleObserver
import androidx.multidex.MultiDexApplication
import com.mediapark.interview.base.BaseActivity
import com.mediapark.interview.util.SystemUtil

class AppApplication : MultiDexApplication(), LifecycleObserver {

    var isLoggedIn = false
    var isForeground = false
    var currentActivity: BaseActivity? = null
    private val handler: Handler = Handler(Looper.getMainLooper())
    private val idleHandler: Handler = Handler(Looper.getMainLooper())
    private var idleStartTime: Long? = null

    override fun onCreate() {
        super.onCreate()

        SystemUtil.initStrictMode()
    }
}