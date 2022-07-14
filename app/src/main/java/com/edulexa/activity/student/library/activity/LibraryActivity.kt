package com.edulexa.activity.student.library.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.homework.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.student.homework.model.subject_list.SubjectListDatum
import com.edulexa.activity.student.homework.model.subject_list.SubjectListResponse
import com.edulexa.activity.student.library.adapter.LibraryIssuedBooksAdapter
import com.edulexa.activity.student.library.model.LibraryResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityHomeworkStudentBinding
import com.edulexa.databinding.ActivityLibraryStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LibraryActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityLibraryStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getLibraryBookList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvLibraryAllBook.setOnClickListener(this)
    }

    private fun getLibraryBookList() {
        if (Utils.isNetworkAvailable(mActivity!!)) {
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId =
                Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent =
                APIClientStudent.getRetroFitClientWithNewKeyHeader(
                    mActivity!!,
                    accessToken,
                    branchId,
                    userId
                ).create(
                    ApiInterfaceStudent::class.java
                )

            val jsonObject = JSONObject()
            jsonObject.put(Constants.ParamsStudent.STUDENT_ID, Utils.getStudentId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/getLibraryBookIssued")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getLibraryBookIssued(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonObjectResponse = JSONObject(responseStr)
                            val bookCount = jsonObjectResponse.optString("bookCount")
                            val returnCount = jsonObjectResponse.optString("return")
                            binding!!.tvBooksInLibrary.text = bookCount
                            binding!!.tvBooksReturned.text = returnCount
                            val issueBookArr = jsonObjectResponse.optJSONArray("issueBook")
                            if (issueBookArr != null) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    LibraryResponse::class.java
                                ) as LibraryResponse
                                if (modelResponse.getIssueBook()!!.size > 0) {
                                    binding!!.tvBooksIssued.text =
                                        modelResponse.getIssueBook()!!.size.toString()
                                    binding!!.libraryRecycler.visibility = View.VISIBLE
                                    binding!!.tvLibraryNoData.visibility = View.GONE
                                    binding!!.libraryRecycler.layoutManager =
                                        LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false)
                                    binding!!.libraryRecycler.adapter = LibraryIssuedBooksAdapter(
                                        mActivity!!,
                                        modelResponse.getIssueBook()
                                    )
                                } else {
                                    binding!!.libraryRecycler.visibility = View.GONE
                                    binding!!.tvLibraryNoData.visibility = View.VISIBLE
                                }
                            } else {
                                binding!!.libraryRecycler.visibility = View.GONE
                                binding!!.tvLibraryNoData.visibility = View.VISIBLE
                            }
                        } else Utils.showToastPopup(
                            mActivity!!,
                            getString(R.string.response_null_or_empty_validation)
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.libraryRecycler.visibility = View.GONE
                        binding!!.tvLibraryNoData.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.libraryRecycler.visibility = View.GONE
                    binding!!.tvLibraryNoData.visibility = View.VISIBLE
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_library_all_book)
            startActivity(Intent(mActivity, LibraryAllBooksActivity::class.java))
    }
}