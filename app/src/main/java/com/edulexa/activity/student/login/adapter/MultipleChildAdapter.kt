package com.edulexa.activity.student.login.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.dashboard.activity.DashboardStudentActivity
import com.edulexa.activity.student.login.ParentChildList
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentLoginMultipleChildBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils

class MultipleChildAdapter(context: Activity,list: List<ParentChildList>?) :
    RecyclerView.Adapter<MultipleChildAdapter.ViewHolder>() {
    var context: Activity? = null
    var list: List<ParentChildList>?= null
    var preference : Preference? = null
    var binding : ItemStudentLoginMultipleChildBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentLoginMultipleChildBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            preference = Preference().getInstance(context!!)
            Utils.setpProfileImageUsingGlide(context,list!!.get(position).getImage(),binding!!.ivStudentLoginMultipleChildImage)
            binding!!.tvStudentLoginMultipleChildName.text = list!!.get(position).getName()
            binding!!.tvStudentLoginMultipleChildName.text = context!!.getString(R.string.login_multiple_child_class_format,list!!.get(position).getClass(),"-",list!!.get(position).getSection())
            viewHolder.itemView.setOnClickListener(object :View.OnClickListener{
                override fun onClick(p0: View?) {
                    preference!!.putString(Constants.Preference.STUDENT_IS_LOGIN,Constants.Preference.STUDENT_IS_LOGIN_YES)
                    preference!!.putString(Constants.Preference.HAS_MULTIPLE_CHILD, Constants.Preference.HAS_MULTIPLE_CHILD_YES)
                    preference!!.putString(Constants.Preference.STUDENT_ID, list!!.get(position).getStudentId())
                    preference!!.putString(Constants.Preference.SESSION_ID, "")
                    preference!!.putString(Constants.Preference.STUDENT_SESSION_ID, list!!.get(position).getStudentSessionId())
                    preference!!.putString(Constants.Preference.CLASS_SECTION, list!!.get(position).getClass() + "-"+list!!.get(position).getSection())
                    preference!!.putString(Constants.Preference.STUDENT_NAME, list!!.get(position).getName())
                    context!!.startActivity(Intent(context!!, DashboardStudentActivity::class.java))
                    context!!.finish()
                }

            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentLoginMultipleChildBinding) : RecyclerView.ViewHolder(binding.root)
}