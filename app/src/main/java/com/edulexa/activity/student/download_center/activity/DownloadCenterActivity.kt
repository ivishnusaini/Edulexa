package com.edulexa.activity.student.download_center.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.edulexa.R
import com.edulexa.activity.student.download_center.adapter.DownloadCenterTypeAdapter
import com.edulexa.activity.student.download_center.adapter.ViewpagerDownloadCenterAdapter
import com.edulexa.activity.student.download_center.model.DownloadCenterModel
import com.edulexa.activity.student.download_center.model.DownloadDetailModel
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityDownloadCenterStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DownloadCenterActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityDownloadCenterStudentBinding? = null
    var downloadCenterList : List<DownloadCenterModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadCenterStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getDownloadCenterData()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getDownloadCenterData(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.CLASSID, Utils.getStudentClassId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.SECTIONID, Utils.getStudentSectionId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.TAG, "")
            jsonObject.put(Constants.ParamsStudent.SEARCH, "")

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getDownloadsLinks")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getDownloadLinks(requestBody)
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
                            val statusCode = jsonObjectResponse.optInt("success")
                            if (statusCode == 1){
                                val resultJsonObj = jsonObjectResponse.optJSONObject("data")
                                if (resultJsonObj != null){
                                    (downloadCenterList as ArrayList<DownloadCenterModel>).clear()
                                    val iterator: Iterator<String> = resultJsonObj.keys()
                                    while (iterator.hasNext()){
                                        val downloadCenterModel = DownloadCenterModel()
                                        val keyName = iterator.next()
                                        val dayWiseJsonArr = JSONArray(resultJsonObj.optString(keyName))
                                        val downloadDetailModelList : List<DownloadDetailModel> = ArrayList()
                                        if (dayWiseJsonArr.length() > 0){
                                            for (i in 0..dayWiseJsonArr.length()-1){
                                                val dayWiseJsonObj = dayWiseJsonArr.optJSONObject(i)
                                                val downloadTypeDetailModel = DownloadDetailModel()
                                                downloadTypeDetailModel.setClassName(dayWiseJsonObj.optString("class"))
                                                downloadTypeDetailModel.setClassId(dayWiseJsonObj.optString("class_id"))
                                                downloadTypeDetailModel.setClassSectionId(dayWiseJsonObj.optString("class_section_id"))
                                                downloadTypeDetailModel.setCreatedAt(dayWiseJsonObj.optString("created_at"))
                                                downloadTypeDetailModel.setCreatedBy(dayWiseJsonObj.optString("created_by"))
                                                downloadTypeDetailModel.setDate(dayWiseJsonObj.optString("date"))
                                                downloadTypeDetailModel.setDirPath(dayWiseJsonObj.optString("dir_path"))
                                                downloadTypeDetailModel.setFile(dayWiseJsonObj.optString("file"))
                                                downloadTypeDetailModel.setId(dayWiseJsonObj.optString("id"))
                                                downloadTypeDetailModel.setIsActive(dayWiseJsonObj.optString("is_active"))
                                                downloadTypeDetailModel.setIsPublic(dayWiseJsonObj.optString("is_public"))
                                                downloadTypeDetailModel.setNote(dayWiseJsonObj.optString("note"))
                                                downloadTypeDetailModel.setSection(dayWiseJsonObj.optString("section"))
                                                downloadTypeDetailModel.setTag(dayWiseJsonObj.optString("tag"))
                                                downloadTypeDetailModel.setThumbName(dayWiseJsonObj.optString("thumb_name"))
                                                downloadTypeDetailModel.setThumbPath(dayWiseJsonObj.optString("thumb_path"))
                                                downloadTypeDetailModel.setTitle(dayWiseJsonObj.optString("title"))
                                                downloadTypeDetailModel.setType(dayWiseJsonObj.optString("type"))
                                                downloadTypeDetailModel.setUpdateAt(dayWiseJsonObj.optString("updated_at"))
                                                downloadTypeDetailModel.setVideoTitle(dayWiseJsonObj.optString("video_title"))
                                                downloadTypeDetailModel.setVideoUrl(dayWiseJsonObj.optString("video_url"))
                                                (downloadDetailModelList as ArrayList<DownloadDetailModel>).add(downloadTypeDetailModel)
                                            }
                                        }
                                        downloadCenterModel.setType(keyName)
                                        downloadCenterModel.setTypeWiseList(downloadDetailModelList)
                                        (downloadCenterList as ArrayList<DownloadCenterModel>).add(downloadCenterModel)
                                    }
                                    if (downloadCenterList.size > 0){
                                        (downloadCenterList as ArrayList<DownloadCenterModel>).get(0).setSelectedFlag(true)
                                        binding!!.studentDownloadCenterTypeRecycler.layoutManager = LinearLayoutManager(mActivity,
                                            RecyclerView.HORIZONTAL,false)
                                        binding!!.studentDownloadCenterTypeRecycler.adapter = DownloadCenterTypeAdapter(mActivity!!,downloadCenterList)
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
        binding!!.viewPagerDownloadCenter.adapter =
            ViewpagerDownloadCenterAdapter(supportFragmentManager, downloadCenterList,downloadCenterList.size)
        binding!!.viewPagerDownloadCenter.addOnPageChangeListener(object :
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
        for (model in downloadCenterList){
            model.setSelectedFlag(false)
        }
        downloadCenterList.get(position).setSelectedFlag(true)
        binding!!.studentDownloadCenterTypeRecycler.adapter = DownloadCenterTypeAdapter(mActivity!!,downloadCenterList)
        binding!!.studentDownloadCenterTypeRecycler.smoothScrollToPosition(position)
    }

    fun setViewPagerFromTabClick(position: Int) {
        binding!!.viewPagerDownloadCenter.setCurrentItem(position)
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}