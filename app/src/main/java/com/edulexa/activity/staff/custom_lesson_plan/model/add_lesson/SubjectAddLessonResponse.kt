package com.edulexa.activity.staff.custom_lesson_plan.model.add_lesson

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubjectAddLessonResponse {
    @SerializedName("data")
    @Expose
    private var data: List<DatumAddLesson?>? = null

    fun getData(): List<DatumAddLesson?>? {
        return data
    }

    fun setData(data: List<DatumAddLesson?>?) {
        this.data = data
    }
}