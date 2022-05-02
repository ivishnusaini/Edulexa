package com.edulexa.support

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.edulexa.BuildConfig
import com.edulexa.R
import com.edulexa.activity.select_school.model.FetchBaseUrlResponse
import com.edulexa.api.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*
import java.util.regex.Pattern

class Utils {
    companion object {
        private var dialog: Dialog? = null
        fun hideStatusBar(context: Activity) {
            @Suppress("DEPRECATION")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.window.insetsController?.hide(WindowInsets.Type.statusBars())
            } else {
                context.window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }

        fun clearGlide(context: Activity?, imageView: ImageView?) {
            Glide.with(context!!)
                .clear(imageView!!)
        }

        fun setImageUsingGlide(context: Activity?, url: String?, imageView: ImageView?) {
            val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.app_icon)
                .error(R.drawable.app_icon)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform()
            Glide.with(context!!)
                .load(url)
                .apply(options)
                .into(imageView!!)
        }

        fun hideKeyboard(context: Activity) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = context.currentFocus
            if (view == null)
                view = View(context)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun isNetworkAvailable(context: Activity): Boolean {
            val connectivityManager =
                context.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                } else {
                    TODO("VERSION.SDK_INT < M")
                }
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
            return false
        }

        fun startActivity(activity: Activity, aClass: Class<*>?) {
            activity.startActivity(Intent(activity, aClass))
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fun startActivityFinish(activity: Activity, aClass: Class<*>?) {
            activity.startActivity(Intent(activity, aClass))
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            activity.finish()
        }

        fun startActivityBundle(activity: Activity, aClass: Class<*>?, bundle: Bundle?) {
            activity.startActivity(Intent(activity, aClass).putExtras(bundle!!))
            activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        fun isValidEmail(email: String?): Boolean {
            val EMAIL_PATTERN = ("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
            val pattern = Pattern.compile(EMAIL_PATTERN)
            val matcher = pattern.matcher(email)
            return matcher.matches()
        }

        fun getObject(name: String, aClass: Class<*>): Any {
            val gson = Gson()
            return gson.fromJson(name, aClass)
        }

        fun printLog(key: String, value: String) {
            if (BuildConfig.DEBUG) {
                Log.e(key, value)
            }
        }

        fun showToastPopup(context: Activity, message: String) {
            try {
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setContentView(R.layout.dialog_validation_popup)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setCanceledOnTouchOutside(false)
                val tvMessage = dialog.findViewById<TextView>(R.id.tv_message)
                val btnOk = dialog.findViewById<TextView>(R.id.btn_ok)
                tvMessage.text = message
                btnOk.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(p0: View?) {
                        dialog.dismiss()
                    }
                })
                dialog.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        fun showSnackBar(context: Activity, message: String) {
            val snackbar = Snackbar.make(
                context.findViewById(android.R.id.content),
                message,
                Snackbar.LENGTH_LONG
            )
            snackbar.setBackgroundTint(ContextCompat.getColor(context, R.color.red))
            snackbar.view.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
            snackbar.show()
        }

        fun getCurrentDate(): String? {
            @SuppressLint("SimpleDateFormat") val dateFormat: DateFormat =
                SimpleDateFormat("yyyy-MM-dd")
            val date = Date()
            return dateFormat.format(date)
        }

        fun getCurrentYear(): Int {
            return Calendar.getInstance().get(Calendar.YEAR);
        }

        fun getCurrentMonth(): Int {
            return Calendar.getInstance().get(Calendar.MONTH);
        }

        fun getCurrentDayNumber(): Int {
            val currentDate = getCurrentDate()
            val tokenizer = StringTokenizer(currentDate, "-")
            val year = tokenizer.nextToken()
            val month = tokenizer.nextToken()
            return (tokenizer.nextToken()).toInt()
        }

        fun getNumberOfDaysInMonth(year: Int, month: Int): Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val yearMonthObject: YearMonth = YearMonth.of(year, month)
                val daysInMonth: Int = yearMonthObject.lengthOfMonth()
                return daysInMonth
            } else {
                val calendar = GregorianCalendar(year, month, 1)
                val daysInMonth: Int = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                return daysInMonth
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun getCurrentDayOfWeek(dateStr: String): String {
            val inFormat = SimpleDateFormat("yyyy-MM-dd")
            val date = inFormat.parse(dateStr)
            val outFormat = SimpleDateFormat("EEEE")
            return outFormat.format(date!!)
        }

        fun getMonthNameFromMonthNo(monthNo: Int): String {
            when (monthNo) {
                1 -> {
                    return "January"
                }
                2 -> {
                    return "February"
                }
                3 -> {
                    return "March"
                }
                4 -> {
                    return "April"
                }
                5 -> {
                    return "May"
                }
                6 -> {
                    return "June"
                }
                7 -> {
                    return "July"
                }
                8 -> {
                    return "August"
                }
                9 -> {
                    return "September"
                }
                10 -> {
                    return "Octomber"
                }
                11 -> {
                    return "November"
                }
                12 -> {
                    return "December"
                }
                else -> {
                    return ""
                }
            }
        }

        fun showProgressBar(activity: Activity) {
            try {
                if (dialog != null && dialog!!.isShowing()) {
                    return
                }
                dialog = Dialog(activity)
                dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog!!.setContentView(R.layout.progress_bar)
                Objects.requireNonNull(dialog!!.getWindow())!!.setBackgroundDrawable(
                    ColorDrawable(
                        Color.TRANSPARENT
                    )
                )
                dialog!!.setCancelable(false)
                dialog!!.show()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

        fun hideProgressBar() {
            if (dialog != null)
                dialog!!.dismiss()
        }
    }
}