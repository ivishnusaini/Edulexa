package com.edulexa.activity.student.online_exam.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.databinding.ItemStudentOnlineQAnsSubjectiveImageBinding

class OnlineExamSubjectiveImageAdapter(context: Activity, list : List<Uri?>?) :
    RecyclerView.Adapter<OnlineExamSubjectiveImageAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<Uri?>? = null
    var binding : ItemStudentOnlineQAnsSubjectiveImageBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentOnlineQAnsSubjectiveImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            if (position == 0){
                binding!!.ivOnlineQAnsImage.setImageURI(null)
                binding!!.ivOnlineQAnsImage.setBackgroundResource(R.drawable.ic_add_image)
                binding!!.ivCross.visibility = View.GONE
            }else{
                binding!!.ivOnlineQAnsImage.setBackgroundResource(0)
                binding!!.ivOnlineQAnsImage.setImageURI(list!!.get(position))
                binding!!.ivCross.visibility = View.VISIBLE
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentOnlineQAnsSubjectiveImageBinding) : RecyclerView.ViewHolder(binding.root)
}