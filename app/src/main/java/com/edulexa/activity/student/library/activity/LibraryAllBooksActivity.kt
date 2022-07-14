package com.edulexa.activity.student.library.activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.library.adapter.LibraryAllBooksAdapter
import com.edulexa.activity.student.library.adapter.LibraryIssuedBooksAdapter
import com.edulexa.activity.student.library.model.LibraryResponse
import com.edulexa.activity.student.library.model.all_books.DatumAllBooks
import com.edulexa.activity.student.library.model.all_books.LibraryAllBookResponse
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityLibraryAllBooksStudentBinding
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

class LibraryAllBooksActivity : AppCompatActivity(),View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityLibraryAllBooksStudentBinding? = null
    var libraryAllBooksAdapter : LibraryAllBooksAdapter? = null
    var listAllBooks : List<DatumAllBooks?>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryAllBooksStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }
    private fun init() {
        mActivity = this
        setUpClickListener()
        getLibraryAllBookList()
        setUpFilterData()
    }
    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getLibraryAllBookList(){
        if (Utils.isNetworkAvailable(mActivity!!)){
            Utils.showProgressBar(mActivity!!)
            Utils.hideKeyboard(mActivity!!)

            val branchId = Preference().getInstance(mActivity!!)!!.getString(Constants.Preference.BRANCH_ID)!!
            val accessToken = Utils.getStudentLoginResponse(mActivity)!!.getToken()!!
            val userId = Utils.getStudentUserId(mActivity!!)

            val apiInterfaceWithHeader: ApiInterfaceStudent = APIClientStudent.getRetroFitClientWithNewKeyHeader(mActivity!!, accessToken,branchId,userId).create(
                ApiInterfaceStudent::class.java)

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/getLibraryBooks")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getLibraryAllBooks()
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
                            val success = jsonObjectResponse.optInt("success")
                            if (success == 1){
                                val modelResponse = Utils.getObject(responseStr, LibraryAllBookResponse::class.java) as LibraryAllBookResponse
                                if (modelResponse.getData() != null && modelResponse.getData()!!.size > 0){
                                    binding!!.libraryAllBookRecycler.visibility = View.VISIBLE
                                    binding!!.tvLibraryAllBookNoData.visibility = View.GONE
                                    binding!!.libraryAllBookRecycler.layoutManager = LinearLayoutManager(mActivity,
                                        RecyclerView.VERTICAL,false)
                                    listAllBooks = modelResponse.getData()
                                    libraryAllBooksAdapter = LibraryAllBooksAdapter(mActivity!!,listAllBooks)
                                    binding!!.libraryAllBookRecycler.adapter = libraryAllBooksAdapter
                                }else{
                                    binding!!.libraryAllBookRecycler.visibility = View.GONE
                                    binding!!.tvLibraryAllBookNoData.visibility = View.VISIBLE
                                }
                            }else{
                                binding!!.libraryAllBookRecycler.visibility = View.GONE
                                binding!!.tvLibraryAllBookNoData.visibility = View.VISIBLE
                            }
                        }else Utils.showToastPopup(mActivity!!, getString(R.string.response_null_or_empty_validation))
                    }catch (e : Exception){
                        e.printStackTrace()
                        binding!!.libraryAllBookRecycler.visibility = View.GONE
                        binding!!.tvLibraryAllBookNoData.visibility = View.VISIBLE
                    }
                }
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.libraryAllBookRecycler.visibility = View.GONE
                    binding!!.tvLibraryAllBookNoData.visibility = View.VISIBLE
                }
            })
        }else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun setUpFilterData(){
        binding!!.etLibraryAllBookSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                try{
                    if (!charSequence.toString().isEmpty()){
                        if (listAllBooks != null && listAllBooks!!.size > 0){
                            val filterList : List<DatumAllBooks?> = ArrayList()
                            for (model in listAllBooks!!){
                                if (model!!.getBookTitle()!!.lowercase().contains(charSequence.toString().lowercase()) ||
                                    model.getPublish()!!.lowercase().contains(charSequence.toString().lowercase()))
                                    (filterList as ArrayList<DatumAllBooks?>).add(model)
                            }
                            if (filterList.size > 0){
                                binding!!.libraryAllBookRecycler.visibility = View.VISIBLE
                                binding!!.tvLibraryAllBookNoData.visibility = View.GONE
                                if (libraryAllBooksAdapter != null)
                                    libraryAllBooksAdapter!!.setFilterData(filterList)
                            }else{
                                binding!!.libraryAllBookRecycler.visibility = View.GONE
                                binding!!.tvLibraryAllBookNoData.visibility = View.VISIBLE
                            }
                        }
                    }else{
                        binding!!.libraryAllBookRecycler.visibility = View.VISIBLE
                        binding!!.tvLibraryAllBookNoData.visibility = View.GONE
                        if (libraryAllBooksAdapter != null)
                            libraryAllBooksAdapter!!.setFilterData(listAllBooks)
                    }

                }catch (e : Exception){
                    e.printStackTrace()
                    binding!!.libraryAllBookRecycler.visibility = View.VISIBLE
                    binding!!.tvLibraryAllBookNoData.visibility = View.GONE
                    if (libraryAllBooksAdapter != null)
                        libraryAllBooksAdapter!!.setFilterData(listAllBooks)
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }
}