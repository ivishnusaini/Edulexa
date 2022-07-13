package com.edulexa.activity.student.fee.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.edulexa.R
import com.edulexa.activity.student.fee.model.FeeDetail
import com.edulexa.activity.student.fee.model.FeeModel
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.FragmentFeeViewpagerStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class FeeViewpagerFragment : Fragment() {
    var binding: FragmentFeeViewpagerStudentBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: FeeViewpagerFragment? = null
        fun newInstance(): FeeViewpagerFragment? {
            fragment = FeeViewpagerFragment()
            return fragment
        }

        fun getInstance(): FeeViewpagerFragment? {
            return if (fragment == null) FeeViewpagerFragment() else fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeeViewpagerStudentBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }
    private fun init(){
        mActivity = activity
        setUpFeeList()
    }
    private fun setUpFeeList(){
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
            jsonObject.put(Constants.ParamsStudent.STUDENT_SESSION_ID, Utils.getStudentSessionId(mActivity!!))

            val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString())

            Utils.printLog("Url", Constants.DOMAIN_STUDENT+"/Webservice/fees")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getFeesData(requestBody)
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
                                val dataJsonObj = jsonObjectResponse.optJSONObject("data")
                                if (dataJsonObj != null){
                                    val studentJsonArr = dataJsonObj.optJSONArray("student_due_fee")
                                    if (studentJsonArr != null && studentJsonArr.length() > 0){
                                        val listFee : List<FeeModel> = ArrayList()
                                        val listFeeDetail : List<FeeDetail> = ArrayList()
                                        for (i in 0..studentJsonArr.length()-1){
                                            val studentJsonObj = studentJsonArr.optJSONObject(i)
                                            val feeModel = FeeModel()
                                            feeModel.setAmount(studentJsonObj.optString("amount"))
                                            feeModel.setCreatedAt(studentJsonObj.optString("created_at"))
                                            feeModel.setFeeSessionGroupId(studentJsonObj.optString("fee_session_group_id"))
                                            feeModel.setId(studentJsonObj.optString("id"))
                                            feeModel.setIsActive(studentJsonObj.optString("is_active"))
                                            feeModel.setIsSystem(studentJsonObj.optString("is_system"))
                                            feeModel.setName(studentJsonObj.optString("name"))
                                            feeModel.setStudentSessionId(studentJsonObj.optString("student_session_id"))

                                            val studentFeeArr = studentJsonObj.optJSONArray("fees")
                                            if (studentFeeArr != null && studentFeeArr.length() > 0){
                                                for (j in 0..studentFeeArr.length()-1){
                                                    val innerFeeDetail = studentFeeArr.optJSONObject(j)
                                                    val feeDetail = FeeDetail()
                                                    feeDetail.setAmount(innerFeeDetail.optString("amount"))
                                                    feeDetail.setDueDate(innerFeeDetail.optString("due_date"))
                                                    feeDetail.setFeeTypeId(innerFeeDetail.optString("feetype_id"))
                                                    feeDetail.setFeeGroupdFeeTypeId(innerFeeDetail.optString("fee_groups_feetype_id"))
                                                    feeDetail.setFeeGroupsId(innerFeeDetail.optString("fee_groups_id"))
                                                    feeDetail.setFeeSessionGroupId(innerFeeDetail.optString("fee_session_group_id"))
                                                    feeDetail.setId(innerFeeDetail.optString("id"))
                                                    feeDetail.setIsActive(innerFeeDetail.optString("is_active"))
                                                    feeDetail.setName(innerFeeDetail.optString("name"))
                                                    feeDetail.setStatus(innerFeeDetail.optString("status"))
                                                    feeDetail.setStudentFeesDepositeId(innerFeeDetail.optString("student_fees_deposite_id"))
                                                    feeDetail.setStudentSessionId(innerFeeDetail.optString("student_session_id"))
                                                    feeDetail.setTotalAmountDiscount(innerFeeDetail.optString("total_amount_discount"))
                                                    feeDetail.setTotalAmountDisplay(innerFeeDetail.optString("total_amount_display"))
                                                    feeDetail.setTotalAmountFine(innerFeeDetail.optString("total_amount_fine"))
                                                    feeDetail.setTotalAmountPaid(innerFeeDetail.optString("total_amount_paid"))
                                                    feeDetail.setTotalAmountRemaining(innerFeeDetail.optString("total_amount_remaining"))
                                                    feeDetail.setType(innerFeeDetail.optString("type"))
                                                    (listFeeDetail as ArrayList<FeeDetail>).add(feeDetail)
                                                }
                                            }
                                            feeModel.setFeeDetail(listFeeDetail)
                                            (listFee as ArrayList<FeeModel>).add(feeModel)
                                        }
                                        setUpFeeData(listFeeDetail)
                                    }
                                }else Utils.showToast(mActivity!!,message)
                            }else
                                Utils.showToast(mActivity!!,message)
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

    @SuppressLint("InflateParams")
    private fun setUpFeeData(list : List<FeeDetail>){
        if (list.size > 0){
            binding!!.feeListLay.visibility = View.VISIBLE
            binding!!.tvFeeNoData.visibility = View.GONE
            binding!!.feeListLay.removeAllViews()
            for (position in 0 until list.size){
                val inflater = mActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val itemView: View = inflater!!.inflate(R.layout.item_student_fee_list, null, true)

                val tvStudentFeeName = itemView.findViewById<TextView>(R.id.tv_student_fee_name)
                val tvDueDateFee = itemView.findViewById<TextView>(R.id.tv_due_date_fee)
                val tvDueAmount = itemView.findViewById<TextView>(R.id.tv_due_amount)
                val tvFeeStatus = itemView.findViewById<TextView>(R.id.tv_fee_status)
                val tvTotalFee = itemView.findViewById<TextView>(R.id.tv_total_fee)
                val tvDiscountFee = itemView.findViewById<TextView>(R.id.tv_discount_fee)
                val tvPaidFee = itemView.findViewById<TextView>(R.id.tv_paid_fee)
                val ivExpandLessMoreFee = itemView.findViewById<ImageView>(R.id.iv_expand_less_more_fee)
                val feeDescriptionLay = itemView.findViewById<LinearLayout>(R.id.fee_description_lay)
                val viewExpandLessMoreFee = itemView.findViewById<View>(R.id.view_expand_less_more_fee)

                tvStudentFeeName.text = list.get(position).getName()
                tvDueDateFee.text = list.get(position).getDueDate()
                tvDueAmount.text = list.get(position).getTotalAmountRemaining()
                tvFeeStatus.text = list.get(position).getStatus()
                tvTotalFee.text = list.get(position).getAmount()
                tvDiscountFee.text = list.get(position).getTotalAmountDiscount()
                tvPaidFee.text = list.get(position).getTotalAmountRemaining()

                ivExpandLessMoreFee.tag = 1000+position


                ivExpandLessMoreFee.setOnClickListener {
                    val tag = ivExpandLessMoreFee.tag as Int - 10000
                    if (feeDescriptionLay.visibility == View.GONE) {
                        feeDescriptionLay.setVisibility(View.VISIBLE)
                        viewExpandLessMoreFee.setVisibility(View.VISIBLE)
                        ivExpandLessMoreFee.setImageResource(R.drawable.ic_expand_less)
                    } else {
                        feeDescriptionLay.setVisibility(View.GONE)
                        viewExpandLessMoreFee.setVisibility(View.GONE)
                        ivExpandLessMoreFee.setImageResource(R.drawable.ic_expand_more)
                    }
                }
                binding!!.feeListLay.addView(itemView)
            }
        }else{
            binding!!.feeListLay.visibility = View.GONE
            binding!!.tvFeeNoData.visibility = View.VISIBLE
        }

    }

}