package com.mediapark.interview.util

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import androidx.core.content.ContextCompat

object UIUtil {

    fun setViewClickListener(listener: View.OnClickListener, vararg views: View) {
        for (view in views) {
            view.setOnClickListener(listener)
        }
    }

    fun visibility(boolean: Boolean, hide: Boolean = true): Int {
        return if (boolean) View.VISIBLE else (if (hide) View.GONE else View.INVISIBLE)
    }

    fun colorToColorStateList(context: Context, colorResId: Int): ColorStateList {
        return ColorStateList.valueOf(
            ContextCompat.getColor(context, colorResId)
        )
    }

    interface KeyboardVisibilityListener {
        fun onKeyboardVisibilityChanged(keyboardVisible: Boolean)
    }
}