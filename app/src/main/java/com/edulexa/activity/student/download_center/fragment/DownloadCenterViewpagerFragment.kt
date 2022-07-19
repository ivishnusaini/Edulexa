package com.edulexa.activity.student.download_center.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.app.NotificationCompat
import androidx.fragment.app.Fragment
import com.edulexa.R
import com.edulexa.activity.student.download_center.model.DownloadDetailModel
import com.edulexa.api.Constants
import com.edulexa.databinding.FragmentDownloadCenterStudentBinding
import com.edulexa.support.Preference
import com.edulexa.support.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions

class DownloadCenterViewpagerFragment : Fragment() {
    var binding: FragmentDownloadCenterStudentBinding? = null
    var mActivity: Activity? = null
    var rootView: View? = null
    var preference : Preference? = null
    private var typeWiseList: List<DownloadDetailModel>? = ArrayList()
    private val downloadID: Long = 0

    companion object {
        @SuppressLint("StaticFieldLeak")
        var fragment: DownloadCenterViewpagerFragment? = null
        fun newInstance(list: List<DownloadDetailModel>): DownloadCenterViewpagerFragment? {
            fragment = DownloadCenterViewpagerFragment()
            val args = Bundle()
            args.putString("list", Gson().toJson(list))
            fragment!!.setArguments(args)
            return fragment
        }

        fun getInstance(): DownloadCenterViewpagerFragment? {
            return if (fragment == null) DownloadCenterViewpagerFragment() else fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gson = Gson()
        val listStr = requireArguments().getString("list")!!
        typeWiseList =
            gson.fromJson(listStr, object : TypeToken<List<DownloadDetailModel?>?>() {}.type)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDownloadCenterStudentBinding.inflate(layoutInflater)
        rootView = binding!!.root
        init()
        return rootView
    }

    private fun init() {
        mActivity = activity
        preference = Preference().getInstance(mActivity!!)
        mActivity!!.registerReceiver(
            onDownloadComplete,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )
        setUpData()
    }

    @SuppressLint("InflateParams")
    private fun setUpData() {
        if (typeWiseList!!.size > 0) {
            binding!!.downloadCenterListLay.visibility = View.VISIBLE
            binding!!.tvDownloadCenterNoData.visibility = View.GONE
            binding!!.downloadCenterListLay.removeAllViews()
            var position = 0
            while (position < typeWiseList!!.size) {
                val inflater =
                    mActivity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val itemView: View =
                    inflater!!.inflate(R.layout.item_student_download_center_detail, null, true)

                val cvDownloadCenter1 = itemView.findViewById<CardView>(R.id.cv_download_center_1)
                val tvDownloadCenterTitle1 =
                    itemView.findViewById<TextView>(R.id.tv_download_center_title_1)
                val ivDownloadCenter1 = itemView.findViewById<ImageView>(R.id.iv_download_center_1)
                val ivDownload1 = itemView.findViewById<ImageView>(R.id.iv_download_1)
                val cvDownloadCenter2 = itemView.findViewById<CardView>(R.id.cv_download_center_2)
                val tvDownloadCenterTitle2 =
                    itemView.findViewById<TextView>(R.id.tv_download_center_title_2)
                val ivDownloadCenter2 = itemView.findViewById<ImageView>(R.id.iv_download_center_2)
                val ivDownload2 = itemView.findViewById<ImageView>(R.id.iv_download_2)

                if (position < typeWiseList!!.size){
                    cvDownloadCenter1.visibility = View.VISIBLE
                    tvDownloadCenterTitle1.text = typeWiseList!!.get(position).getTitle()
                    if (typeWiseList!!.get(position).getType().equals("5"))
                        ivDownload1.visibility = View.GONE
                    else{
                        ivDownload1.visibility = View.VISIBLE
                        ivDownload1.tag = 1000 + position
                    }
                    ivDownloadCenter1.tag = 20000 + position
                }
                val secondItemPosition = position + 1
                if (secondItemPosition < typeWiseList!!.size){
                    cvDownloadCenter2.visibility = View.VISIBLE
                    tvDownloadCenterTitle2.text = typeWiseList!!.get(secondItemPosition).getTitle()
                    if (typeWiseList!!.get(secondItemPosition).getType().equals("5"))
                        ivDownload2.visibility = View.GONE
                    else{
                        ivDownload2.visibility = View.VISIBLE
                        ivDownload2.tag = 10000 + secondItemPosition
                    }
                    ivDownloadCenter2.tag = 30000 + position
                }

                ivDownload1.setOnClickListener {
                    val permissions = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                                val tag = ivDownload1.tag as Int - 1000
                                if (!typeWiseList!!.get(tag).getFile().equals("")){
                                    val imageUrl = preference!!.getString(Constants.Preference.IMAGESURL_STUDENT)
                                    val downloadUrl = imageUrl + typeWiseList!!.get(tag).getFile()
                                    Utils.startDownload(mActivity!!,downloadUrl,downloadUrl)
                                }else Utils.showToast(mActivity!!,getString(R.string.download_center_student_no_file_url))
                            }
                            override fun onDenied(
                                context: Context,
                                deniedPermissions: java.util.ArrayList<String>
                            ) {
                                Toast.makeText(mActivity, getString(R.string.permission_denied), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                }
                ivDownload2.setOnClickListener {
                    val permissions = arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
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
                                val tag = ivDownload2.tag as Int - 10000
                                if (!typeWiseList!!.get(tag).getFile().equals("")){
                                    val imageUrl = preference!!.getString(Constants.Preference.IMAGESURL_STUDENT)
                                    val downloadUrl = imageUrl + typeWiseList!!.get(tag).getFile()
                                    Utils.startDownload(mActivity!!,downloadUrl,downloadUrl)
                                }else Utils.showToast(mActivity!!,getString(R.string.download_center_student_no_file_url))
                            }
                            override fun onDenied(
                                context: Context,
                                deniedPermissions: java.util.ArrayList<String>
                            ) {
                                Toast.makeText(mActivity, "Permission denied", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        })
                }

                ivDownloadCenter1.setOnClickListener {
                    val tag = ivDownloadCenter1.tag as Int - 20000
                    if (typeWiseList!!.get(tag).getType().equals("5")){
                        if (!typeWiseList!!.get(tag).getVideoUrl().equals("")) {
                            val id1: List<String> = typeWiseList!!.get(tag).getVideoUrl().split("=")
                            val a = id1.get(0)
                            val b = id1.get(1)
                            val webIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=$b")
                            )
                            try {
                                startActivity(webIntent)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                            }
                        }else Utils.showToast(mActivity!!,getString(R.string.download_center_student_no_file_url))

                    }
                }

                ivDownloadCenter2.setOnClickListener {
                    val tag = ivDownloadCenter2.tag as Int - 30000
                    if (typeWiseList!!.get(tag).getType().equals("5")){
                        if (!typeWiseList!!.get(tag).getVideoUrl().equals("")) {
                            val id1: List<String> = typeWiseList!!.get(tag).getVideoUrl().split("=")
                            val a = id1.get(0)
                            val b = id1.get(1)
                            val webIntent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://www.youtube.com/watch?v=$b")
                            )
                            try {
                                startActivity(webIntent)
                            } catch (e: ActivityNotFoundException) {
                                e.printStackTrace()
                            }
                        }else Utils.showToast(mActivity!!,getString(R.string.download_center_student_no_file_url))

                    }
                }

                binding!!.downloadCenterListLay.addView(itemView)
                position += 2
            }
        } else {
            binding!!.downloadCenterListLay.visibility = View.GONE
            binding!!.tvDownloadCenterNoData.visibility = View.VISIBLE
        }
    }


    private val onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            if (downloadID == id) {
                val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.app_icon)
                    .setContentTitle(context.applicationContext.getString(R.string.app_name))
                    .setContentText("All Download completed")
                val notificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.notify(455, mBuilder.build())
            }
        }
    }

}