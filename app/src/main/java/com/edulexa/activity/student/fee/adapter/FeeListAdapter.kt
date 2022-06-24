package com.edulexa.activity.student.fee.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.attendance.adapter.AttendanceStudentMonthAdapter
import com.edulexa.activity.student.fee.model.FeeDetail
import com.edulexa.databinding.ItemStudentAttedndanceMonthBinding
import com.edulexa.databinding.ItemStudentFeeListBinding

class FeeListAdapter(context: Activity,list: List<FeeDetail>) :
    RecyclerView.Adapter<FeeListAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<FeeDetail>? = null
    var binding : ItemStudentFeeListBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentFeeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            binding!!.tvStudentFeeName.text = list!!.get(position).getName()
            binding!!.tvDueDateFee.text = list!!.get(position).getDueDate()
            binding!!.tvDueAmount.text = list!!.get(position).getTotalAmountRemaining()
            binding!!.tvFeeStatus.text = list!!.get(position).getStatus()
            binding!!.tvTotalFee.text = list!!.get(position).getAmount()
            binding!!.tvDiscountFee.text = list!!.get(position).getTotalAmountDiscount()
            binding!!.tvPaidFee.text = list!!.get(position).getTotalAmountRemaining()
            binding!!.ivExpandLessMoreFee.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    if (binding!!.feeDescriptionLay.getVisibility() == View.GONE) {
                        binding!!.feeDescriptionLay.setVisibility(View.VISIBLE)
                        binding!!.viewExpandLessMoreFee.setVisibility(View.VISIBLE)
                        binding!!.ivExpandLessMoreFee.setImageResource(R.drawable.ic_expand_less)
                    }
                    else {
                        binding!!.feeDescriptionLay.setVisibility(View.GONE)
                        binding!!.viewExpandLessMoreFee.setVisibility(View.GONE)
                        binding!!.ivExpandLessMoreFee.setImageResource(R.drawable.ic_expand_more)
                    }
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    class ViewHolder(binding: ItemStudentFeeListBinding) : RecyclerView.ViewHolder(binding.root)
}