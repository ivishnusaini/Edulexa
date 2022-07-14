package com.edulexa.activity.student.library.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.library.model.IssueBook
import com.edulexa.databinding.ItemStudentLibraryIssuedBooksBinding

class LibraryIssuedBooksAdapter(context: Activity, list : List<IssueBook?>?) :
    RecyclerView.Adapter<LibraryIssuedBooksAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<IssueBook?>? = null
    var binding : ItemStudentLibraryIssuedBooksBinding? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentLibraryIssuedBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            binding!!.tvLibraryBookName.text = context!!.getString(R.string.library_student_string_format,"Subject : ",list!!.get(position)!!.getBookTitle())
            binding!!.tvLibraryBookAuthorName.text = context!!.getString(R.string.library_student_string_format,"Author : ",list!!.get(position)!!.getAuthor())
            binding!!.tvLibraryBookIssueDate.text = context!!.getString(R.string.library_student_string_format,"Issued Date : ",list!!.get(position)!!.getIssueDate())
            binding!!.tvLibraryBookReturnedDate.text = context!!.getString(R.string.library_student_string_format,"Returned Date : ",list!!.get(position)!!.getReturnDate())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentLibraryIssuedBooksBinding) : RecyclerView.ViewHolder(binding.root)
}