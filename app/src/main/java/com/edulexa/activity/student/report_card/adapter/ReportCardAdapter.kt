package com.edulexa.activity.student.report_card.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.online_exam.adapter.OnlineExamListAdapter
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.activity.student.report_card.model.DatumReportCardList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentOnlineExamListBinding
import com.edulexa.databinding.ItemStudentReportCardBinding

class ReportCardAdapter(context: Activity,list : List<DatumReportCardList?>?) : RecyclerView.Adapter<ReportCardAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumReportCardList?>?= null
    var binding : ItemStudentReportCardBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentReportCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            binding!!.tvReportCardExamName.text = list!!.get(position)!!.getExam()
            binding!!.tvReportCardExamStatus.text = list!!.get(position)!!.getResult()

            if (list!!.get(position)!!.getResult().equals("Pass"))
                binding!!.tvReportCardExamStatus.setTextColor(ContextCompat.getColor(context!!,R.color.green))
            else binding!!.tvReportCardExamStatus.setTextColor(ContextCompat.getColor(context!!,R.color.red))

            viewHolder.itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    val bundle = Bundle()
                    bundle.putString(Constants.ReportCardDetail.TITLE,list!!.get(position)!!.getExam())
                    bundle.putString(Constants.ReportCardDetail.RESULT_ID,list!!.get(position)!!.getId())
                    bundle.putString(Constants.ReportCardDetail.EXAM_GROUP_CLASS_BATCH_EXAM_ID,list!!.get(position)!!.getExamGroupClassBatchExamId())
                    bundle.putString(Constants.ReportCardDetail.DOWNLOAD_MARK_SHEET,list!!.get(position)!!.getDownloadMarksheet())
                    context!!.startActivity(Intent(context,ReportCardDetailActivity::class.java).putExtras(bundle))
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentReportCardBinding) : RecyclerView.ViewHolder(binding.root)
}