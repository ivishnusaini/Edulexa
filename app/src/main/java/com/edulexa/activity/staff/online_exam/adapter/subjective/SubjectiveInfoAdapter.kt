package com.edulexa.activity.staff.online_exam.adapter.subjective

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.activity.SubjectiveActivity
import com.edulexa.activity.staff.online_exam.activity.SubjectiveImagesActivity
import com.edulexa.activity.staff.online_exam.model.subjective.info.QuestionResult
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStaffSubmittedSubjectiveBinding
import com.google.gson.Gson

class SubjectiveInfoAdapter(context: Activity, list: List<QuestionResult?>?) :
    RecyclerView.Adapter<SubjectiveInfoAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<QuestionResult?>? = null
    var binding: ItemStaffSubmittedSubjectiveBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding =
            ItemStaffSubmittedSubjectiveBinding.inflate(
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
            binding!!.tvQuestion.loadData(
                list!![position]!!.getQuestion()!!,
                "text/html; charset=utf-8",
                "UTF-8"
            )
            binding!!.tvAns.text = context!!.getString(R.string.library_student_string_format,"Ans : ",list!![position]!!.getSelectOption())
            binding!!.etMark.hint =
                context!!.getString(R.string.library_student_string_format,"Max : ",list!![position]!!.getMark())

            if (list!![position]!!.getSubjectiveMark() != "")
                binding!!.etMark.setText(list!![position]!!.getSubjectiveMark())
            setEditTextMaxLength(list!![position]!!.getMark()!!.length,binding!!.etMark)

            if (list!![position]!!.getImagesArray()!!.isNotEmpty())
                binding!!.tvAttachedImages.visibility = View.VISIBLE
            else binding!!.tvAttachedImages.visibility = View.GONE

            binding!!.etMark.isEnabled = list!![position]!!.getOnlineexamStudentResultId() != ""

            binding!!.etMark.addTextChangedListener(object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString() != "") {
                        if (s.toString().toInt() > list!![position]!!.getMark()!!.toInt()) {
                            binding!!.etMark.setText(list!![position]!!.getMark())
                            binding!!.etMark.setSelection(list!![position]!!.getMark()!!.length)
                        }
                    }
                }
            })

            binding!!.tvAttachedImages.setOnClickListener {
                val bundle = Bundle()
                bundle.putString(Constants.StaffOnlineExam.IMAGES, Gson().toJson(list!![position]!!.getImagesArray()))
                context!!.startActivity(Intent(context, SubjectiveImagesActivity::class.java).putExtras(bundle))
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setEditTextMaxLength(length : Int,editText : EditText){
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = LengthFilter(length)
        editText.filters = filterArray
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStaffSubmittedSubjectiveBinding) :
        RecyclerView.ViewHolder(binding.root)
}