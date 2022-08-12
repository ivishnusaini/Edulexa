package com.edulexa.activity.staff.custom_lesson_plan.model.lesson_plan_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CustomLessonPlanResponse {
    @SerializedName("status")
    @Expose
    private var status: Int? = null

    @SerializedName("data")
    @Expose
    private var data: List<DatumCustomLesson?>? = null

    @SerializedName("formFields")
    @Expose
    private var formFields: FormFields? = null

    fun getStatus(): Int? {
        return status
    }

    fun setStatus(status: Int?) {
        this.status = status
    }

    fun getData(): List<DatumCustomLesson?>? {
        if (data == null)
            return ArrayList()
        return data
    }

    fun setData(data: List<DatumCustomLesson?>?) {
        this.data = data
    }

    fun getFormFields(): FormFields? {
        return formFields
    }

    fun setFormFields(formFields: FormFields?) {
        this.formFields = formFields
    }
}