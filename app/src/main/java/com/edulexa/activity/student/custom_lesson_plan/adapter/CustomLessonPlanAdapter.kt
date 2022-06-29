package com.edulexa.activity.student.custom_lesson_plan.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DownloadManager
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.student.custom_lesson_plan.activity.CustomLessonPlanActivity
import com.edulexa.activity.student.custom_lesson_plan.model.DatumCustomLessonPlan
import com.edulexa.databinding.ItemStudentCustomLessonPlanBinding
import com.edulexa.support.Utils
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView

class CustomLessonPlanAdapter(context: Activity, list : List<DatumCustomLessonPlan?>?) :
    RecyclerView.Adapter<CustomLessonPlanAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumCustomLessonPlan?>? = null
    var binding : ItemStudentCustomLessonPlanBinding? = null
    var downloadID : Long? = null
    init {
        this.context = context
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ItemStudentCustomLessonPlanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        try {
            context!!.registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )
            if (!list!!.get(position)!!.getFile().equals("")){
                binding!!.imageDetailLay.visibility = View.VISIBLE
                binding!!.tvTypeImage.text = "Image"
            }else binding!!.imageDetailLay.visibility = View.GONE

            if (!list!!.get(position)!!.getLectureYoutubeUrl().equals("")){
                binding!!.videoDetailLay.visibility = View.VISIBLE
                binding!!.tvTypeVideo.text = "Video"
            }else binding!!.videoDetailLay.visibility = View.GONE

            binding!!.tvSubtopicVideo.text = list!!.get(position)!!.getTitle()
            binding!!.tvSubtopicImage.text = list!!.get(position)!!.getTitle()
            binding!!.tvTopicVideo.text = list!!.get(position)!!.getTopic()
            binding!!.tvTopicImage.text = list!!.get(position)!!.getTopic()
            binding!!.tvLessonVideo.text = list!!.get(position)!!.getLesson()
            binding!!.tvLessonImage.text = list!!.get(position)!!.getLesson()
            binding!!.tvSubjectName.text = list!!.get(position)!!.getName()
            binding!!.tvSubjectNameImage.text = list!!.get(position)!!.getName()

            binding!!.youtubePlayerView.playerUIController.enableLiveVideoUI(false)
            binding!!.youtubePlayerView.playerUIController.showYouTubeButton(false)
            binding!!.youtubePlayerView.initialize({ initializedYouTubePlayer: YouTubePlayer ->
                initializedYouTubePlayer.addListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady() {
                        val youtubeLink = list!![position]!!.getLectureYoutubeUrl()
                        var youtubeId: String? = ""
                        if (youtubeLink!!.contains("https://www.youtube.com/watch?v=")) {
                            var index = youtubeLink.indexOf("=")
                            index = index + 1
                            youtubeId = youtubeLink.substring(index)
                        }
                        initializedYouTubePlayer.loadVideo(youtubeId!!, 0f)
                        addFullScreenListenerToPlayer(binding!!.youtubePlayerView, youtubeId)
                    }
                })
            }, true)

            Utils.setImageUsingGlideImagePlaceholder(context,list!!.get(position)!!.getFile(),binding!!.ivImage)

            when(position % 3){
                0 -> {
                    binding!!.videoDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_blue)
                    binding!!.imageDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_yellow)
                    binding!!.ivInfoImage.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                    binding!!.ivInfoVideo.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                }
                1 -> {
                    binding!!.videoDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_green)
                    binding!!.imageDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_blue)
                    binding!!.ivInfoImage.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_2)
                    binding!!.ivInfoVideo.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_2)
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_2)
                }
                2 -> {
                    binding!!.videoDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_green)
                    binding!!.imageDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_blue)
                    binding!!.ivInfoImage.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_3)
                    binding!!.ivInfoVideo.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_3)
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_3)
                }
                else -> {
                    binding!!.videoDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_green)
                    binding!!.imageDetailLay.setBackgroundResource(R.drawable.bg_subject_syllabus_blue)
                    binding!!.ivInfoImage.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                    binding!!.ivInfoVideo.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                    binding!!.ivDownload.backgroundTintList = ContextCompat.getColorStateList(context!!,R.color.circular_1)
                }
            }

            binding!!.ivInfoImage.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    (context as CustomLessonPlanActivity).showInfoDialog(list!!.get(position)!!.getComprehensiveQuestions()!!,
                        list!!.get(position)!!.getGeneralObjectives()!!,list!!.get(position)!!.getPreviousKnowledge()!!,
                        list!!.get(position)!!.getTeachingMethod()!!)
                }
            })

            binding!!.ivInfoVideo.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    (context as CustomLessonPlanActivity).showInfoDialog(list!!.get(position)!!.getComprehensiveQuestions()!!,
                        list!!.get(position)!!.getGeneralObjectives()!!,list!!.get(position)!!.getPreviousKnowledge()!!,
                        list!!.get(position)!!.getTeachingMethod()!!)
                }
            })

            binding!!.ivDownload.setOnClickListener(object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    Permissions.check(context, permissions, null, null, object : PermissionHandler() {
                        override fun onGranted() {
                            downloadID = Utils.startDownload(context!!,"",list!!.get(position)!!.getFile()!!)
                        }
                        override fun onDenied(context: Context, deniedPermissions: ArrayList<String>) {
                            super.onDenied(context, deniedPermissions)
                        }
                    })
                }
            })

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private fun addFullScreenListenerToPlayer(viewfull: YouTubePlayerView, youtubeid: String) {
        viewfull.addFullScreenListener(object : YouTubePlayerFullScreenListener {
            override fun onYouTubePlayerEnterFullScreen() {
               /* val intent = Intent(context, PlayVideoActivity::class.java)
                intent.putExtra("videoURL", youtubeid)
                context!!.startActivity(intent)*/
            }

            override fun onYouTubePlayerExitFullScreen() {}
        })
    }

    override fun getItemCount(): Int {
        return list!!.size;
    }
    var onDownloadComplete: BroadcastReceiver = object : BroadcastReceiver() {
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

    class ViewHolder(binding: ItemStudentCustomLessonPlanBinding) : RecyclerView.ViewHolder(binding.root)

}