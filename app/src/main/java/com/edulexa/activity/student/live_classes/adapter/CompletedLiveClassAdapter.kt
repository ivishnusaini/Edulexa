package com.edulexa.activity.student.live_classes.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.homework.model.HomeworkData
import com.edulexa.activity.student.live_classes.model.DatumLiveClass
import com.edulexa.databinding.ItemLiveClassUpcomingBinding
import com.edulexa.databinding.ItemStudentDashboardHomeworkInnerListBinding
import com.edulexa.databinding.ItemStudentHomeworkDateBinding
import com.edulexa.support.Utils

class CompletedLiveClassAdapter(context: Activity, list : List<DatumLiveClass?>?) :
    RecyclerView.Adapter<CompletedLiveClassAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumLiveClass?>? = null
    var binding : ItemLiveClassUpcomingBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemLiveClassUpcomingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            binding!!.tvLiveClassTitle.text = list!!.get(position)!!.getTitle()
            binding!!.tvLiveClassDescription.text = list!!.get(position)!!.getDescs()
            binding!!.tvLiveClassFromDate.text = context!!.getString(R.string.library_student_string_format,"From date : ",list!!.get(position)!!.getFdate())
            binding!!.tvLiveClassToDate.text = context!!.getString(R.string.library_student_string_format,"To date : ",list!!.get(position)!!.getTdate())

            binding!!.tvLiveClassDescription.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    dialogDescription(list!!.get(position)!!.getDescs()!!)
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    private fun dialogDescription(description : String){
        try {
            val dialog = Dialog(context!!)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_live_class_description)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCanceledOnTouchOutside(false)
            val tvDescription = dialog.findViewById<TextView>(R.id.tv_dialog_live_class_description)
            tvDescription.text = description
            val tvOk: CardView = dialog.findViewById(R.id.tv_dialog_live_class_ok)
            tvOk.setOnClickListener { v: View? -> dialog.dismiss() }
            dialog.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    class ViewHolder(binding: ItemLiveClassUpcomingBinding) : RecyclerView.ViewHolder(binding.root)
}