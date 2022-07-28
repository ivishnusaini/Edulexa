package com.edulexa.activity.student.documents.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.documents.adapter.DocumentDetailAdapter
import com.edulexa.activity.student.documents.adapter.DocumentListAdapter
import com.edulexa.activity.student.documents.model.DocumentListModel
import com.edulexa.activity.student.documents.model.document_folder_detail.DocumentFolderDetailResponse
import com.edulexa.activity.student.homework.adapter.SubjectSpinnerAdapter
import com.edulexa.activity.student.homework.model.subject_list.SubjectListDatum
import com.edulexa.activity.student.homework.model.subject_list.SubjectListResponse
import com.edulexa.activity.student.online_exam.model.question_ans.UploadFileModel
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityDocumentDetailStudentBinding
import com.edulexa.databinding.ActivityDocumentListStudentBinding
import com.edulexa.databinding.DialogSelectImageBinding
import com.edulexa.support.FileUtils
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class DocumentDetailActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityDocumentDetailStudentBinding? = null
    var folderId = ""
    var cameraOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var galleryOnActivityLaunch: ActivityResultLauncher<Intent>? = null
    var uploadImageFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentDetailStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getBundleData()
        getDocumentFolderList()
        onActivityCamera()
        onActivityGallery()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
        binding!!.tvDocumentDetailUploadButton.setOnClickListener(this)
    }

    private fun getBundleData() {
        try {
            val bundle = intent.extras
            val title = bundle!!.getString(Constants.StudentDocument.TITLE)
            folderId = bundle.getString(Constants.StudentDocument.FOLDER_ID)!!
            binding!!.tvDocumentDetailTitle.text = title
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun onActivityCamera() {
        cameraOnActivityLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(), @SuppressLint("NotifyDataSetChanged")
            fun(result: ActivityResult) {
                if (result.resultCode === RESULT_OK) {
                    try {
                        val data = result.data
                        val bitmap = data!!.extras!!["data"] as Bitmap?
                        val resultUri: Uri = Utils.getImageUri(mActivity!!, bitmap!!)
                        val filePath: String? = FileUtils.getPath(mActivity!!, resultUri)
                        if (filePath != null) {
                            uploadImageFile = File(filePath)
                            uploadDocument()
                        }
                    } catch (e: java.lang.Exception) {
                        e.printStackTrace()
                    }
                }
            }
        )

    }

    private fun onActivityGallery() {
        galleryOnActivityLaunch = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode === RESULT_OK) {
                try {
                    val data = result.data
                    val resultUri =
                        Objects.requireNonNull(data)!!.data
                    val filePath = FileUtils.getPath(mActivity!!, resultUri!!)
                    if (filePath != null) {
                        uploadImageFile = File(filePath)
                        uploadDocument()
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }


    private fun getDocumentFolderList() {
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
            jsonObject.put(Constants.ParamsStudent.FOLDER_ID, folderId)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/getFolderData")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getFolderData(requestBody)
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
                            val statusCode = jsonObjectResponse.optInt("status")
                            if (statusCode == 1) {
                                val modelResponse = Utils.getObject(
                                    responseStr,
                                    DocumentFolderDetailResponse::class.java
                                ) as DocumentFolderDetailResponse
                                if (modelResponse.getData() != null) {
                                    if (modelResponse.getData()!!.size > 0) {
                                        binding!!.documentDetailRecycler.visibility = View.VISIBLE
                                        binding!!.tvDocumentDetailNoData.visibility = View.GONE
                                        binding!!.documentDetailRecycler.layoutManager =
                                            GridLayoutManager(
                                                mActivity,
                                                2,
                                                RecyclerView.VERTICAL,
                                                false
                                            )
                                        binding!!.documentDetailRecycler.adapter =
                                            DocumentDetailAdapter(
                                                mActivity!!,
                                                modelResponse.getData()
                                            )
                                    } else {
                                        binding!!.documentDetailRecycler.visibility = View.GONE
                                        binding!!.tvDocumentDetailNoData.visibility = View.VISIBLE
                                    }
                                } else {
                                    binding!!.documentDetailRecycler.visibility = View.GONE
                                    binding!!.tvDocumentDetailNoData.visibility = View.VISIBLE
                                }
                            } else {
                                binding!!.documentDetailRecycler.visibility = View.GONE
                                binding!!.tvDocumentDetailNoData.visibility = View.VISIBLE
                            }
                        } else {
                            Utils.showToastPopup(
                                mActivity!!,
                                getString(R.string.response_null_or_empty_validation)
                            )
                            binding!!.documentDetailRecycler.visibility = View.GONE
                            binding!!.tvDocumentDetailNoData.visibility = View.VISIBLE
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.documentDetailRecycler.visibility = View.GONE
                        binding!!.tvDocumentDetailNoData.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.documentDetailRecycler.visibility = View.GONE
                    binding!!.tvDocumentDetailNoData.visibility = View.VISIBLE
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun uploadDocument() {
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

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart(
                Constants.ParamsStudent.STUDENT_ID,
                Utils.getStudentId(mActivity!!)
            );
            builder.addFormDataPart(
                Constants.ParamsStudent.STUDENT_SESSION_ID,
                Utils.getStudentSessionId(mActivity!!)
            );
            builder.addFormDataPart(Constants.ParamsStudent.FOLDER_ID, folderId)
            builder.addFormDataPart(
                Constants.ParamsStudent.FIRST_DOC,
                uploadImageFile!!.name,
                RequestBody.create(MediaType.parse("multipart/form-data"), uploadImageFile!!)
            )
            val requestBody = builder.build()

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/create_doc")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.uploadDocument(requestBody)
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
                            val statusCode = jsonObjectResponse.optInt("status")
                            val message = jsonObjectResponse.optString("message")
                            if (statusCode == 1)
                                Utils.showToast(mActivity!!, message)
                            else Utils.showToast(mActivity!!, message)
                        } else Utils.showToastPopup(
                            mActivity!!,
                            getString(R.string.response_null_or_empty_validation)
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun dialogUploadDocument() {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
        )
        val rationale = "Please provide permission so that you can ..."
        val options = Permissions.Options()
            .setRationaleDialogTitle("Info")
            .setSettingsDialogTitle("Warning")
        Permissions.check(
            mActivity,
            permissions,
            rationale,
            options,
            object : PermissionHandler() {
                override fun onGranted() {
                    try {
                        var dialogBinding: DialogSelectImageBinding? = null
                        val dialog = Dialog(mActivity!!)
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        dialogBinding = DialogSelectImageBinding.inflate(layoutInflater)
                        dialog.setContentView(dialogBinding.root)
                        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                        dialog.setCanceledOnTouchOutside(false)

                        dialogBinding.tvDialogCamera.setOnClickListener(object :
                            View.OnClickListener {
                            override fun onClick(p0: View?) {
                                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                cameraOnActivityLaunch!!.launch(intent)
                                dialog.dismiss()
                            }
                        })

                        dialogBinding.tvDialogGallery.setOnClickListener(object :
                            View.OnClickListener {
                            override fun onClick(p0: View?) {
                                val pickPhoto = Intent(
                                    Intent.ACTION_PICK,
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                )
                                galleryOnActivityLaunch!!.launch(pickPhoto)
                                dialog.dismiss()
                            }
                        })
                        dialog.show()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onDenied(
                    context: Context,
                    deniedPermissions: java.util.ArrayList<String>
                ) {
                    Toast.makeText(
                        mActivity,
                        getString(R.string.permission_denied),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })
    }


    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
        else if (id == R.id.tv_document_detail_upload_button)
            dialogUploadDocument()
    }
}