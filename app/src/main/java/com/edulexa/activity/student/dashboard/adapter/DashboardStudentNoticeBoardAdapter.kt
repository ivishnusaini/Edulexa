package com.edulexa.activity.student.dashboard.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.activity.student.dashboard.model.DatumNotification
import com.edulexa.databinding.ItemStudentDashboardNoticeBoardBinding
import java.sql.Date
import java.text.SimpleDateFormat

class DashboardStudentNoticeBoardAdapter(context: Activity,list : List<DatumNotification?>?) : RecyclerView.Adapter<DashboardStudentNoticeBoardAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumNotification?>? = null
    var binding : ItemStudentDashboardNoticeBoardBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentDashboardNoticeBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            binding!!.tvNoticeMessage.text = list!!.get(position)!!.getMessage()
            binding!!.tvDate.text = getFormattedDate(list!!.get(position)!!.getPublishDate()!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getFormattedDate(dateStr : String) : String{
        try {
            if (!dateStr.isEmpty()) {
                var spf = SimpleDateFormat("yyyy-mm-dd")
                val newDate = spf.parse(dateStr)
                spf = SimpleDateFormat("dd MM yyyy")
                val date = spf.format(newDate!!)
                return date
            }else return dateStr
        }catch (e : Exception){
            e.printStackTrace()
            return dateStr
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class ViewHolder(binding: ItemStudentDashboardNoticeBoardBinding) : RecyclerView.ViewHolder(binding.root)
}