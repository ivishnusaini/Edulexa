package com.edulexa.activity.staff.student_profile.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.student_profile.adapter.ClassListAdapter
import com.edulexa.activity.staff.student_profile.adapter.SectionAdapter
import com.edulexa.activity.staff.student_profile.model.class_list.ClassData
import com.edulexa.activity.staff.student_profile.model.class_list.ClassResponse
import com.edulexa.activity.staff.student_profile.model.section.Section
import com.edulexa.activity.staff.student_profile.model.section.SectionResponse
import com.edulexa.api.*
import com.edulexa.databinding.ActivityStudentProfileClassListStaffBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentProfileClassListActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityStudentProfileClassListStaffBinding? = null
    var classListSpinn: List<ClassData?>? = ArrayList()
    var sectionListSpinn: List<Section?>? = ArrayList()
    var classId: String = ""
    var preference: Preference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileClassListStaffBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        preference = Preference().getInstance(mActivity!!)
        setUpClickListener()
        getClassList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getClassList() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!),
                    dbId!!
                ).create(ApiInterfaceStaff::class.java)

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getClasses")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getClasses(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val responseJsonObject = JSONObject(responseStr)
                            val status = responseJsonObject.optInt("status")
                            if (status == 200) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    ClassResponse::class.java
                                ) as ClassResponse
                                if (modelResponse.getClasses() != null && modelResponse.getClasses()!!
                                        .isNotEmpty()
                                ) {
                                    (classListSpinn as ArrayList<ClassData?>).clear()
                                    binding!!.recyclerViewClass.visibility = View.VISIBLE
                                    binding!!.tvNoClass.visibility = View.GONE
                                    classListSpinn = modelResponse.getClasses()
                                    binding!!.recyclerViewClass.layoutManager = GridLayoutManager(
                                        mActivity,
                                        3,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                    binding!!.recyclerViewClass.adapter =
                                        ClassListAdapter(mActivity!!, classListSpinn)
                                } else {
                                    binding!!.recyclerViewClass.visibility = View.GONE
                                    binding!!.tvNoClass.visibility = View.VISIBLE
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
                                binding!!.recyclerViewClass.visibility = View.GONE
                                binding!!.tvNoClass.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.recyclerViewClass.visibility = View.GONE
                            binding!!.tvNoClass.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.recyclerViewClass.visibility = View.GONE
                        binding!!.tvNoClass.visibility = View.VISIBLE
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.recyclerViewClass.visibility = View.GONE
                    binding!!.tvNoClass.visibility = View.VISIBLE
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))
    }

    fun getSection(classId: String) {
        this.classId = classId
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val dbId = preference!!.getString(Constants.Preference.BRANCH_ID)

            val apiInterfaceWithHeader: ApiInterfaceStaff =
                APIClientStaff.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    Utils.getStaffToken(mActivity!!),
                    Utils.getStaffId(mActivity!!),
                    dbId!!
                ).create(ApiInterfaceStaff::class.java)


            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(Constants.ParamsStaff.STAFF_ID, Utils.getStaffId(mActivity!!))
            builder.addFormDataPart(Constants.ParamsStaff.CLASS_ID,  classId)
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.BASE_URL_STAFF + "getClassSections")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getClassSections(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        binding!!.nestesdScrollView.post(Runnable {
                            binding!!.nestesdScrollView.fullScroll(
                                View.FOCUS_DOWN
                            )
                        })
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            (sectionListSpinn as ArrayList<Section?>).clear()
                            val responseJsonObject = JSONObject(responseStr)
                            val sectionListJsonArr = responseJsonObject.optJSONArray("section_list")
                            if (sectionListJsonArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    SectionResponse::class.java
                                ) as SectionResponse
                                if (modelResponse.getSectionList() != null && modelResponse.getSectionList()!!
                                        .isNotEmpty()
                                ) {
                                    binding!!.recyclerViewSection.visibility = View.VISIBLE
                                    binding!!.tvNoSection.visibility = View.GONE
                                    sectionListSpinn = modelResponse.getSectionList()
                                    binding!!.recyclerViewSection.layoutManager = GridLayoutManager(
                                        mActivity,
                                        3,
                                        LinearLayoutManager.VERTICAL,
                                        false
                                    )
                                    binding!!.recyclerViewSection.adapter =
                                        SectionAdapter(mActivity!!, sectionListSpinn)
                                } else {
                                    binding!!.recyclerViewClass.visibility = View.GONE
                                    binding!!.tvNoClass.visibility = View.VISIBLE
                                }
                            } else {
                                val message = responseJsonObject.optString("message")
                                if (!message.isEmpty())
                                    Utils.showToastPopup(mActivity!!, message)
                                else Utils.showToastPopup(
                                    mActivity!!,
                                    getString(R.string.did_not_fetch_data)
                                )
                                binding!!.recyclerViewClass.visibility = View.GONE
                                binding!!.tvNoClass.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.recyclerViewClass.visibility = View.GONE
                            binding!!.tvNoClass.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.recyclerViewClass.visibility = View.GONE
                        binding!!.tvNoClass.visibility = View.VISIBLE
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.recyclerViewClass.visibility = View.GONE
                    binding!!.tvNoClass.visibility = View.VISIBLE
                }

            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))
    }

    fun selectSection(sectionId: String) {
        val bundle = Bundle()
        bundle.putString(Constants.StaffStudentProfile.CLASS_ID, classId)
        bundle.putString(Constants.StaffStudentProfile.SECTION_ID, sectionId)
        startActivity(Intent(mActivity, StudentListActivity::class.java).putExtras(bundle))
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}