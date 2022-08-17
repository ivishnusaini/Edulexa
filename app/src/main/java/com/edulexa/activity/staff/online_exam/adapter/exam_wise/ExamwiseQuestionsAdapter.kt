package com.edulexa.activity.staff.online_exam.adapter.exam_wise

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.activity.*
import com.edulexa.activity.staff.online_exam.model.examwise_questions.QuestionExamWise
import com.edulexa.activity.staff.online_exam.model.list.ExamOnlineExamStaff
import com.edulexa.activity.student.report_card.activity.ReportCardDetailActivity
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffExamWiseQuestionBinding
import com.edulexa.databinding.ItemStaffOnlineExamListBinding
import com.edulexa.support.Utils
import com.google.gson.Gson

class ExamwiseQuestionsAdapter(context: Activity, list: List<QuestionExamWise?>?,examId : String,examType : String) :
    RecyclerView.Adapter<ExamwiseQuestionsAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<QuestionExamWise?>? = null
    var binding: ItemStaffExamWiseQuestionBinding? = null
    var examId : String = ""
    var examType : String = ""

    init {
        this.context = context
        this.list = list
        this.examId = examId
        this.examType = examType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffExamWiseQuestionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        try {
            if (list!![position]!!.getQuestion() == "")
                binding!!.tvTitle.visibility = View.GONE
            else binding!!.tvTitle.visibility = View.VISIBLE
            if (list!![position]!!.getImgUrl() == "")
                binding!!.ivImage.visibility = View.GONE
            else binding!!.ivImage.visibility = View.VISIBLE
            binding!!.tvTitle.text = list!![position]!!.getQuestion()
            Utils.setImageUsingGlide(context,list!![position]!!.getImgUrl(),binding!!.ivImage)


            binding!!.ivEdit.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(Constants.StaffOnlineExam.EXAM_MODEL,Gson().toJson(list!![position]))
                bundle.putString(Constants.StaffOnlineExam.EXAM_ID,examId)
                bundle.putString(Constants.StaffOnlineExam.QUESTION_ID,list!![position]!!.getId())
                bundle.putString(Constants.StaffOnlineExam.TYPE,"edit")
                bundle.putString(Constants.StaffOnlineExam.EXAM_TYPE,examType)
                context!!.startActivity(Intent(context, ExamwiseQuestionAddActivity::class.java).putExtras(bundle))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffExamWiseQuestionBinding) :
        RecyclerView.ViewHolder(binding.root)
}