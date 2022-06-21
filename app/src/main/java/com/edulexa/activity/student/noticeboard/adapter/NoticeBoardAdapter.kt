package com.edulexa.activity.student.noticeboard.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.attendance.adapter.AttendanceStudentMonthAdapter
import com.edulexa.activity.student.noticeboard.model.NoticeboardDatum
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentAttedndanceMonthBinding
import com.edulexa.databinding.ItemStudentNoticeBoardBinding
import com.edulexa.support.Utils
import java.text.SimpleDateFormat

class NoticeBoardAdapter(context: Activity,list : List<NoticeboardDatum?>?) :
    RecyclerView.Adapter<NoticeBoardAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<NoticeboardDatum?>? = null
    var binding : ItemStudentNoticeBoardBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentNoticeBoardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            Utils.setImageUsingGlide(context, Constants.BASE_URL_STUDENT+list!!.get(position)!!.getDocument(),binding!!.ivStudentNoticeBoardData)
            binding!!.tvNoticeMessage.text = list!!.get(position)!!.getMessage()
            binding!!.tvDate.text = getFormattedDate(list!!.get(position)!!.getPublishDate()!!)
            when(position % 3){
                0 -> {
                    binding!!.noticeBoardTopLay.setBackgroundColor(ContextCompat.getColor(context!!,R.color.notice_board_bg_1))
                }
                1 -> {
                    binding!!.noticeBoardTopLay.setBackgroundColor(ContextCompat.getColor(context!!,R.color.notice_board_bg_2))
                }
                2 -> {
                    binding!!.noticeBoardTopLay.setBackgroundColor(ContextCompat.getColor(context!!,R.color.notice_board_bg_3))
                }
            }
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
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentNoticeBoardBinding) : RecyclerView.ViewHolder(binding.root)
}