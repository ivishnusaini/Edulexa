package com.edulexa.activity.student.library.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.library.model.IssueBook
import com.edulexa.activity.student.library.model.all_books.DatumAllBooks
import com.edulexa.databinding.ItemStudentLibraryAllBooksBinding
import com.edulexa.databinding.ItemStudentLibraryIssuedBooksBinding

class LibraryAllBooksAdapter(context: Activity, list : List<DatumAllBooks?>?) :
    RecyclerView.Adapter<LibraryAllBooksAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumAllBooks?>? = null
    var binding : ItemStudentLibraryAllBooksBinding? = null
    init {
        this.context = context
        this.list = list
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setFilterData(list : List<DatumAllBooks?>?){
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentLibraryAllBooksBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        try {
            binding!!.tvLibraryBookName.text = context!!.getString(R.string.library_student_string_format,"Subject : ",list!!.get(position)!!.getBookTitle())
            binding!!.tvLibraryBookAuthorName.text = context!!.getString(R.string.library_student_string_format,"Author : ",list!!.get(position)!!.getAuthor())
            binding!!.tvLibraryBookPublisherName.text = context!!.getString(R.string.library_student_string_format,"Publish by : ",list!!.get(position)!!.getPublish())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }

    class ViewHolder(binding: ItemStudentLibraryAllBooksBinding) : RecyclerView.ViewHolder(binding.root)
}