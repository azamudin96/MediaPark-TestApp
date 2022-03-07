package com.mediapark.interview.custom.listener

import android.view.View

interface OnItemClickListener {
    fun onItemClick(view: View, position: Int, item: Any? = null)
}