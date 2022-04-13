package com.edulexa.activity.student.examination.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.examination.activity.ExamDetailActivity

class ExamAdapter(context: Activity) :
    RecyclerView.Adapter<ExamAdapter.ViewHolder>() {
    var context: Activity? = null

    init {
        this.context = context
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView: View =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student_exam, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            if (position % 2 == 0){
                viewHolder.startTestLay!!.visibility = View.VISIBLE
                viewHolder.completedLay!!.visibility = View.GONE
            }else{
                viewHolder.startTestLay!!.visibility = View.GONE
                viewHolder.completedLay!!.visibility = View.VISIBLE
            }
            viewHolder.itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    context!!.startActivity(Intent(context, ExamDetailActivity::class.java))
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 10;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var startTestLay: LinearLayout? = null
        var completedLay: LinearLayout? = null

        init {
            startTestLay = itemView.findViewById(R.id.exam_student_start_test_lay)
            completedLay = itemView.findViewById(R.id.exam_student_completed_lay)
        }
    }
}