package com.edulexa.activity.student.transport.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.edulexa.R
import com.edulexa.activity.student.transport.model.TransportModel
import com.edulexa.activity.student.transport.model.VehicleListModel
import com.edulexa.activity.student.transport.model.vehicle_details.VehicleDetailsModel
import com.edulexa.api.APIClientStudent
import com.edulexa.api.ApiInterfaceStudent
import com.edulexa.api.Constants
import com.edulexa.databinding.ActivityTransportStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TransportActivity : AppCompatActivity(), View.OnClickListener {
    var mActivity: Activity? = null
    var binding: ActivityTransportStudentBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransportStudentBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        init()
    }

    private fun init() {
        mActivity = this
        setUpClickListener()
        getTransportList()
    }

    private fun setUpClickListener() {
        binding!!.ivBack.setOnClickListener(this)
    }

    private fun getTransportList() {
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

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/getTransportroute")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getTransportRoute(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                @SuppressLint("InflateParams")
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonArr = JSONArray(responseStr)
                            if (jsonArr.length() > 0) {
                                val listTransport: List<TransportModel> = ArrayList()
                                for (position in 0 until jsonArr.length()) {
                                    val innerJsonObject = jsonArr.optJSONObject(position)
                                    if (!innerJsonObject.optString("no_of_vehicle").equals("")
                                        && !innerJsonObject.optString("no_of_vehicle").equals("null") ){
                                        val transportModel = TransportModel()
                                        transportModel.setCreateAt(innerJsonObject.optString("created_at"))
                                        transportModel.setFare(innerJsonObject.optString("fare"))
                                        transportModel.setId(innerJsonObject.optString("id"))
                                        transportModel.setIsActive(innerJsonObject.optString("is_active"))
                                        transportModel.setNote(innerJsonObject.optString("note"))
                                        transportModel.setNoOfVehicle(innerJsonObject.optString("no_of_vehicle"))
                                        transportModel.setRouteTitle(innerJsonObject.optString("route_title"))
                                        transportModel.setShiftId(innerJsonObject.optString("shift_id"))
                                        transportModel.setUpdateAt(innerJsonObject.optString("updated_at"))
                                        val innerJsonArr = innerJsonObject.optJSONArray("vehicles")
                                        val listVehicle: List<VehicleListModel> = ArrayList()
                                        for (innerPosition in 0 until innerJsonArr!!.length()) {
                                            val vehicleJsonObj = innerJsonArr.optJSONObject(innerPosition)
                                            val vehicleModel = VehicleListModel()
                                            vehicleModel.setAmcDate(vehicleJsonObj.optString("amc_date"))
                                            vehicleModel.setAssigned(vehicleJsonObj.optString("assigned"))
                                            vehicleModel.setCreatedAt(vehicleJsonObj.optString("created_at"))
                                            vehicleModel.setDeviceNo(vehicleJsonObj.optString("device_no"))
                                            vehicleModel.setDriverContact(vehicleJsonObj.optString("driver_contact"))
                                            vehicleModel.setDriverLicence(vehicleJsonObj.optString("driver_licence"))
                                            vehicleModel.setDriverName(vehicleJsonObj.optString("driver_name"))
                                            vehicleModel.setEngineChassisNumber(vehicleJsonObj.optString("engine_chassis_number"))
                                            vehicleModel.setEngineType(vehicleJsonObj.optString("engine_type"))
                                            vehicleModel.setFitnessValidity(vehicleJsonObj.optString("fitness_validity"))
                                            vehicleModel.setGpsEnable(vehicleJsonObj.optString("gps_enable"))
                                            vehicleModel.setId(vehicleJsonObj.optString("id"))
                                            vehicleModel.setInsuranceDate(vehicleJsonObj.optString("insurance_date"))
                                            vehicleModel.setInsuranceValidDate(vehicleJsonObj.optString("insurance_valid_date"))
                                            vehicleModel.setLastFuelAmount(vehicleJsonObj.optString("last_fuel_amount"))
                                            vehicleModel.setLastFuelInLitres(vehicleJsonObj.optString("last_fuel_in_litres"))
                                            vehicleModel.setLastNotedKms(vehicleJsonObj.optString("last_noted_kms"))
                                            vehicleModel.setManufactureYear(vehicleJsonObj.optString("manufacture_year"))
                                            vehicleModel.setNote(vehicleJsonObj.optString("note"))
                                            vehicleModel.setPassingDate(vehicleJsonObj.optString("passing_date"))
                                            vehicleModel.setPermitDate(vehicleJsonObj.optString("permit_date"))
                                            vehicleModel.setPollutionDate(vehicleJsonObj.optString("pollution_date"))
                                            vehicleModel.setPollutionValidDate(vehicleJsonObj.optString("pollution_valid_date"))
                                            vehicleModel.setPurchaseYear(vehicleJsonObj.optString("purchase_year"))
                                            vehicleModel.setRcNumber(vehicleJsonObj.optString("rc_number"))
                                            vehicleModel.setStaffId(vehicleJsonObj.optString("staff_id"))
                                            vehicleModel.setVecRouteId(vehicleJsonObj.optString("vec_route_id"))
                                            vehicleModel.setVehicalCondition(vehicleJsonObj.optString("vehical_condition"))
                                            vehicleModel.setVehicleModel(vehicleJsonObj.optString("vehicle_model"))
                                            vehicleModel.setVehicleNo(vehicleJsonObj.optString("vehicle_no"))
                                            (listVehicle as ArrayList<VehicleListModel>).add(vehicleModel)
                                        }
                                        transportModel.setVehicleList(listVehicle)
                                        (listTransport as ArrayList<TransportModel>).add(transportModel)
                                    }
                                }
                                if (listTransport.size > 0) {
                                    binding!!.transportListLay.visibility = View.VISIBLE
                                    binding!!.tvTransportNoData.visibility = View.GONE
                                    binding!!.transportListLay.removeAllViews()
                                    for (position in 0 until listTransport.size) {
                                        val inflater =
                                            mActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                                        val itemView: View = inflater!!.inflate(
                                            R.layout.item_student_transport,
                                            null,
                                            true
                                        )
                                        val tvTransportRouteName =
                                            itemView.findViewById<TextView>(R.id.tv_transport_route_name)
                                        val vehicleListLay =
                                            itemView.findViewById<LinearLayout>(R.id.vehicle_list_lay)
                                        tvTransportRouteName.text = getString(R.string.library_student_string_format,"Route : ",listTransport.get(position).getRouteTitle())

                                        if (listTransport.get(position).getVehicleList()!!.size > 0){
                                            vehicleListLay.removeAllViews()
                                            for (innerPosition in 0 until listTransport.get(position).getVehicleList()!!.size){
                                                val innerInflater =
                                                    mActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                                                val innerItemView: View = innerInflater!!.inflate(
                                                    R.layout.item_student_transport_vehicle_list,
                                                    null,
                                                    true
                                                )
                                                val tvTransportVehicleName = innerItemView.findViewById<TextView>(R.id.tv_transport_vehicle_name)
                                                val vehicleNoLay = innerItemView.findViewById<RelativeLayout>(R.id.vehicle_no_lay)
                                                tvTransportVehicleName.text = getString(R.string.library_student_string_format,"Vehicle No : ",listTransport.get(position).getVehicleList()!!.get(innerPosition).getVehicleNo())
                                                tvTransportVehicleName.tag = 1000 + innerPosition

                                                vehicleNoLay.setOnClickListener {
                                                    val tag = tvTransportVehicleName.tag as Int - 1000
                                                    getVehicleDetails(listTransport.get(position).getVehicleList()!!.get(tag).getId()!!,
                                                        listTransport.get(position).getVehicleList()!!.get(innerPosition).getVehicleNo()!!)
                                                }


                                                vehicleListLay.addView(innerItemView)
                                            }

                                        }
                                        binding!!.transportListLay.addView(itemView)
                                    }
                                } else {
                                    binding!!.transportListLay.visibility = View.GONE
                                    binding!!.tvTransportNoData.visibility = View.VISIBLE
                                }


                            }

                        } else Utils.showToastPopup(
                            mActivity!!,
                            getString(R.string.response_null_or_empty_validation)
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.transportListLay.visibility = View.GONE
                        binding!!.tvTransportNoData.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.transportListLay.visibility = View.GONE
                    binding!!.tvTransportNoData.visibility = View.VISIBLE
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    private fun getVehicleDetails(vehicleId : String,routeName : String){
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
            jsonObject.put(Constants.ParamsStudent.VEHICLEID, vehicleId)

            val requestBody: RequestBody = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
            )

            Utils.printLog("Url", Constants.DOMAIN_STUDENT + "/Webservice/getTransportVehicleDetails")

            val call: Call<ResponseBody> = apiInterfaceWithHeader.getTransportVehicleDetails(requestBody)
            call.enqueue(object : Callback<ResponseBody> {
                @SuppressLint("InflateParams")
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Utils.hideProgressBar()
                    try {
                        val responseStr = response.body()!!.string()
                        if (!responseStr.isNullOrEmpty()) {
                            val jsonArr = JSONArray(responseStr)
                            if (jsonArr.length() > 0) {
                                val listVehicleDetails: List<VehicleDetailsModel> = ArrayList()
                                for (position in 0 until jsonArr.length()) {
                                    val vehicleJsonObj = jsonArr.optJSONObject(position)
                                    val vehicleDetailsModel = VehicleDetailsModel()
                                    vehicleDetailsModel.setAmcDate(vehicleJsonObj.optString("amc_date"))
                                    vehicleDetailsModel.setCreatedAt(vehicleJsonObj.optString("created_at"))
                                    vehicleDetailsModel.setDeviceNo(vehicleJsonObj.optString("device_no"))
                                    vehicleDetailsModel.setDriverContact(vehicleJsonObj.optString("driver_contact"))
                                    vehicleDetailsModel.setDriverLicence(vehicleJsonObj.optString("driver_licence"))
                                    vehicleDetailsModel.setDriverName(vehicleJsonObj.optString("driver_name"))
                                    vehicleDetailsModel.setEngineChassisNumber(vehicleJsonObj.optString("engine_chassis_number"))
                                    vehicleDetailsModel.setEngineType(vehicleJsonObj.optString("engine_type"))
                                    vehicleDetailsModel.setFitnessValidity(vehicleJsonObj.optString("fitness_validity"))
                                    vehicleDetailsModel.setGpsEnable(vehicleJsonObj.optString("gps_enable"))
                                    vehicleDetailsModel.setId(vehicleJsonObj.optString("id"))
                                    vehicleDetailsModel.setInsuranceDate(vehicleJsonObj.optString("insurance_date"))
                                    vehicleDetailsModel.setInsuranceValidDate(vehicleJsonObj.optString("insurance_valid_date"))
                                    vehicleDetailsModel.setLastFuelAmount(vehicleJsonObj.optString("last_fuel_amount"))
                                    vehicleDetailsModel.setLastFuelInLitres(vehicleJsonObj.optString("last_fuel_in_litres"))
                                    vehicleDetailsModel.setLastNotedKms(vehicleJsonObj.optString("last_noted_kms"))
                                    vehicleDetailsModel.setManufactureYear(vehicleJsonObj.optString("manufacture_year"))
                                    vehicleDetailsModel.setNote(vehicleJsonObj.optString("note"))
                                    vehicleDetailsModel.setPassingDate(vehicleJsonObj.optString("passing_date"))
                                    vehicleDetailsModel.setPermitDate(vehicleJsonObj.optString("permit_date"))
                                    vehicleDetailsModel.setPollutionDate(vehicleJsonObj.optString("pollution_date"))
                                    vehicleDetailsModel.setPollutionValidDate(vehicleJsonObj.optString("pollution_valid_date"))
                                    vehicleDetailsModel.setPurchaseYear(vehicleJsonObj.optString("purchase_year"))
                                    vehicleDetailsModel.setRcNumber(vehicleJsonObj.optString("rc_number"))
                                    vehicleDetailsModel.setStaffId(vehicleJsonObj.optString("staff_id"))
                                    vehicleDetailsModel.setVecRouteId(vehicleJsonObj.optString("vec_route_id"))
                                    vehicleDetailsModel.setVehicalCondition(vehicleJsonObj.optString("vehical_condition"))
                                    vehicleDetailsModel.setVehicleModel(vehicleJsonObj.optString("vehicle_model"))
                                    vehicleDetailsModel.setVehicleNo(vehicleJsonObj.optString("vehicle_no"))

                                    (listVehicleDetails as ArrayList<VehicleDetailsModel>).add(vehicleDetailsModel)
                                }
                                if (listVehicleDetails.size > 0) {
                                    for (position in 0 until listVehicleDetails.size) {
                                        val inflater =
                                            mActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                                        val itemView: View = inflater!!.inflate(
                                            R.layout.item_student_transport_vehicle_details,
                                            null,
                                            true
                                        )
                                        val tvTransportHeader = itemView.findViewById<TextView>(R.id.tv_transport_vehicle_header)
                                        val tvTransportVehicleNo = itemView.findViewById<TextView>(R.id.tv_transport_vehicle_no)
                                        val tvTransportVehicleModel = itemView.findViewById<TextView>(R.id.tv_transport_vehicle_model)
                                        val tvTransportMade = itemView.findViewById<TextView>(R.id.tv_transport_vehicle_made)
                                        val tvTransportVehicleDriverName = itemView.findViewById<TextView>(R.id.tv_transport_driver_name)
                                        val tvTransportVehicleDriverLicense = itemView.findViewById<TextView>(R.id.tv_transport_driver_license)
                                        val tvTransportVehicleDriverContact = itemView.findViewById<TextView>(R.id.tv_transport_driver_contact)
                                        val ivCross = itemView.findViewById<ImageView>(R.id.iv_transport_cross)

                                        tvTransportHeader.text = routeName
                                        tvTransportVehicleNo.text = listVehicleDetails.get(position).getVehicleNo()
                                        tvTransportVehicleModel.text = listVehicleDetails.get(position).getVehicleModel()
                                        tvTransportMade.text = listVehicleDetails.get(position).getManufactureYear()
                                        tvTransportVehicleDriverName.text = listVehicleDetails.get(position).getDriverName()
                                        tvTransportVehicleDriverLicense.text = listVehicleDetails.get(position).getDriverLicence()
                                        tvTransportVehicleDriverContact.text = listVehicleDetails.get(position).getDriverContact()

                                        val dialog = BottomSheetDialog(mActivity!!)
                                        dialog.setContentView(itemView)
                                        dialog.show()

                                        ivCross.setOnClickListener(object : View.OnClickListener{
                                            override fun onClick(p0: View?) {
                                                dialog.dismiss()
                                            }

                                        })

                                    }
                                } else {
                                    binding!!.transportListLay.visibility = View.GONE
                                    binding!!.tvTransportNoData.visibility = View.VISIBLE
                                }


                            }

                        } else Utils.showToastPopup(
                            mActivity!!,
                            getString(R.string.response_null_or_empty_validation)
                        )
                    } catch (e: Exception) {
                        e.printStackTrace()
                        binding!!.transportListLay.visibility = View.GONE
                        binding!!.tvTransportNoData.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Utils.hideProgressBar()
                    Utils.showToastPopup(mActivity!!, getString(R.string.api_response_failure))
                    binding!!.transportListLay.visibility = View.GONE
                    binding!!.tvTransportNoData.visibility = View.VISIBLE
                }
            })
        } else Utils.showToastPopup(mActivity!!, getString(R.string.internet_connection_error))

    }

    override fun onClick(view: View?) {
        val id = view!!.id
        if (id == R.id.iv_back)
            onBackPressed()
    }

}