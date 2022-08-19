package com.edulexa.activity.staff.online_exam.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.edulexa.R
import com.edulexa.activity.staff.online_exam.adapter.subjective.SubjectiveImagesAdapter
import com.edulexa.activity.staff.online_exam.model.subjective.info.ImagesArray
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivitySubjectiveImagesStaffBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SubjectiveImagesActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivitySubjectiveImagesStaffBinding? = null
    private var imagesArr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubjectiveImagesStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        setUpData()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            imagesArr = bundle!!.getString(Constants.StaffOnlineExam.IMAGES)!!
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setUpData(){
        try{
            if (imagesArr != ""){
                val list: List<ImagesArray> = Gson().fromJson(imagesArr,
                    object : TypeToken<List<ImagesArray?>?>() {}.type
                )
                if (list.isNotEmpty()){
                    binding!!.recyclerView.visibility = View.VISIBLE
                    binding!!.tvNoData.visibility = View.GONE
                    binding!!.recyclerView.layoutManager = GridLayoutManager(mActivity!!,2,LinearLayoutManager.VERTICAL,false)
                    binding!!.recyclerView.adapter = SubjectiveImagesAdapter(mActivity!!,list)
                }else{
                    binding!!.recyclerView.visibility = View.GONE
                    binding!!.tvNoData.visibility = View.VISIBLE
                }
            }
        }catch (e : Exception){
            e.printStackTrace()
        }
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}