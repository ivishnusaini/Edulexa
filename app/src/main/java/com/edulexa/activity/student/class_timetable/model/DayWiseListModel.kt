package com.edulexa.activity.student.class_timetable.model


class DayWiseListModel {
    var subject = ""
    var name = ""
    var surName = ""
    var startTime = ""
    var endTime = ""
    var teacherName = ""

    @JvmName("getSubject1")
    fun getSubject() : String{
        return subject
    }
    @JvmName("setSubject1")
    fun setSubject(subject: String){
        this.subject = subject
    }


    @JvmName("getName1")
    fun getName() : String{
        return name
    }
    @JvmName("setName1")
    fun setName(name: String){
        this.name = name
    }


    @JvmName("getSurName1")
    fun getSurName() : String{
        return surName
    }

    @JvmName("setSurName1")
    fun setSurName(surName: String){
        this.surName = surName
    }

    @JvmName("getstartTime1")
    fun getStartTime() : String{
        return startTime
    }

    @JvmName("setstartTime1")
    fun setStartTime(startTime: String){
        this.startTime = startTime
    }
    @JvmName("getendTime1")
    fun getEndTime() : String{
        return endTime
    }

    @JvmName("setendTime1")
    fun setEndTime(endTime: String){
        this.endTime = endTime
    }
    @JvmName("getteacherName1")
    fun getTeacherName() : String{
        return teacherName
    }

    @JvmName("setteacherName1")
    fun setTeacherName(teacherName: String){
        this.teacherName = teacherName
    }
}