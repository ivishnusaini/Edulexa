package com.edulexa.activity.student.fee.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R

class FeeListAdapter(context: Activity) :
    RecyclerView.Adapter<FeeListAdapter.ViewHolder>() {
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
                .inflate(R.layout.item_student_fee_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            viewHolder.ivExpandLessMore!!.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    if (viewHolder.feeDescriptionLay!!.getVisibility() == View.GONE) {
                        viewHolder.feeDescriptionLay!!.setVisibility(View.VISIBLE)
                        viewHolder.viewExpandLessMore!!.setVisibility(View.VISIBLE)
                        viewHolder.ivExpandLessMore!!.setImageResource(R.drawable.ic_expand_less)
                    }
                    else {
                        viewHolder.feeDescriptionLay!!.setVisibility(View.GONE)
                        viewHolder.viewExpandLessMore!!.setVisibility(View.GONE)
                        viewHolder.ivExpandLessMore!!.setImageResource(R.drawable.ic_expand_more)
                    }
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return 5;
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivExpandLessMore: ImageView? = null
        var viewExpandLessMore: View? = null
        var feeDescriptionLay: LinearLayout? = null

        init {
            ivExpandLessMore = itemView.findViewById(R.id.iv_expand_less_more_fee)
            viewExpandLessMore = itemView.findViewById(R.id.view_expand_less_more_fee)
            feeDescriptionLay = itemView.findViewById(R.id.fee_description_lay)
        }
    }
}