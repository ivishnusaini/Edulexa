package com.edulexa.activity.staff.custom_lesson_plan.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.edulexa.R
import com.edulexa.activity.branch_code.model.Branch
import com.edulexa.activity.staff.custom_lesson_plan.model.add_lesson.DatumAddLesson
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData

class SubjectSpinnerAdapter(context: Activity, list : List<DatumAddLesson?>?) : BaseAdapter() {
    var context: Activity? = null
    var list : List<DatumAddLesson?>? = null
    var inflter: LayoutInflater? = null
    init {
        this.context = context
        this.list = list
        inflter = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return list!!.size
    }

    override fun getItem(i: Int): Any? {
        return null
    }

    override fun getItemId(i: Int): Long {
        return 0
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup): View {
        var view = view
        view = inflter!!.inflate(R.layout.item_spinner_branch_code, null)
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        tvName.text = list!![position]!!.getName()
        return view
    }
}