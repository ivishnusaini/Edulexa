package com.edulexa.activity.student.homework.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.branch_code.adapter.BranchSpinnerAdapter
import com.edulexa.activity.branch_code.model.Branch
import com.edulexa.activity.student.dashboard.adapter.DashboardStudentNoticeBoardAdapter
import com.edulexa.activity.student.dashboard.model.StudentDashboardResponse
import com.edulexa.activity.student.homework.adapter.HomeworkStudentAdapter
import com.edulexa.activity.student.homework.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.student.homework.model.StudentHomeworkResponse
import com.edulexa.activity.student.homework.model.subject_list.SubjectListDatum
import com.edulexa.activity.student.homework.model.subject_list.SubjectListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityHomeworkStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeworkStudentActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityHomeworkStudentBinding? = null
    var subjectId = ""
    var subjectListSpinn: List<SubjectListDatum?> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeworkStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        setUpData()
        getSubjectList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun setUpData(){
        binding!!.subjectSpinn.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
                if (!subjectListSpinn.get(position)!!.getSubjectId().equals("")) {
                    subjectId = subjectListSpinn.get(position)!!.getSubjectId()!!
                    setUpHomeworkData()
                }
                else subjectId = ""
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        })
    }

    private fun getSubjectList(){
        (subjectListSpinn as ArrayList<SubjectListDatum?>).clear()
        subjectId = ""
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
            jsonObject.put(Constants.ParamsStudent.SECTIONID,Utils.getStudentSectionId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getSubjectList")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getSubjectList(requestBody)
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
                            val statusCode = jsonObjectResponse.optInt("status")
                            val message = jsonObjectResponse.optString("message")
                            if (statusCode == 200){
                                binding!!.subjectSpinnLay.visibility = View.VISIBLE
                                val modelResponse = Utils.getObject(responseStr, SubjectListResponse::class.java) as SubjectListResponse
                                if (modelResponse.getData() != null){
                                    if (modelResponse.getData()!!.size > 0){
                                        val subjectListDatum = SubjectListDatum()
                                        subjectListDatum.setSubjectName("Select Subject")
                                        subjectListSpinn = modelResponse.getData() as List<SubjectListDatum?>
                                        (subjectListSpinn as ArrayList<SubjectListDatum?>).add(0,subjectListDatum)
                                        binding!!.subjectSpinn.adapter = SubjectSpinnerAdapter(mActivity!!,subjectListSpinn)
                                    }
                                }
                            }else Utils.showToast(mActivity!!,message)
                        }else Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                    }catch (e : Exception){
                        e.printStackTrace()
                    }
                    setUpHomeworkData()
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    setUpHomeworkData()
                }
            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun setUpHomeworkData(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(mActivity!!))
            jsonObject.put(Constants.ParamsStudent.SUBJECT_ID,subjectId)

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getHomework")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getHomeworkData(requestBody)
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
                            val statusCode = jsonObjectResponse.optInt("status")
                            val message = jsonObjectResponse.optString("message")
                            if (statusCode == 200){
                                val modelResponse = Utils.getObject(responseStr, StudentHomeworkResponse::class.java) as StudentHomeworkResponse
                                if (modelResponse.getHomework() != null){
                                    if (modelResponse.getHomework()!!.size > 0){
                                        binding!!.studentHomeworkRecycler.visibility = View.VISIBLE
                                        binding!!.tvStudentHomeworkNoData.visibility = View.GONE
                                        binding!!.studentHomeworkRecycler.layoutManager =
                                            LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
                                        binding!!.studentHomeworkRecycler.adapter =
                                            HomeworkStudentAdapter(mActivity!!,modelResponse.getHomework())
                                        binding!!.studentHomeworkRecycler.smoothScrollToPosition(0)
                                    }else{
                                        binding!!.studentHomeworkRecycler.visibility = View.GONE
                                        binding!!.tvStudentHomeworkNoData.visibility = View.VISIBLE
                                    }
                                }else{
                                    binding!!.studentHomeworkRecycler.visibility = View.GONE
                                    binding!!.tvStudentHomeworkNoData.visibility = View.VISIBLE
                                }
                            }else {
                                Utils.showToast(mActivity!!,message)
                                binding!!.studentHomeworkRecycler.visibility = View.GONE
                                binding!!.tvStudentHomeworkNoData.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.studentHomeworkRecycler.visibility = View.GONE
                            binding!!.tvStudentHomeworkNoData.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.studentHomeworkRecycler.visibility = View.GONE
                        binding!!.tvStudentHomeworkNoData.visibility = View.VISIBLE
                    }

                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.studentHomeworkRecycler.visibility = View.GONE
                    binding!!.tvStudentHomeworkNoData.visibility = View.VISIBLE
                }

            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}