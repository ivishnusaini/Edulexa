package com.edulexa.activity.student.documents.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.documents.activity.DocumentDetailActivity
import com.edulexa.activity.student.documents.model.DocumentListModel
import com.edulexa.activity.student.homework.model.HomeworkData
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.activity.student.teacher_rating.model.DatumTeacherList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentDashboardHomeworkInnerListBinding
import com.edulexa.databinding.ItemStudentDocumentBinding
import com.edulexa.databinding.ItemStudentHomeworkDateBinding
import com.edulexa.databinding.ItemStudentTeacherListBinding
import com.edulexa.support.Utils

class DocumentListAdapter(context: Activity, list : List<DocumentListModel?>?) :
    RecyclerView.Adapter<DocumentListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DocumentListModel?>? = null
    var binding : ItemStudentDocumentBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentDocumentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            binding!!.tvDocumentName.text = list!!.get(position)!!.getFolderName()

            viewHolder.itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    val bundle = Bundle()
                    bundle.putString(Constants.StudentDocument.TITLE,list!!.get(position)!!.getFolderName())
                    bundle.putString(Constants.StudentDocument.FOLDER_ID,list!!.get(position)!!.getId())
                    context!!.startActivity(Intent(context, DocumentDetailActivity::class.java).putExtras(bundle))
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentDocumentBinding) : RecyclerView.ViewHolder(binding.root)
}