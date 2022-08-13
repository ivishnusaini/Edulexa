package com.edulexa.activity.staff.student_profile.model.class_list

import android.widget.RelativeLayout
import android.widget.TextView

class CustomModel {
    var relativeLayout : RelativeLayout? = null
    var textView : TextView? = null

    @JvmName("getRelativeLayout1")
    fun getRelativeLayout(): RelativeLayout? {
        return relativeLayout
    }

    @JvmName("setRelativeLayout1")
    fun setRelativeLayout(relativeLayout: RelativeLayout) {
        this.relativeLayout = relativeLayout
    }

    @JvmName("getTextView1")
    fun getTextView(): TextView? {
        return textView
    }

    @JvmName("setTextView1")
    fun setTextView(textView: TextView) {
        this.textView = textView
    }
}