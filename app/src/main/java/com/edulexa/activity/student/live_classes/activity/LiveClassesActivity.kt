package com.edulexa.activity.student.live_classes.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.student.live_classes.adapter.LiveClassesViewpagerAdapter
import com.edulexa.activity.student.live_classes.adapter.UpcomingLiveClassAdapter
import com.edulexa.activity.student.live_classes.model.DatumLiveClass
import com.edulexa.activity.student.live_classes.model.LiveClassResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityLiveClassesStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiveClassesActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityLiveClassesStudentBinding? = null
    val listUpcoming : List<DatumLiveClass> = ArrayList()
    val listOnGoing : List<DatumLiveClass> = ArrayList()
    val listCompleted : List<DatumLiveClass> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveClassesStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getLiveClassesList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.liveClassUpcomingLay.setOnClickListener(this)
        binding!!.liveClassOngoingLay.setOnClickListener(this)
        binding!!.liveClassCompletedLay.setOnClickListener(this)
    }

    private fun getLiveClassesList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.CLASS_ID, Utils.getStudentClassId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.SECTION_ID, Utils.getStudentSectionId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.TYPE, "zoom")

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getyoutube_live")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getLiveClasses(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try{
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()){
                            val jsonObjectResponse = JSONObject(responseStr)
                            val youtubeListJson = jsonObjectResponse.optJSONObject("youtube_list")
                            if (youtubeListJson != null){
                                val status = youtubeListJson.optInt("success")
                                if (status == 1){
                                    val modelResponse = Utils.getObject(
                                        responseStr,
                                        LiveClassResponse::class.java
                                    ) as LiveClassResponse
                                    if (modelResponse.getYoutubeList()!!.getData() != null && modelResponse.getYoutubeList()!!.getData()!!.size > 0){
                                        for (datumLiveClass in modelResponse.getYoutubeList()!!.getData()!!){
                                            if (datumLiveClass!!.getStatus().equals("Upcoming"))
                                                (listUpcoming as ArrayList<DatumLiveClass>).add(datumLiveClass)
                                            if (datumLiveClass.getStatus().equals("Ongoing"))
                                                (listOnGoing as ArrayList<DatumLiveClass>).add(datumLiveClass)
                                            if (datumLiveClass.getStatus().equals("Result"))
                                                (listCompleted as ArrayList<DatumLiveClass>).add(datumLiveClass)
                                        }
                                        Constants.AppSaveData.listUpcoming = listUpcoming
                                        Constants.AppSaveData.listOnGoing = listOnGoing
                                        Constants.AppSaveData.listCompleted = listCompleted
                                        setUpViewPager()
                                    }
                                }
                            }
                        }else Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                }
            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }


    private fun setUpViewPager() {
        binding!!.viewPagerLiveClass.adapter =
            LiveClassesViewpagerAdapter(supportFragmentManager, 3)
        binding!!.viewPagerLiveClass.addOnPageChangeListener(object :
            ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                resetTab(position)
            }

            override fun onPageScrollStateChanged(state: Int) {

            }

        })
    }

    private fun resetTab(position: Int) {
        binding!!.viewPagerLiveClass.currentItem = position
        binding!!.tvLiveClassUpcoming.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewLiveClassUpcoming.visibility = View.GONE
        binding!!.tvLiveClassOngoing.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewLiveClassOngoing.visibility = View.GONE
        binding!!.tvLiveClassCompleted.setTextColor(
            ContextCompat.getColor(
                mActivity!!,
                R.color.primaray_text_color
            )
        )
        binding!!.viewLiveClassCompleted.visibility = View.GONE

        when (position) {
            0 -> {
                binding!!.tvLiveClassUpcoming.setTextColor(ContextCompat.getColor(mActivity!!, R.color.colorPrimary))
                binding!!.viewLiveClassUpcoming.visibility = View.VISIBLE
            }
            1 -> {
                binding!!.tvLiveClassOngoing.setTextColor(ContextCompat.getColor(mActivity!!, R.color.colorPrimary))
                binding!!.viewLiveClassOngoing.visibility = View.VISIBLE
            }
            2 -> {
                binding!!.tvLiveClassCompleted.setTextColor(ContextCompat.getColor(mActivity!!, R.color.colorPrimary))
                binding!!.viewLiveClassCompleted.visibility = View.VISIBLE
            }
        }
    }



    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.live_class_upcoming_lay)
            resetTab(0)
        else if (id == R.id.live_class_ongoing_lay)
            resetTab(1)
        else if (id == R.id.live_class_completed_lay)
            resetTab(2)
    }

}