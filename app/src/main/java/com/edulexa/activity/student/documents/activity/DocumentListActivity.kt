package com.edulexa.activity.student.documents.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.documents.adapter.DocumentListAdapter
import com.edulexa.activity.student.documents.model.DocumentListModel
import com.edulexa.activity.student.teacher_rating.adapter.TeacherListAdapter
import com.edulexa.activity.student.teacher_rating.model.TeacherListResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityDocumentListStudentBinding
import com.edulexa.databinding.ActivityTeacherListStudentBinding
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

class DocumentListActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityDocumentListStudentBinding? = null
    val listDocument : List<DocumentListModel> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentListStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getDocumentList()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getDocumentList(){
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

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getDocument")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getDocument(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try{
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()){
                            val jsonArr = JSONArray(responseStr)
                            if (jsonArr.length() > 0){
                                (listDocument as ArrayList<DocumentListModel>).clear()
                                for (i in 0..jsonArr.length()-1){
                                    val jsonObj = jsonArr.optJSONObject(i)
                                    val documentModel = DocumentListModel()
                                    documentModel.setId(jsonObj.optString("id"))
                                    documentModel.setFolderName(jsonObj.optString("folder_name"))
                                    documentModel.setFolderPath(jsonObj.optString("folder_path"))
                                    documentModel.setCreatedAt(jsonObj.optString("created_at"))
                                    documentModel.setUpdatedAt(jsonObj.optString("updated_at"))
                                    documentModel.setStatus(jsonObj.optString("status"))
                                    listDocument.add(documentModel)
                                }
                                if (listDocument.size > 0){
                                    binding!!.documentRecycler.visibility = View.VISIBLE
                                    binding!!.tvDocumentNoData.visibility = View.GONE
                                    binding!!.documentRecycler.layoutManager = GridLayoutManager(mActivity,2,RecyclerView.VERTICAL,false)
                                    binding!!.documentRecycler.adapter = DocumentListAdapter(mActivity!!,listDocument)
                                }else{
                                    binding!!.documentRecycler.visibility = View.GONE
                                    binding!!.tvDocumentNoData.visibility = View.VISIBLE
                                }
                            }else{
                                binding!!.documentRecycler.visibility = View.GONE
                                binding!!.tvDocumentNoData.visibility = View.VISIBLE
                            }
                        }else {
                            Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                            binding!!.documentRecycler.visibility = View.GONE
                            binding!!.tvDocumentNoData.visibility = View.VISIBLE
                        }
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.documentRecycler.visibility = View.GONE
                        binding!!.tvDocumentNoData.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.documentRecycler.visibility = View.GONE
                    binding!!.tvDocumentNoData.visibility = View.VISIBLE
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