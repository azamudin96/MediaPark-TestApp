package com.mediapark.interview.modal.ui

import com.mediapark.interview.util.ParseUtil


class RecyclerItem {
    companion object {
        const val TYPE_ITEM = 0
        const val TYPE_EMPTY = -1
        const val TYPE_LOADING = -2

        fun removeStateItem(array: MutableList<RecyclerItem>,
                            removeEmpty: Boolean = true,
                            removeLoad: Boolean = true) {
            array.removeAll {
                when(it.type) {
                    TYPE_EMPTY -> removeEmpty
                    TYPE_LOADING -> removeLoad
                    else -> false
                }
            }
        }

        inline fun <reified F> safeParse(item: Any?) : F? {
            return ParseUtil.safeParse<RecyclerItem>(item)?.getParsedContent<F>()
        }

        fun Empty(): RecyclerItem {
            return RecyclerItem(TYPE_EMPTY)
        }

        fun Loading(): RecyclerItem {
            return RecyclerItem(TYPE_LOADING)
        }
    }

    constructor(type: Int) {
        this.type = type
    }
    constructor(content: Any?) {
        this.type =
            TYPE_ITEM
        this.content = content
    }
    constructor(type: Int, content: Any?) {
        this.type = type
        this.content = content
    }

    var type =
        TYPE_ITEM
    var content: Any? = null

    inline fun <reified F> getParsedContent(): F? {
        return if (content != null && content is F) {
            content as F
        } else null
    }
}