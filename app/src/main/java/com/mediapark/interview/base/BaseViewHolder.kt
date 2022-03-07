package com.mediapark.interview.base

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mediapark.interview.modal.ui.RecyclerItem

open class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    open var currentPosition = 0
    open var currentItem: Any? = null
    open var context: Context = itemView.context

    open fun clear() {}

    open fun onBind(position: Int) {
        currentPosition = position
        clear()
    }

    open fun onBind(position: Int, item: Any?) {
        currentPosition = position
        currentItem = item
        clear()
    }

    open fun onBind(position: Int, item: RecyclerItem?) {
        this.onBind(position, item as Any?)
    }
}