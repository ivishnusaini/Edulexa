package com.edulexa.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterfaceStaff {
    @POST("login")
    fun staffLogin(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getClasses")
    fun getClasses(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getNotifications")
    fun getNotifications(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getClassSections")
    fun getClassSections(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getStudentsByClass")
    fun getStudentsByClass(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("view_student_profile")
    fun getStudentProfile(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("view_student_timeline")
    fun getK12Timeline(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("delete_student_timeline")
    fun deleteStudentTimeline(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("add_student_timeline")
    fun addStudentTimeline(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getAllSubjects")
    fun getAllSubjects(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getcustomlessonplan")
    fun getcustomlessonplan(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("deleteCustomLessonPlan")
    fun deleteCustomLessonPlan(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getLessonSubjectList")
    fun getLessonSubjectList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("addcustomlessonplan")
    fun addCustomLessonPlan(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("onlineexam")
    fun getOnlineExam(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("sendOnlineExamNotification")
    fun sendOnlineExamNotification(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("deleteOnlineExam")
    fun deleteOnlineExam(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("createPracticeExam")
    fun createPracticeExam(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("createOnlineExam")
    fun createOnlineExam(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getQuestionTypeList")
    fun getQuestionTypeList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("addQuestion")
    fun addQuestion(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("editOnlineExam")
    fun editOnlineExam(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("editPracticeExam")
    fun editPracticeExam(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getByOnlineExamQuestion")
    fun getByOnlineExamQuestion(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("deleteQuetion")
    fun deleteQuetion(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("subjectiveMarkReport")
    fun subjectiveMarkReport(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getStudentSubjectiveExam")
    fun getStudentSubjectiveExam(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getExamResult")
    fun getExamResult(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("homeworkList")
    fun homeworkList(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("evaluation")
    fun evaluation(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("addEvaluvation")
    fun addEvaluvation(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getSubjectGroupByClassAndSection")
    fun getSubjectGroupByClassAndSection(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getGroupSubjects")
    fun getGroupSubjects(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("addHomework")
    fun addHomework(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("lessionPlan")
    fun lessionPlan(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("deletesubjectsyllabus")
    fun deletesubjectsyllabus(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getSubjectGroupClassSectionsId")
    fun getSubjectGroupClassSectionsId(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getlessonBysubjectid")
    fun getlessonBysubjectId(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("getTopicBylessonid")
    fun getTopicBylessonId(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("editSubjectSyllabus")
    fun editSubjectSyllabus(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("addSyllabus")
    fun addSyllabus(@Body requestBody : RequestBody): Call<ResponseBody>

    @POST("deleteNotification")
    fun deleteNotification(@Body requestBody : RequestBody): Call<ResponseBody>
}