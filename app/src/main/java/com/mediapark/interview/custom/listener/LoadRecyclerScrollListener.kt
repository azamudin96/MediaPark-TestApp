package com.mediapark.interview.custom.listener

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class LoadRecyclerScrollListener(): RecyclerView.OnScrollListener() {
    companion object {
        const val SCROLL_THRESHOLD = 5
        const val HIDE_THRESHOLD = 20
    }
    var layoutManager: LinearLayoutManager? = null

    /** Load Params  */
    private var lockLoadMore = true

    /** Show Hide Params  */
    private var scrolledDistance = 0
    private var controlsVisible = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        layoutManager ?: return

        val visibleItemCount        = recyclerView.childCount
        val totalItemCount: Int     = layoutManager!!.itemCount
        val firstVisibleItem: Int   = layoutManager!!.findFirstVisibleItemPosition()

        if (!lockLoadMore && (totalItemCount - visibleItemCount) <= (firstVisibleItem + SCROLL_THRESHOLD)) {
            onLoadMore()
        }

        if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
            onHide()
            controlsVisible = false
            scrolledDistance = 0
        }
        else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
            onShow()
            controlsVisible = true
            scrolledDistance = 0
        }
        if (controlsVisible && dy > 0 || !controlsVisible && dy < 0) {
            scrolledDistance += dy
        }
    }

    open fun setLock(lockLoadMore: Boolean) {
        this.lockLoadMore = lockLoadMore
    }

    open fun onLoadMore() {
        this.lockLoadMore = true
    }

    open fun onHide() {}

    open fun onShow() {}
}