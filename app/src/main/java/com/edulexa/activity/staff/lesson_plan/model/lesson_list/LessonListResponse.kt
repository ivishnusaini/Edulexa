package com.edulexa.activity.staff.lesson_plan.model.lesson_list

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class LessonListResponse {
    @SerializedName("data")
    @Expose
    private var data: List<DatumLesson?>? = null

    fun getData(): List<DatumLesson?>? {
        if (data == null)
            return ArrayList()
        return data
    }

    fun setData(data: List<DatumLesson?>?) {
        this.data = data
    }
}