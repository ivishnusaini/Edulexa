package com.edulexa.activity.staff.custom_lesson_plan.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.edulexa.R
import com.edulexa.activity.staff.custom_lesson_plan.activity.CustomLessonPlanActivity
import com.edulexa.activity.staff.custom_lesson_plan.model.lesson_plan_list.DatumCustomLesson
import com.edulexa.api.Constants
import com.edulexa.databinding.ItemStudentCustomLessonPlanBinding
import com.edulexa.support.Utils
import com.pierfrancescosoffritti.youtubeplayer.player.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayer
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerFullScreenListener
import com.pierfrancescosoffritti.youtubeplayer.player.YouTubePlayerView

class CustomLessonPlanStaffAdapter(context: Activity, list : List<DatumCustomLesson?>?) :
    RecyclerView.Adapter<CustomLessonPlanStaffAdapter.ViewHolder>() {
    var context: Activity? = null
    var list : List<DatumCustomLesson?>? = null
    var binding : ItemStudentCustomLessonPlanBinding? = null
    var downloadID : Long? = null
    var staffId = ""
    var roleId = ""
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
            staffId = Utils.getStaffId(context!!)
            roleId = Utils.getStaffRoleId(context!!)
            /*context!!.registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
            )*/

            if (!list!![position]!!.getLectureYoutubeUrl().equals("")){
                binding!!.videoDetailLay.visibility = View.VISIBLE
                binding!!.tvTypeVideo.text = "Video"
                binding!!.tvSubtopicVideo.text = list!![position]!!.getTitle()
                if (!list!![position]!!.getNote().equals("")){
                    binding!!.descriptionLay.visibility = View.VISIBLE
                    binding!!.tvDescription.text = list!![position]!!.getNote()
                }else binding!!.descriptionLay.visibility = View.GONE
                if (!list!![position]!!.getName().equals("")){
                    binding!!.lessonLay.visibility = View.VISIBLE
                    binding!!.tvSubjectName.text = list!![position]!!.getName()
                }else binding!!.lessonLay.visibility = View.GONE
                if (!list!![position]!!.getTopic().equals("")){
                    binding!!.topicLay.visibility = View.VISIBLE
                    binding!!.tvTopicVideo.text = list!![position]!!.getTopic()
                }else binding!!.topicLay.visibility = View.GONE
                if (!list!![position]!!.getLesson().equals("")){
                    binding!!.lessonLay.visibility = View.VISIBLE
                    binding!!.tvLessonVideo.text = list!![position]!!.getLesson()
                }else binding!!.lessonLay.visibility = View.GONE
                if (!list!![position]!!.getPeriod().equals("")){
                    binding!!.periodLay.visibility = View.VISIBLE
                    binding!!.tvPeriod.text = list!![position]!!.getPeriod()
                }else binding!!.periodLay.visibility = View.GONE
                if (!list!![position]!!.getTeachingAids().equals("")){
                    binding!!.teachingAidsLay.visibility = View.VISIBLE
                    binding!!.tvTeachingAids.text = list!![position]!!.getTeachingAids()
                }else binding!!.teachingAidsLay.visibility = View.GONE
                if (!list!![position]!!.getPortionActuallyTaught().equals("")){
                    binding!!.portionActuallyTaughtLay.visibility = View.VISIBLE
                    binding!!.tvPortionActuallyTaught.text = list!![position]!!.getPortionActuallyTaught()
                }else binding!!.portionActuallyTaughtLay.visibility = View.GONE
                if (!list!![position]!!.getHwAssigned().equals("")){
                    binding!!.homeworkAssignedLay.visibility = View.VISIBLE
                    binding!!.tvHomeworkAssigned.text = list!![position]!!.getHwAssigned()
                }else binding!!.homeworkAssignedLay.visibility = View.GONE
                if (!list!![position]!!.getHwNotAssignedReason().equals("")){
                    binding!!.homeworkNotAssignedReasonLay.visibility = View.VISIBLE
                    binding!!.tvHomeworkNotAssignedReason.text = list!![position]!!.getHwNotAssignedReason()
                }else binding!!.homeworkNotAssignedReasonLay.visibility = View.GONE

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
            }else binding!!.videoDetailLay.visibility = View.GONE

            if (!list!![position]!!.getFile().equals("")){
                binding!!.imageDetailLay.visibility = View.VISIBLE
                binding!!.tvTypeImage.text = "Image"
                binding!!.tvSubtopicImage.text = list!![position]!!.getTitle()
                if (!list!![position]!!.getNote().equals("")){
                    binding!!.descriptionImageLay.visibility = View.VISIBLE
                    binding!!.tvDescriptionImage.text = list!![position]!!.getNote()
                }else binding!!.descriptionImageLay.visibility = View.GONE
                if (!list!![position]!!.getName().equals("")){
                    binding!!.lessonImageLay.visibility = View.VISIBLE
                    binding!!.tvSubjectNameImage.text = list!![position]!!.getName()
                }else binding!!.lessonImageLay.visibility = View.GONE
                if (!list!![position]!!.getTopic().equals("")){
                    binding!!.topicImageLay.visibility = View.VISIBLE
                    binding!!.tvTopicImage.text = list!![position]!!.getTopic()
                }else binding!!.topicImageLay.visibility = View.GONE
                if (!list!![position]!!.getLesson().equals("")){
                    binding!!.lessonImageLay.visibility = View.VISIBLE
                    binding!!.tvLessonImage.text = list!![position]!!.getLesson()
                }else binding!!.lessonImageLay.visibility = View.GONE
                if (!list!![position]!!.getPeriod().equals("")){
                    binding!!.periodImageLay.visibility = View.VISIBLE
                    binding!!.tvPeriodImage.text = list!![position]!!.getPeriod()
                }else binding!!.periodImageLay.visibility = View.GONE
                if (!list!![position]!!.getTeachingAids().equals("")){
                    binding!!.teachingAidsImageLay.visibility = View.VISIBLE
                    binding!!.tvTeachingAidsImage.text = list!![position]!!.getTeachingAids()
                }else binding!!.teachingAidsImageLay.visibility = View.GONE
                if (!list!![position]!!.getPortionActuallyTaught().equals("")){
                    binding!!.portionActuallyTaughtImageLay.visibility = View.VISIBLE
                    binding!!.tvPortionActuallyTaughtImage.text = list!![position]!!.getPortionActuallyTaught()
                }else binding!!.portionActuallyTaughtImageLay.visibility = View.GONE
                if (!list!![position]!!.getHwAssigned().equals("")){
                    binding!!.homeworkAssignedImageLay.visibility = View.VISIBLE
                    binding!!.tvHomeworkAssignedName.text = list!![position]!!.getHwAssigned()
                }else binding!!.homeworkAssignedImageLay.visibility = View.GONE
                if (!list!![position]!!.getHwNotAssignedReason().equals("")){
                    binding!!.homeworkNotAssignedReasonImageLay.visibility = View.VISIBLE
                    binding!!.tvHomeworkNotAssignedReasonImage.text = list!![position]!!.getHwNotAssignedReason()
                }else binding!!.homeworkNotAssignedReasonImageLay.visibility = View.GONE

                Utils.setImageUsingGlideImagePlaceholder(context,Constants.BASE_URL_WEBVIEW_DOMAIN_STAFF+list!!.get(position)!!.getFile(),binding!!.ivImage)

            }else binding!!.imageDetailLay.visibility = View.GONE


            if (roleId == "1" || roleId == "7")
                binding!!.tvDelete.visibility = View.VISIBLE
            else {
                if (staffId == list!![position]!!.getCreatedBy())
                    binding!!.tvDelete.visibility = View.VISIBLE
                else  binding!!.tvDelete.visibility = View.GONE
            }

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

    class ViewHolder(binding: ItemStudentCustomLessonPlanBinding) : RecyclerView.ViewHolder(binding.root)

}