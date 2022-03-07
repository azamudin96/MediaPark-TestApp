package com.mediapark.interview.modal.ui

import android.text.Spannable
import java.io.Serializable

class CheckListImageText(
    val resId: Int = -1,
    val title: String = "",
    val id: Int = -1,
    var subtitle: String = "",
    var description: Spannable = Spannable.Factory.getInstance().newSpannable(""),
    var name: String = "",
    var name2: String = "",
    var badge: Int = 0,
    var type: Int = 0,
    var tag: String = ""
): Serializable {

//    constructor(resId: Int = -1,
//                title: String = "",
//                subtitle: String = "",
//                id: Int = -1): this(resId, title, id) {
//        this.subtitle = subtitle
//        this.badge = badge
//    }
//
//    constructor(resId: Int = -1,
//                title: String = "",
//                description: Spannable = Spannable.Factory.getInstance().newSpannable(""),
//                id: Int = -1): this(resId, title, id) {
//        this.description = description
//    }
//
//    constructor(resId: Int = -1,
//                title: String = "",
//                subtitle: String = "",
//                name: String = "",
//                id: Int = -1): this(resId, title, id) {
//        this.subtitle = subtitle
//        this.name = name
//    }
}